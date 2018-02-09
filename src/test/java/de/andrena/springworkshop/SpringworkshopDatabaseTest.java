package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.repositories.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

// Much faster than the tests in SpringworkshopApplicationTests - @DataJpaTest causes only the configurations relevant to JPA to be applied
// Rolls back after each test so that they are independent of one another
@DataJpaTest
@RunWith(SpringRunner.class)
public class SpringworkshopDatabaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepo;

    @Test
    public void searchAllEvents() {




        
        //given
        Event event = getEvent("Testtitle");

        Event event2 = getEvent("Testtitle2");

        entityManager.persist(event);
        entityManager.persist(event2);
        entityManager.flush();

        //when
        Iterable<Event> events = eventRepo.findAll();

        Stream<Event> stream = StreamSupport.stream(events.spliterator(), false);
        List<Event> eventList = stream.collect(Collectors.toList());
        long anzahl = eventList.size();

        //then
        System.out.printf("ANZAHL  %s%n%n", anzahl);
        assertThat(anzahl).isGreaterThan(0);
        assertThat(eventList.get(0).getTitle()).isEqualToIgnoringCase("Testtitle");
    }

    private Event getEvent(String testtitle) {
        Event event = new Event();
        event.setTitle(testtitle);
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(8));
        return event;
    }
}
