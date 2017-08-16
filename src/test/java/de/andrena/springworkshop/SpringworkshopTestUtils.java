package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;

import java.time.LocalDateTime;

public class SpringworkshopTestUtils {

    private static final String COMPANY = "company";

    static Event createEvent(String title) {
        Event event = new Event();
        event.setTitle(title);
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(1));
        return event;
    }

    static Speaker createSpeaker(String firstName, String lastName, int id) {
        Speaker speaker = new Speaker();
        speaker.setCompany(COMPANY);
        speaker.setFirstName(firstName);
        speaker.setLastName(lastName);
        speaker.setId(id);
        return speaker;
    }
}