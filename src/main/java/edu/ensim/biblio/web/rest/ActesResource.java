package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Actes;

import edu.ensim.biblio.repository.ActesRepository;
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
 * REST controller for managing Actes.
 */
@RestController
@RequestMapping("/api")
public class ActesResource {

    private final Logger log = LoggerFactory.getLogger(ActesResource.class);

    private static final String ENTITY_NAME = "actes";

    private final ActesRepository actesRepository;

    public ActesResource(ActesRepository actesRepository) {
        this.actesRepository = actesRepository;
    }

    /**
     * POST  /actes : Create a new actes.
     *
     * @param actes the actes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actes, or with status 400 (Bad Request) if the actes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actes")
    @Timed
    public ResponseEntity<Actes> createActes(@Valid @RequestBody Actes actes) throws URISyntaxException {
        log.debug("REST request to save Actes : {}", actes);
        if (actes.getId() != null) {
            throw new BadRequestAlertException("A new actes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actes result = actesRepository.save(actes);
        return ResponseEntity.created(new URI("/api/actes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actes : Updates an existing actes.
     *
     * @param actes the actes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actes,
     * or with status 400 (Bad Request) if the actes is not valid,
     * or with status 500 (Internal Server Error) if the actes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actes")
    @Timed
    public ResponseEntity<Actes> updateActes(@Valid @RequestBody Actes actes) throws URISyntaxException {
        log.debug("REST request to update Actes : {}", actes);
        if (actes.getId() == null) {
            return createActes(actes);
        }
        Actes result = actesRepository.save(actes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actes : get all the actes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actes in body
     */
    @GetMapping("/actes")
    @Timed
    public List<Actes> getAllActes() {
        log.debug("REST request to get all Actes");
        return actesRepository.findAll();
        }

    /**
     * GET  /actes/:id : get the "id" actes.
     *
     * @param id the id of the actes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actes, or with status 404 (Not Found)
     */
    @GetMapping("/actes/{id}")
    @Timed
    public ResponseEntity<Actes> getActes(@PathVariable Long id) {
        log.debug("REST request to get Actes : {}", id);
        Actes actes = actesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actes));
    }

    /**
     * DELETE  /actes/:id : delete the "id" actes.
     *
     * @param id the id of the actes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actes/{id}")
    @Timed
    public ResponseEntity<Void> deleteActes(@PathVariable Long id) {
        log.debug("REST request to delete Actes : {}", id);
        actesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
