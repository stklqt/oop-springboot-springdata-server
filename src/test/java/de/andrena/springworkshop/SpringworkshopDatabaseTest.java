package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.repositories.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;

// Much faster than the tests in SpringworkshopApplicationTests - @DataJpaTest causes only the configurations relevant to JPA to be applied
// Rolls back after each test so that they are independent of one another
@DataJpaTest
@RunWith(SpringRunner.class)
public class SpringworkshopDatabaseTest {

    private static final String TITLE = "event title";
    public static final String ANOTHER_TITLE = "another title";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findsEventByTitle() {
        Event event = SpringworkshopTestUtils.createEvent(TITLE);
        entityManager.persist(event);
        entityManager.flush();

        List<Event> events = eventRepository.findByTitleContaining(TITLE);
        assertThat(events.size(), is(1));
        assertThat(events.get(0).getTitle(), is(TITLE));
    }

    @Test
    public void showsThatTestsAreIndependent() throws Exception {
        Event event = SpringworkshopTestUtils.createEvent(TITLE);
        Event anotherEvent = SpringworkshopTestUtils.createEvent(ANOTHER_TITLE);
        entityManager.persist(event);
        entityManager.persist(anotherEvent);
        entityManager.flush();

        Iterable<Event> events = eventRepository.findAll();
        assertThat(events, iterableWithSize(2));

    }
}
