package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Notation;

import edu.ensim.biblio.repository.NotationRepository;
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
 * REST controller for managing Notation.
 */
@RestController
@RequestMapping("/api")
public class NotationResource {

    private final Logger log = LoggerFactory.getLogger(NotationResource.class);

    private static final String ENTITY_NAME = "notation";

    private final NotationRepository notationRepository;

    public NotationResource(NotationRepository notationRepository) {
        this.notationRepository = notationRepository;
    }

    /**
     * POST  /notations : Create a new notation.
     *
     * @param notation the notation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notation, or with status 400 (Bad Request) if the notation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notations")
    @Timed
    public ResponseEntity<Notation> createNotation(@Valid @RequestBody Notation notation) throws URISyntaxException {
        log.debug("REST request to save Notation : {}", notation);
        if (notation.getId() != null) {
            throw new BadRequestAlertException("A new notation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notation result = notationRepository.save(notation);
        return ResponseEntity.created(new URI("/api/notations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notations : Updates an existing notation.
     *
     * @param notation the notation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notation,
     * or with status 400 (Bad Request) if the notation is not valid,
     * or with status 500 (Internal Server Error) if the notation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notations")
    @Timed
    public ResponseEntity<Notation> updateNotation(@Valid @RequestBody Notation notation) throws URISyntaxException {
        log.debug("REST request to update Notation : {}", notation);
        if (notation.getId() == null) {
            return createNotation(notation);
        }
        Notation result = notationRepository.save(notation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notations : get all the notations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notations in body
     */
    @GetMapping("/notations")
    @Timed
    public List<Notation> getAllNotations() {
        log.debug("REST request to get all Notations");
        return notationRepository.findAll();
        }

    /**
     * GET  /notations/:id : get the "id" notation.
     *
     * @param id the id of the notation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notation, or with status 404 (Not Found)
     */
    @GetMapping("/notations/{id}")
    @Timed
    public ResponseEntity<Notation> getNotation(@PathVariable Long id) {
        log.debug("REST request to get Notation : {}", id);
        Notation notation = notationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notation));
    }

    /**
     * DELETE  /notations/:id : delete the "id" notation.
     *
     * @param id the id of the notation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notations/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotation(@PathVariable Long id) {
        log.debug("REST request to delete Notation : {}", id);
        notationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
