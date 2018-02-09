package de.andrena.springworkshop.repositories;

import de.andrena.springworkshop.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Integer> {
	//TODO implement search using event title

    List<Event> findByTitleContaining(@Param(value = "title") String title);

}
