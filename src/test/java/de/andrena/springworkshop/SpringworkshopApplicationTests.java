package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.repositories.EventRepository;
import de.andrena.springworkshop.repositories.SpeakerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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

	@LocalServerPort
	private int port;

	@Test
	@DirtiesContext
	public void insertTestEvent() throws Exception {
		ParameterizedTypeReference<Resource<Event>> eventType = new ParameterizedTypeReference<Resource<Event>>() {
		};
		ParameterizedTypeReference<Resource<Speaker>> speakersType = new ParameterizedTypeReference<Resource<Speaker>>() {
		};
		ParameterizedTypeReference<PagedResources<Resource<Speaker>>> pagedSpeakersType = new ParameterizedTypeReference<PagedResources<Resource<Speaker>>>() {
		};
		ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>> pagedEventsWithSpeakerType = new ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>>() {
		};

		Event event = createEvent(MY_EVENT);
		URI eventLocation = testRestTemplate.postForLocation("/talks", event);
		Speaker speaker = createSpeaker("Alice", "Doe", 1);
		URI createdSpeaker = testRestTemplate.postForLocation("/participants", speaker);
		associateSpeakerWithEvent(eventLocation.toString(), createdSpeaker.toString());


		Event event2 = createEvent(ANOTHER_TITLE);
		ResponseEntity<Resource<Event>> createdEvent2 = testRestTemplate.exchange("/talks", HttpMethod.POST, new HttpEntity<>(event2), eventType, Collections.emptyMap());
		Speaker speaker2 = createSpeaker("Bob", "Doe", 2);
		ResponseEntity<Resource<Speaker>> createdSpeaker2 = testRestTemplate.exchange("/participants", HttpMethod.POST, new HttpEntity<>(speaker2), speakersType, Collections.emptyMap());
		associateSpeakerWithEvent(createdEvent2.getBody().getLink("self").getHref(), createdSpeaker2.getBody().getLink("self").getHref());


		String eventsWithInlineSpeakersUrl = UriComponentsBuilder.fromPath("/talks").queryParam("projection", "inlineSpeaker").toUriString();
		ResponseEntity<PagedResources<Resource<InlineSpeakerImpl>>> pagedResult = testRestTemplate.exchange(eventsWithInlineSpeakersUrl, HttpMethod.GET, null, pagedEventsWithSpeakerType);
		Collection<Resource<InlineSpeakerImpl>> eventsWithSpeakers = pagedResult.getBody().getContent();
		assertThat(eventsWithSpeakers, hasSize(2));
		eventsWithSpeakers.forEach(
				inlineSpeakerResource -> assertThat(inlineSpeakerResource.getContent().getSpeakers(), is(not(empty())))
		);

		ResponseEntity<Resource<Event>> singleResult = testRestTemplate.exchange("/talks/1", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(MY_EVENT));

		ResponseEntity<PagedResources<Resource<Speaker>>> speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
		assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).map(Speaker::getFirstName).collect(Collectors.toList()), contains("Alice"));


		singleResult = testRestTemplate.exchange("/talks/2", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(ANOTHER_TITLE));
		speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
		assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).map(Speaker::getFirstName).collect(Collectors.toList()), contains("Bob"));
	}

	private void associateSpeakerWithEvent(String eventLocation, String speakerLink) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "uri-list"));

		testRestTemplate.postForEntity(eventLocation + "/speakers", new HttpEntity<>(speakerLink, headers), String.class);
	}

	@Test
	@DirtiesContext
	public void save() throws Exception {
		Speaker speaker = createSpeaker("John", "Doe", 1);
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

	private Speaker createSpeaker(String firstName, String lastName, int id) {
		Speaker speaker = new Speaker();
		speaker.setCompany(COMPANY);
		speaker.setFirstName(firstName);
		speaker.setLastName(lastName);
		speaker.setId(id);
		return speaker;
	}

}
