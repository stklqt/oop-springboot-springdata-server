package de.andrena.springworkshop.repositories;


import de.andrena.springworkshop.entities.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
public interface SpeakerRepository extends CrudRepository<Speaker, Integer> {
    /* Test with
        http://localhost:8090/speakers/search/findSpeakersByCompany?company=MATHEMA
    */
    List<Speaker> findSpeakersByCompanyContaining(@Param(value = "company") String company);

    /* Native Query Test - not finally implemented
    *  extends JpaRepository<Speaker, Integer>
    */
//    @Query(value = "SELECT * FROM SPEAKERS WHERE COMPANY = ?0", nativeQuery = true)
//    List<Speaker> findByCompanyName(String company);

}
