package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Notation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Notation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotationRepository extends JpaRepository<Notation, Long> {

}
