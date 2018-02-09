package de.andrena.springworkshop.repositories;


import de.andrena.springworkshop.entities.Speaker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
public interface SpeakerRepository extends CrudRepository<Speaker, Integer> {
    /* http://localhost:8090/speakers/search/findSpeakersByCompany?company=MATHEMA */
    List<Speaker> findSpeakersByCompanyContaining(@Param(value = "company") String company);


}
