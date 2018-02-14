package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.PublicationGouvernementale;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PublicationGouvernementale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationGouvernementaleRepository extends JpaRepository<PublicationGouvernementale, Long> {

}
