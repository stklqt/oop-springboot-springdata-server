package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.repositories.EventRepository;
import de.andrena.springworkshop.repositories.SpeakerRepository;
import org.junit.Before;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

	private static final String ANOTHER_TITLE = "another title";
	private static final String EVENT_TITLE = "my event";
	private final SpringworkshopTestUtils springworkshopTestUtils = new SpringworkshopTestUtils();

	private Speaker alice;
	private Speaker bob;
	private Event event;
	private Event anotherEvent;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private SpeakerRepository speakerRepository;

	@Before
	public void setup(){
		alice = SpringworkshopTestUtils.createSpeaker("Alice", "Doe", 1);
		bob = SpringworkshopTestUtils.createSpeaker("Bob", "Smith", 2);
		event = SpringworkshopTestUtils.createEvent(EVENT_TITLE);
		anotherEvent = SpringworkshopTestUtils.createEvent(ANOTHER_TITLE);
	}

	@Test
	@DirtiesContext // Closes context after test and removes it from cache in order to have a fresh test environment
	public void springDataRepository_savesEventsWithSpeakers() throws Exception {

		speakerRepository.save(alice);
		speakerRepository.save(bob);

		event.setSpeakers(Collections.singleton(alice));
		anotherEvent.setSpeakers(Collections.singleton(bob));

		eventRepository.save(event);
		eventRepository.save(anotherEvent);

		Event one = eventRepository.findOne(event.getId());
		Event two = eventRepository.findOne(anotherEvent.getId());
		assertThat(one.getSpeakers(), contains(alice));
		assertThat(two.getSpeakers(), contains(bob));
	}

	@Test
	@DirtiesContext
	public void endToEndTest_usingREST() throws Exception {

		ParameterizedTypeReference<Resource<Event>> eventType = new ParameterizedTypeReference<Resource<Event>>() {
		};
		ParameterizedTypeReference<Resource<Speaker>> speakersType = new ParameterizedTypeReference<Resource<Speaker>>() {
		};
		ParameterizedTypeReference<PagedResources<Resource<Speaker>>> pagedSpeakersType = new ParameterizedTypeReference<PagedResources<Resource<Speaker>>>() {
		};
		ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>> pagedEventsWithSpeakerType = new ParameterizedTypeReference<PagedResources<Resource<InlineSpeakerImpl>>>() {
		};

		URI eventLocation = testRestTemplate.postForLocation("/events", event);
		URI aliceLocation = testRestTemplate.postForLocation("/speakers", alice);
		associateSpeakerWithEvent(eventLocation.toString(), aliceLocation.toString());

		ResponseEntity<Resource<Event>> anotherEventResponse = testRestTemplate.exchange("/events", HttpMethod.POST, new HttpEntity<>(anotherEvent), eventType, Collections.emptyMap());
		ResponseEntity<Resource<Speaker>> bobResponse = testRestTemplate.exchange("/speakers", HttpMethod.POST, new HttpEntity<>(bob), speakersType, Collections.emptyMap());
		associateSpeakerWithEvent(anotherEventResponse.getBody().getLink("self").getHref(), bobResponse.getBody().getLink("self").getHref());

		String eventsWithInlineSpeakersUrl = UriComponentsBuilder.fromPath("/events").queryParam("projection", "inlineSpeaker").toUriString();
		ResponseEntity<PagedResources<Resource<InlineSpeakerImpl>>> pagedResult = testRestTemplate.exchange(eventsWithInlineSpeakersUrl, HttpMethod.GET, null, pagedEventsWithSpeakerType);
		Collection<Resource<InlineSpeakerImpl>> eventsWithSpeakers = pagedResult.getBody().getContent();
		assertThat(eventsWithSpeakers, hasSize(2));
		eventsWithSpeakers.forEach(
				inlineSpeakerResource -> assertThat(inlineSpeakerResource.getContent().getSpeakers(), is(not(empty())))
		);

		ResponseEntity<Resource<Event>> singleResult = testRestTemplate.exchange("/events/1", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(EVENT_TITLE));

		ResponseEntity<PagedResources<Resource<Speaker>>> speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
		assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).map(Speaker::getFirstName).collect(Collectors.toList()), contains("Alice"));

		singleResult = testRestTemplate.exchange("/events/2", HttpMethod.GET, null, eventType);
		assertThat(singleResult.getBody().getContent().getTitle(), is(ANOTHER_TITLE));
		speakers = testRestTemplate.exchange(singleResult.getBody().getLink("speakers").getHref(), HttpMethod.GET, null, pagedSpeakersType);
		assertThat(speakers.getBody().getContent().stream().map(Resource::getContent).map(Speaker::getFirstName).collect(Collectors.toList()), contains("Bob"));
	}

	private void associateSpeakerWithEvent(String eventLocation, String speakerLink) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "uri-list"));

		testRestTemplate.postForEntity(eventLocation + "/speakers", new HttpEntity<>(speakerLink, headers), String.class);
	}
}
