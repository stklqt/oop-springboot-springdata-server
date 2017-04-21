package de.andrena.springworkshop.facades;

import de.andrena.springworkshop.dto.EventDTO;
import de.andrena.springworkshop.entities.Event;

import java.util.List;

public interface EventFacade {
    List<Event> getAllEvents();

    List<EventDTO> getEventsWithDescriptionContaining(String description);

    List<EventDTO> getEventWithTitle(String title);
}
