package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Actes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Actes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActesRepository extends JpaRepository<Actes, Long> {

}
