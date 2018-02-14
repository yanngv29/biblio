package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Memoire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Memoire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemoireRepository extends JpaRepository<Memoire, Long> {

}
