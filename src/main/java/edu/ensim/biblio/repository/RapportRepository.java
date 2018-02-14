package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Rapport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {

}
