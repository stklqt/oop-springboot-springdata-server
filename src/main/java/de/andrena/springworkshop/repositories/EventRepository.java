package de.andrena.springworkshop.repositories;

import de.andrena.springworkshop.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RepositoryRestResource(path = "talks")
public interface EventRepository extends CrudRepository<Event, Integer> {
	@RestResource(path = "byTitle")
	List<Event> findByTitleContaining(@Param("title") String title);

	List<Event> findEventByDescriptionContaining(@Param("description") String description);

	List<Event> findByStartTimeBefore(@Param("startTime") LocalDateTime startTime);

	Long countAllByStartTimeBefore(@Param("startTime") LocalDateTime startTime);
}
