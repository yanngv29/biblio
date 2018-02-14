package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Communication;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Communication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {

}
