package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Acte;

import edu.ensim.biblio.repository.ActeRepository;
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
 * REST controller for managing Acte.
 */
@RestController
@RequestMapping("/api")
public class ActeResource {

    private final Logger log = LoggerFactory.getLogger(ActeResource.class);

    private static final String ENTITY_NAME = "acte";

    private final ActeRepository acteRepository;

    public ActeResource(ActeRepository acteRepository) {
        this.acteRepository = acteRepository;
    }

    /**
     * POST  /actes : Create a new acte.
     *
     * @param acte the acte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acte, or with status 400 (Bad Request) if the acte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actes")
    @Timed
    public ResponseEntity<Acte> createActe(@Valid @RequestBody Acte acte) throws URISyntaxException {
        log.debug("REST request to save Acte : {}", acte);
        if (acte.getId() != null) {
            throw new BadRequestAlertException("A new acte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acte result = acteRepository.save(acte);
        return ResponseEntity.created(new URI("/api/actes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actes : Updates an existing acte.
     *
     * @param acte the acte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acte,
     * or with status 400 (Bad Request) if the acte is not valid,
     * or with status 500 (Internal Server Error) if the acte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actes")
    @Timed
    public ResponseEntity<Acte> updateActe(@Valid @RequestBody Acte acte) throws URISyntaxException {
        log.debug("REST request to update Acte : {}", acte);
        if (acte.getId() == null) {
            return createActe(acte);
        }
        Acte result = acteRepository.save(acte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actes : get all the actes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actes in body
     */
    @GetMapping("/actes")
    @Timed
    public List<Acte> getAllActes() {
        log.debug("REST request to get all Actes");
        return acteRepository.findAll();
        }

    /**
     * GET  /actes/:id : get the "id" acte.
     *
     * @param id the id of the acte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acte, or with status 404 (Not Found)
     */
    @GetMapping("/actes/{id}")
    @Timed
    public ResponseEntity<Acte> getActe(@PathVariable Long id) {
        log.debug("REST request to get Acte : {}", id);
        Acte acte = acteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acte));
    }

    /**
     * DELETE  /actes/:id : delete the "id" acte.
     *
     * @param id the id of the acte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actes/{id}")
    @Timed
    public ResponseEntity<Void> deleteActe(@PathVariable Long id) {
        log.debug("REST request to delete Acte : {}", id);
        acteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
