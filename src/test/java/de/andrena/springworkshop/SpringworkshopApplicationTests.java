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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringworkshopApplicationTests {

    private static final String COMPANY = "company";
    private static final String ANOTHER_TITLE = "another title";
    private static final String MY_EVENT = "my event";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    @DirtiesContext
    public void insertTestEvent() throws Exception {
        ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>> pagedEventsWithSpeakerType = new ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>>() {
        };
        ParameterizedTypeReference<Resource<Event>> eventType = new ParameterizedTypeReference<Resource<Event>>() {
        };
        ParameterizedTypeReference<PagedResources<Resource<Speaker>>> pagedSpeakersType = new ParameterizedTypeReference<PagedResources<Resource<Speaker>>>() {
        };

        Speaker speaker = createSpeaker(new SpeakerKey("Alice", "Doe"));

        URI speakerLocation = testRestTemplate.postForLocation("/speaker", speaker);
        Event event = createEvent(MY_EVENT);
        URI eventLocation = testRestTemplate.postForLocation("/event", event);

        Speaker speaker2 = createSpeaker(new SpeakerKey("Bob", "Doe"));
        URI speaker2Location = testRestTemplate.postForLocation("/speaker", speaker2);
        Event event2 = createEvent(ANOTHER_TITLE);
        URI event2Location = testRestTemplate.postForLocation("/event", event2);

        associateSpeakerWithEvent(eventLocation, speakerLocation);

        associateSpeakerWithEvent(event2Location, speaker2Location);

        ResponseEntity<PagedResources<Resource<InlineSpeakerImpl>>> pagedResult = testRestTemplate.exchange("/event", HttpMethod.GET, null, pagedEventsWithSpeakerType);
        assertThat(pagedResult.getBody().getContent(), hasSize(2));

        ResponseEntity<Resource<Event>> singleResult = testRestTemplate.exchange("/event/1", HttpMethod.GET, null, eventType);
        assertThat(singleResult.getBody().getContent().getTitle(), is(MY_EVENT));

        ResponseEntity<PagedResources<Resource<Speaker>>> speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
        assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList()), contains(speaker));


        singleResult = testRestTemplate.exchange("/event/2", HttpMethod.GET, null, eventType);
        assertThat(singleResult.getBody().getContent().getTitle(), is(ANOTHER_TITLE));
        speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
        assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList()), contains(speaker2));
    }

    @Test
    public void name() throws Exception {
        ParameterizedTypeReference<Resources<Resource<Event>>> type = new ParameterizedTypeReference<Resources<Resource<Event>>>() {
        };
        ResponseEntity<Resources<Resource<Event>>> exchange = testRestTemplate.exchange("/event", HttpMethod.GET, null, type);
        System.out.println("exchange = " + exchange);
    }

    private void associateSpeakerWithEvent(URI eventLocation, URI speakerLink) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "uri-list"));

        testRestTemplate.postForEntity(eventLocation + "/speakers", new HttpEntity<>(speakerLink.toString(), headers), String.class);
    }

    @Test
    @DirtiesContext
    public void save() throws Exception {
        SpeakerKey speakerKey = new SpeakerKey("John", "Doe");
        Speaker speaker = createSpeaker(speakerKey);
        speakerRepository.save(speaker);
        Event event = createEvent(MY_EVENT);
        event.setSpeakers(Collections.singleton(speaker));
        Event event2 = createEvent(ANOTHER_TITLE);
        event2.setSpeakers(Collections.singleton(speaker));

        eventRepository.save(event);
        eventRepository.save(event2);

        Event one = eventRepository.findOne(event.getId());
        Event two = eventRepository.findOne(event2.getId());
        assertThat(one.getSpeakers(), contains(speaker));
        assertThat(two.getSpeakers(), contains(speaker));
    }

    private Event createEvent(String title) {
        Event event = new Event();
        event.setTitle(title);
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(1));
        return event;
    }

    private Speaker createSpeaker(SpeakerKey name) {
        Speaker speaker = new Speaker();
        speaker.setCompany(COMPANY);
        speaker.setName(name);
        return speaker;
    }

}
