package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Ouvrage;

import edu.ensim.biblio.repository.OuvrageRepository;
import edu.ensim.biblio.web.rest.errors.BadRequestAlertException;
import edu.ensim.biblio.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ouvrage.
 */
@RestController
@RequestMapping("/api")
public class OuvrageResource {

    private final Logger log = LoggerFactory.getLogger(OuvrageResource.class);

    private static final String ENTITY_NAME = "ouvrage";

    private final OuvrageRepository ouvrageRepository;

    public OuvrageResource(OuvrageRepository ouvrageRepository) {
        this.ouvrageRepository = ouvrageRepository;
    }

    /**
     * POST  /ouvrages : Create a new ouvrage.
     *
     * @param ouvrage the ouvrage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ouvrage, or with status 400 (Bad Request) if the ouvrage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ouvrages")
    @Timed
    public ResponseEntity<Ouvrage> createOuvrage(@Valid @RequestBody Ouvrage ouvrage) throws URISyntaxException {
        log.debug("REST request to save Ouvrage : {}", ouvrage);
        if (ouvrage.getId() != null) {
            throw new BadRequestAlertException("A new ouvrage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ouvrage result = ouvrageRepository.save(ouvrage);
        return ResponseEntity.created(new URI("/api/ouvrages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ouvrages : Updates an existing ouvrage.
     *
     * @param ouvrage the ouvrage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ouvrage,
     * or with status 400 (Bad Request) if the ouvrage is not valid,
     * or with status 500 (Internal Server Error) if the ouvrage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ouvrages")
    @Timed
    public ResponseEntity<Ouvrage> updateOuvrage(@Valid @RequestBody Ouvrage ouvrage) throws URISyntaxException {
        log.debug("REST request to update Ouvrage : {}", ouvrage);
        if (ouvrage.getId() == null) {
            return createOuvrage(ouvrage);
        }
        Ouvrage result = ouvrageRepository.save(ouvrage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ouvrage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ouvrages : get all the ouvrages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ouvrages in body
     */
    @GetMapping("/ouvrages")
    @Timed
    public List<Ouvrage> getAllOuvrages() {
        log.debug("REST request to get all Ouvrages");
        return ouvrageRepository.findAll();
        }

    /**
     * GET  /ouvrages/:id : get the "id" ouvrage.
     *
     * @param id the id of the ouvrage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ouvrage, or with status 404 (Not Found)
     */
    @GetMapping("/ouvrages/{id}")
    @Timed
    public ResponseEntity<Ouvrage> getOuvrage(@PathVariable Long id) {
        log.debug("REST request to get Ouvrage : {}", id);
        Ouvrage ouvrage = ouvrageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ouvrage));
    }

    /**
     * DELETE  /ouvrages/:id : delete the "id" ouvrage.
     *
     * @param id the id of the ouvrage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ouvrages/{id}")
    @Timed
    public ResponseEntity<Void> deleteOuvrage(@PathVariable Long id) {
        log.debug("REST request to delete Ouvrage : {}", id);
        ouvrageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
