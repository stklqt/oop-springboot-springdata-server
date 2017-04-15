package de.andrena.springworkshop.facades;

import de.andrena.springworkshop.dao.EventDao;
import de.andrena.springworkshop.dao.EventDaoImpl;
import de.andrena.springworkshop.dto.EventDTO;

import java.util.List;

public class EventFacadeImpl implements EventFacade {

    private EventDao eventDao = new EventDaoImpl();

    @Override
    public List<EventDTO> getAllEvents(){
        return eventDao.getAllEvents().events;
    }

    @Override
    public List<EventDTO> getEventsWithDescriptionContaining(String description){
        return eventDao.getEventsWithDescriptionContaining(description).events;
    }

    @Override
    public EventDTO getEventWithTitle(String title){
        return eventDao.getEventWithTitle(title);
    }
}
