package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Rapport;

import edu.ensim.biblio.repository.RapportRepository;
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
 * REST controller for managing Rapport.
 */
@RestController
@RequestMapping("/api")
public class RapportResource {

    private final Logger log = LoggerFactory.getLogger(RapportResource.class);

    private static final String ENTITY_NAME = "rapport";

    private final RapportRepository rapportRepository;

    public RapportResource(RapportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    /**
     * POST  /rapports : Create a new rapport.
     *
     * @param rapport the rapport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rapport, or with status 400 (Bad Request) if the rapport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rapports")
    @Timed
    public ResponseEntity<Rapport> createRapport(@Valid @RequestBody Rapport rapport) throws URISyntaxException {
        log.debug("REST request to save Rapport : {}", rapport);
        if (rapport.getId() != null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rapport result = rapportRepository.save(rapport);
        return ResponseEntity.created(new URI("/api/rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rapports : Updates an existing rapport.
     *
     * @param rapport the rapport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rapport,
     * or with status 400 (Bad Request) if the rapport is not valid,
     * or with status 500 (Internal Server Error) if the rapport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rapports")
    @Timed
    public ResponseEntity<Rapport> updateRapport(@Valid @RequestBody Rapport rapport) throws URISyntaxException {
        log.debug("REST request to update Rapport : {}", rapport);
        if (rapport.getId() == null) {
            return createRapport(rapport);
        }
        Rapport result = rapportRepository.save(rapport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rapport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rapports : get all the rapports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rapports in body
     */
    @GetMapping("/rapports")
    @Timed
    public List<Rapport> getAllRapports() {
        log.debug("REST request to get all Rapports");
        return rapportRepository.findAll();
        }

    /**
     * GET  /rapports/:id : get the "id" rapport.
     *
     * @param id the id of the rapport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rapport, or with status 404 (Not Found)
     */
    @GetMapping("/rapports/{id}")
    @Timed
    public ResponseEntity<Rapport> getRapport(@PathVariable Long id) {
        log.debug("REST request to get Rapport : {}", id);
        Rapport rapport = rapportRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rapport));
    }

    /**
     * DELETE  /rapports/:id : delete the "id" rapport.
     *
     * @param id the id of the rapport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rapports/{id}")
    @Timed
    public ResponseEntity<Void> deleteRapport(@PathVariable Long id) {
        log.debug("REST request to delete Rapport : {}", id);
        rapportRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
