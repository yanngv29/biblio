package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Memoire;

import edu.ensim.biblio.repository.MemoireRepository;
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
 * REST controller for managing Memoire.
 */
@RestController
@RequestMapping("/api")
public class MemoireResource {

    private final Logger log = LoggerFactory.getLogger(MemoireResource.class);

    private static final String ENTITY_NAME = "memoire";

    private final MemoireRepository memoireRepository;

    public MemoireResource(MemoireRepository memoireRepository) {
        this.memoireRepository = memoireRepository;
    }

    /**
     * POST  /memoires : Create a new memoire.
     *
     * @param memoire the memoire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memoire, or with status 400 (Bad Request) if the memoire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/memoires")
    @Timed
    public ResponseEntity<Memoire> createMemoire(@Valid @RequestBody Memoire memoire) throws URISyntaxException {
        log.debug("REST request to save Memoire : {}", memoire);
        if (memoire.getId() != null) {
            throw new BadRequestAlertException("A new memoire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Memoire result = memoireRepository.save(memoire);
        return ResponseEntity.created(new URI("/api/memoires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /memoires : Updates an existing memoire.
     *
     * @param memoire the memoire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memoire,
     * or with status 400 (Bad Request) if the memoire is not valid,
     * or with status 500 (Internal Server Error) if the memoire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/memoires")
    @Timed
    public ResponseEntity<Memoire> updateMemoire(@Valid @RequestBody Memoire memoire) throws URISyntaxException {
        log.debug("REST request to update Memoire : {}", memoire);
        if (memoire.getId() == null) {
            return createMemoire(memoire);
        }
        Memoire result = memoireRepository.save(memoire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memoire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /memoires : get all the memoires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of memoires in body
     */
    @GetMapping("/memoires")
    @Timed
    public List<Memoire> getAllMemoires() {
        log.debug("REST request to get all Memoires");
        return memoireRepository.findAll();
        }

    /**
     * GET  /memoires/:id : get the "id" memoire.
     *
     * @param id the id of the memoire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memoire, or with status 404 (Not Found)
     */
    @GetMapping("/memoires/{id}")
    @Timed
    public ResponseEntity<Memoire> getMemoire(@PathVariable Long id) {
        log.debug("REST request to get Memoire : {}", id);
        Memoire memoire = memoireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memoire));
    }

    /**
     * DELETE  /memoires/:id : delete the "id" memoire.
     *
     * @param id the id of the memoire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/memoires/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemoire(@PathVariable Long id) {
        log.debug("REST request to delete Memoire : {}", id);
        memoireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
