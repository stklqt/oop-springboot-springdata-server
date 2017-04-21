package de.andrena.springworkshop.facades;

import de.andrena.springworkshop.dao.EventDao;
import de.andrena.springworkshop.dto.EventDTO;
import de.andrena.springworkshop.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private EventDao eventDao;

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    @Override
    public List<EventDTO> getEventsWithDescriptionContaining(String description){
        return eventDao.getEventsWithDescriptionContaining(description).events;
    }

    @Override
    public List<EventDTO> getEventWithTitle(String title) {
        return eventDao.getEventsWithTitleContaining(title).events;
    }
}
