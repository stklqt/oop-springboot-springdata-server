package de.andrena.springworkshop.repositories;

import de.andrena.springworkshop.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "event")
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByTitle(@Param("title") String title);

    List<Event> findByDescriptionContaining(@Param("description") String description);

//    List<Event> findByStartTimeBefore(@Param("startTime") LocalDateTime startTime);

//    Long countAllByStartTimeBefore(@Param("startTime") LocalDateTime startTime);

}
