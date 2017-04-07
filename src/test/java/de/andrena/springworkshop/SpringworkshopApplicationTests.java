package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringworkshopApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void insertTestEvent() throws Exception {
		Event event = new Event();
		event.setTitle("my event");
		event.setStartTime(LocalDateTime.now());
		event.setEndTime(LocalDateTime.now().plusHours(1));
		event.setSpeakers(Arrays.asList(new Speaker()));
		testRestTemplate.put("/event", event);

		ResponseEntity<Object> result = testRestTemplate.getForEntity("/event", Object.class);
		ResponseEntity<Event> typedResult = testRestTemplate.getForEntity("/event", Event.class);
		System.out.println("event = " + event);
		System.out.println("result = " + result);
		System.out.println("typedResult = " + typedResult);

	}

}
