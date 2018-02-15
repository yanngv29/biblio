package edu.ensim.biblio.repository;

import edu.ensim.biblio.domain.Chercheur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Chercheur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChercheurRepository extends JpaRepository<Chercheur, Long> {
    @Query("select distinct chercheur from Chercheur chercheur left join fetch chercheur.actes left join fetch chercheur.articles left join fetch chercheur.chapitres left join fetch chercheur.communications left join fetch chercheur.ouvrages left join fetch chercheur.publicationGouvernementales left join fetch chercheur.revues left join fetch chercheur.memoires left join fetch chercheur.rapports")
    List<Chercheur> findAllWithEagerRelationships();

    @Query("select chercheur from Chercheur chercheur left join fetch chercheur.actes left join fetch chercheur.articles left join fetch chercheur.chapitres left join fetch chercheur.communications left join fetch chercheur.ouvrages left join fetch chercheur.publicationGouvernementales left join fetch chercheur.revues left join fetch chercheur.memoires left join fetch chercheur.rapports where chercheur.id =:id")
    Chercheur findOneWithEagerRelationships(@Param("id") Long id);

}
