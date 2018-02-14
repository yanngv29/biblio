package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Revue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Revue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevueRepository extends JpaRepository<Revue, Long> {

}
