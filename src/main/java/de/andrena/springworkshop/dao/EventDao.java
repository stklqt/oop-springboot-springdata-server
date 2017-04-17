package de.andrena.springworkshop.dao;

import de.andrena.springworkshop.dto.EventDTO;
import de.andrena.springworkshop.dto.EventListDTO;

public interface EventDao {

    EventListDTO getAllEvents();

    EventDTO getEventWithTitle(String title);

    EventListDTO getEventsWithDescriptionContaining(String description);
}