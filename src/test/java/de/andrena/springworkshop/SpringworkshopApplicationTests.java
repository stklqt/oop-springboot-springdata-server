package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKey;
import de.andrena.springworkshop.repositories.EventRepository;
import de.andrena.springworkshop.repositories.SpeakerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringworkshopApplicationTests {

    public static final String ANOTHER_TITLE = "another title";
    public static final String MY_EVENT = "my event";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    @DirtiesContext
    public void insertTestEvent() throws Exception {

        Speaker speaker = createSpeaker("company", new SpeakerKey("Alice", "Doe"));
        testRestTemplate.postForEntity("/speaker", speaker, Map.class);
        Event event = createEvent(MY_EVENT, speaker);
        Speaker speaker2 = createSpeaker("company", new SpeakerKey("Bob", "Doe"));
        testRestTemplate.postForEntity("/speaker", speaker2, Map.class);
        Event event2 = createEvent(ANOTHER_TITLE, speaker2);

        testRestTemplate.postForEntity("/event", event, Map.class);
        testRestTemplate.postForEntity("/event", event2, Map.class);

        ResponseEntity<PagedEvent> pagedResult = testRestTemplate.getForEntity("/event", PagedEvent.class);
        assertThat(pagedResult.getBody().getContent().size(), is(2));

        ResponseEntity<Event> typedResult = testRestTemplate.getForEntity("/event/1", Event.class);
        assertThat(typedResult.getBody().getTitle(), is(MY_EVENT));

        typedResult = testRestTemplate.getForEntity("/event/2", Event.class);
        assertThat(typedResult.getBody().getTitle(), is(ANOTHER_TITLE));
    }

    @Test
    @DirtiesContext
    public void save() throws Exception {
        SpeakerKey speakerKey = new SpeakerKey("John", "Doe");
        Speaker speaker = createSpeaker("company", speakerKey);
        speakerRepository.save(speaker);
        Event event = createEvent(MY_EVENT, speaker);
        Event event2 = createEvent(ANOTHER_TITLE, speaker);

        eventRepository.save(event);
        eventRepository.save(event2);

        Event one = eventRepository.findOne(event.getId());
        Event two = eventRepository.findOne(event2.getId());
        assertThat(one.getSpeakers(), contains(speaker));
        assertThat(two.getSpeakers(), contains(speaker));
    }

    private Event createEvent(String title, Speaker name) {
        Event event = new Event();
        event.setTitle(title);
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(1));
        event.setSpeakers(Collections.singleton(name));
        return event;
    }

    private Speaker createSpeaker(String company, SpeakerKey name) {
        Speaker speaker = new Speaker();
        speaker.setCompany("a title");
        speaker.setName(name);
        return speaker;
    }

    private static class PagedEvent extends PagedResources<Event> {
    }
}
