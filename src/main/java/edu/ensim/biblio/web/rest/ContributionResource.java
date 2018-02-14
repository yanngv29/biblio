package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Contribution;

import edu.ensim.biblio.repository.ContributionRepository;
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
 * REST controller for managing Contribution.
 */
@RestController
@RequestMapping("/api")
public class ContributionResource {

    private final Logger log = LoggerFactory.getLogger(ContributionResource.class);

    private static final String ENTITY_NAME = "contribution";

    private final ContributionRepository contributionRepository;

    public ContributionResource(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    /**
     * POST  /contributions : Create a new contribution.
     *
     * @param contribution the contribution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contribution, or with status 400 (Bad Request) if the contribution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contributions")
    @Timed
    public ResponseEntity<Contribution> createContribution(@Valid @RequestBody Contribution contribution) throws URISyntaxException {
        log.debug("REST request to save Contribution : {}", contribution);
        if (contribution.getId() != null) {
            throw new BadRequestAlertException("A new contribution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contribution result = contributionRepository.save(contribution);
        return ResponseEntity.created(new URI("/api/contributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contributions : Updates an existing contribution.
     *
     * @param contribution the contribution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contribution,
     * or with status 400 (Bad Request) if the contribution is not valid,
     * or with status 500 (Internal Server Error) if the contribution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contributions")
    @Timed
    public ResponseEntity<Contribution> updateContribution(@Valid @RequestBody Contribution contribution) throws URISyntaxException {
        log.debug("REST request to update Contribution : {}", contribution);
        if (contribution.getId() == null) {
            return createContribution(contribution);
        }
        Contribution result = contributionRepository.save(contribution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contribution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contributions : get all the contributions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contributions in body
     */
    @GetMapping("/contributions")
    @Timed
    public List<Contribution> getAllContributions() {
        log.debug("REST request to get all Contributions");
        return contributionRepository.findAll();
        }

    /**
     * GET  /contributions/:id : get the "id" contribution.
     *
     * @param id the id of the contribution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contribution, or with status 404 (Not Found)
     */
    @GetMapping("/contributions/{id}")
    @Timed
    public ResponseEntity<Contribution> getContribution(@PathVariable Long id) {
        log.debug("REST request to get Contribution : {}", id);
        Contribution contribution = contributionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contribution));
    }

    /**
     * DELETE  /contributions/:id : delete the "id" contribution.
     *
     * @param id the id of the contribution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contributions/{id}")
    @Timed
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        log.debug("REST request to delete Contribution : {}", id);
        contributionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
