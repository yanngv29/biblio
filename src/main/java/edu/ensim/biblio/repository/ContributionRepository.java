package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Contribution;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Contribution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

}
