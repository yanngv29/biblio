package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Revue;

import edu.ensim.biblio.repository.RevueRepository;
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
 * REST controller for managing Revue.
 */
@RestController
@RequestMapping("/api")
public class RevueResource {

    private final Logger log = LoggerFactory.getLogger(RevueResource.class);

    private static final String ENTITY_NAME = "revue";

    private final RevueRepository revueRepository;

    public RevueResource(RevueRepository revueRepository) {
        this.revueRepository = revueRepository;
    }

    /**
     * POST  /revues : Create a new revue.
     *
     * @param revue the revue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new revue, or with status 400 (Bad Request) if the revue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/revues")
    @Timed
    public ResponseEntity<Revue> createRevue(@Valid @RequestBody Revue revue) throws URISyntaxException {
        log.debug("REST request to save Revue : {}", revue);
        if (revue.getId() != null) {
            throw new BadRequestAlertException("A new revue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Revue result = revueRepository.save(revue);
        return ResponseEntity.created(new URI("/api/revues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /revues : Updates an existing revue.
     *
     * @param revue the revue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated revue,
     * or with status 400 (Bad Request) if the revue is not valid,
     * or with status 500 (Internal Server Error) if the revue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/revues")
    @Timed
    public ResponseEntity<Revue> updateRevue(@Valid @RequestBody Revue revue) throws URISyntaxException {
        log.debug("REST request to update Revue : {}", revue);
        if (revue.getId() == null) {
            return createRevue(revue);
        }
        Revue result = revueRepository.save(revue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, revue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /revues : get all the revues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of revues in body
     */
    @GetMapping("/revues")
    @Timed
    public List<Revue> getAllRevues() {
        log.debug("REST request to get all Revues");
        return revueRepository.findAll();
        }

    /**
     * GET  /revues/:id : get the "id" revue.
     *
     * @param id the id of the revue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the revue, or with status 404 (Not Found)
     */
    @GetMapping("/revues/{id}")
    @Timed
    public ResponseEntity<Revue> getRevue(@PathVariable Long id) {
        log.debug("REST request to get Revue : {}", id);
        Revue revue = revueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(revue));
    }

    /**
     * DELETE  /revues/:id : delete the "id" revue.
     *
     * @param id the id of the revue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/revues/{id}")
    @Timed
    public ResponseEntity<Void> deleteRevue(@PathVariable Long id) {
        log.debug("REST request to delete Revue : {}", id);
        revueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
