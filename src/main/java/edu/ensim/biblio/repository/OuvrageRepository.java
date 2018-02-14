package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Ouvrage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ouvrage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {

}
