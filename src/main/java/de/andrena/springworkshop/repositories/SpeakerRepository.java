package de.andrena.springworkshop.repositories;

import de.andrena.springworkshop.entities.Speaker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SpeakerRepository extends CrudRepository<Speaker, Integer> {
}
