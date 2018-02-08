package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.repositories.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SpringworkshopApplicationTests {

	private static final String ANOTHER_TITLE = "another title";
	private static final String MY_EVENT = "my event";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private EventRepository eventRepository;

	@Test
	@DirtiesContext
	public void insertTestEvent() throws Exception {
		ParameterizedTypeReference<Resource<Event>> eventType = new ParameterizedTypeReference<Resource<Event>>() {
		};
		ParameterizedTypeReference<PagedResources<Resource<Event>>> pagedEventsType = new ParameterizedTypeReference<PagedResources<Resource<Event>>>() {
		};

		Event event = createEvent(MY_EVENT);
		testRestTemplate.postForLocation("/events", event);

		Event event2 = createEvent(ANOTHER_TITLE);
		testRestTemplate.exchange("/events", HttpMethod.POST, new HttpEntity<>(event2), eventType, Collections.emptyMap());

		ResponseEntity<PagedResources<Resource<Event>>> pagedResult = testRestTemplate.exchange("/events", HttpMethod.GET, null, pagedEventsType);
		Collection<Resource<Event>> eventsWithSpeakers = pagedResult.getBody().getContent();
		assertThat(eventsWithSpeakers, hasSize(2));

		ResponseEntity<Resource<Event>> singleResult = testRestTemplate.exchange("/events/1", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(MY_EVENT));

		singleResult = testRestTemplate.exchange("/events/2", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(ANOTHER_TITLE));
	}

	@Test
	@DirtiesContext
	public void save() throws Exception {
		Event event = createEvent(MY_EVENT);
		Event event2 = createEvent(ANOTHER_TITLE);

		eventRepository.save(event);
		eventRepository.save(event2);

		Event one = eventRepository.findOne(event.getId());
		Event two = eventRepository.findOne(event2.getId());
		assertThat(one.getTitle(), is(MY_EVENT));
		assertThat(two.getTitle(), is(ANOTHER_TITLE));
	}

	private Event createEvent(String title) {
		Event event = new Event();
		event.setTitle(title);
		event.setStartTime(LocalDateTime.now());
		event.setEndTime(LocalDateTime.now().plusHours(1));
		return event;
	}

}
