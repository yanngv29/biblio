package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.NumeroRevue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NumeroRevue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumeroRevueRepository extends JpaRepository<NumeroRevue, Long> {

}
