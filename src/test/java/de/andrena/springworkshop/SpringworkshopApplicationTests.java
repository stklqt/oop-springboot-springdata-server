package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringworkshopApplicationTests {

	public static final String ANOTHER_TITLE = "another title";
	public static final String MY_EVENT = "my event";
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void insertTestEvent() throws Exception {
		Event event = createEvent(MY_EVENT, new SpeakerKey("Alice", "Doe"));
		Event event2 = createEvent(ANOTHER_TITLE, new SpeakerKey("Bob", "Doe"));

		testRestTemplate.postForEntity("/event",event, Map.class);
		testRestTemplate.postForEntity("/event",event2, Map.class);

		ResponseEntity<PagedEvent> pagedResult = testRestTemplate.getForEntity("/event", PagedEvent.class);
		assertThat(pagedResult.getBody().getContent().size(),is(2));

		ResponseEntity<Event> typedResult = testRestTemplate.getForEntity("/event/1", Event.class);
		assertThat(typedResult.getBody().getTitle(), is(MY_EVENT));

		typedResult = testRestTemplate.getForEntity("/event/2", Event.class);
		assertThat(typedResult.getBody().getTitle(), is(ANOTHER_TITLE));
	}

	private Event createEvent(String title, SpeakerKey name) {
		Event event = new Event();
		event.setTitle(title);
		event.setStartTime(LocalDateTime.now());
		event.setEndTime(LocalDateTime.now().plusHours(1));
		Speaker speaker = new Speaker();
		speaker.setTitle("a title");
		speaker.setName(name);
		event.setSpeakers(Arrays.asList(speaker));
		return event;
	}

	private static class PagedEvent extends PagedResources<Event>{
	}
}
