package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Acte;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Acte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeRepository extends JpaRepository<Acte, Long> {

}
