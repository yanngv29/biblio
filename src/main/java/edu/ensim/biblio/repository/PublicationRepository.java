package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Publication;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Publication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
