package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.NumeroRevue;

import edu.ensim.biblio.repository.NumeroRevueRepository;
import edu.ensim.biblio.web.rest.errors.BadRequestAlertException;
import edu.ensim.biblio.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NumeroRevue.
 */
@RestController
@RequestMapping("/api")
public class NumeroRevueResource {

    private final Logger log = LoggerFactory.getLogger(NumeroRevueResource.class);

    private static final String ENTITY_NAME = "numeroRevue";

    private final NumeroRevueRepository numeroRevueRepository;

    public NumeroRevueResource(NumeroRevueRepository numeroRevueRepository) {
        this.numeroRevueRepository = numeroRevueRepository;
    }

    /**
     * POST  /numero-revues : Create a new numeroRevue.
     *
     * @param numeroRevue the numeroRevue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new numeroRevue, or with status 400 (Bad Request) if the numeroRevue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/numero-revues")
    @Timed
    public ResponseEntity<NumeroRevue> createNumeroRevue(@RequestBody NumeroRevue numeroRevue) throws URISyntaxException {
        log.debug("REST request to save NumeroRevue : {}", numeroRevue);
        if (numeroRevue.getId() != null) {
            throw new BadRequestAlertException("A new numeroRevue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumeroRevue result = numeroRevueRepository.save(numeroRevue);
        return ResponseEntity.created(new URI("/api/numero-revues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /numero-revues : Updates an existing numeroRevue.
     *
     * @param numeroRevue the numeroRevue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated numeroRevue,
     * or with status 400 (Bad Request) if the numeroRevue is not valid,
     * or with status 500 (Internal Server Error) if the numeroRevue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/numero-revues")
    @Timed
    public ResponseEntity<NumeroRevue> updateNumeroRevue(@RequestBody NumeroRevue numeroRevue) throws URISyntaxException {
        log.debug("REST request to update NumeroRevue : {}", numeroRevue);
        if (numeroRevue.getId() == null) {
            return createNumeroRevue(numeroRevue);
        }
        NumeroRevue result = numeroRevueRepository.save(numeroRevue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, numeroRevue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /numero-revues : get all the numeroRevues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of numeroRevues in body
     */
    @GetMapping("/numero-revues")
    @Timed
    public List<NumeroRevue> getAllNumeroRevues() {
        log.debug("REST request to get all NumeroRevues");
        return numeroRevueRepository.findAll();
        }

    /**
     * GET  /numero-revues/:id : get the "id" numeroRevue.
     *
     * @param id the id of the numeroRevue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the numeroRevue, or with status 404 (Not Found)
     */
    @GetMapping("/numero-revues/{id}")
    @Timed
    public ResponseEntity<NumeroRevue> getNumeroRevue(@PathVariable Long id) {
        log.debug("REST request to get NumeroRevue : {}", id);
        NumeroRevue numeroRevue = numeroRevueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(numeroRevue));
    }

    /**
     * DELETE  /numero-revues/:id : delete the "id" numeroRevue.
     *
     * @param id the id of the numeroRevue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/numero-revues/{id}")
    @Timed
    public ResponseEntity<Void> deleteNumeroRevue(@PathVariable Long id) {
        log.debug("REST request to delete NumeroRevue : {}", id);
        numeroRevueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
