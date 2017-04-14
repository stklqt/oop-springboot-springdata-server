package de.andrena.springworkshop.repositories;

import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "speaker", itemResourceRel = "speaker/{surname}_{firstName}")
public interface SpeakerRepository extends CrudRepository<Speaker, SpeakerKey> {
}
