package de.andrena.springworkshop.dao;

import de.andrena.springworkshop.dto.EventListDTO;
import de.andrena.springworkshop.entities.Event;

import java.util.List;

public interface EventDao {

    List<Event> getAllEvents();

    EventListDTO getEventsWithTitleContaining(String title);

    EventListDTO getEventsWithDescriptionContaining(String description);
}