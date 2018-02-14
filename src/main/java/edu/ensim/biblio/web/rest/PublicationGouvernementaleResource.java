package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.PublicationGouvernementale;

import edu.ensim.biblio.repository.PublicationGouvernementaleRepository;
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
 * REST controller for managing PublicationGouvernementale.
 */
@RestController
@RequestMapping("/api")
public class PublicationGouvernementaleResource {

    private final Logger log = LoggerFactory.getLogger(PublicationGouvernementaleResource.class);

    private static final String ENTITY_NAME = "publicationGouvernementale";

    private final PublicationGouvernementaleRepository publicationGouvernementaleRepository;

    public PublicationGouvernementaleResource(PublicationGouvernementaleRepository publicationGouvernementaleRepository) {
        this.publicationGouvernementaleRepository = publicationGouvernementaleRepository;
    }

    /**
     * POST  /publication-gouvernementales : Create a new publicationGouvernementale.
     *
     * @param publicationGouvernementale the publicationGouvernementale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicationGouvernementale, or with status 400 (Bad Request) if the publicationGouvernementale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publication-gouvernementales")
    @Timed
    public ResponseEntity<PublicationGouvernementale> createPublicationGouvernementale(@Valid @RequestBody PublicationGouvernementale publicationGouvernementale) throws URISyntaxException {
        log.debug("REST request to save PublicationGouvernementale : {}", publicationGouvernementale);
        if (publicationGouvernementale.getId() != null) {
            throw new BadRequestAlertException("A new publicationGouvernementale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicationGouvernementale result = publicationGouvernementaleRepository.save(publicationGouvernementale);
        return ResponseEntity.created(new URI("/api/publication-gouvernementales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publication-gouvernementales : Updates an existing publicationGouvernementale.
     *
     * @param publicationGouvernementale the publicationGouvernementale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicationGouvernementale,
     * or with status 400 (Bad Request) if the publicationGouvernementale is not valid,
     * or with status 500 (Internal Server Error) if the publicationGouvernementale couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publication-gouvernementales")
    @Timed
    public ResponseEntity<PublicationGouvernementale> updatePublicationGouvernementale(@Valid @RequestBody PublicationGouvernementale publicationGouvernementale) throws URISyntaxException {
        log.debug("REST request to update PublicationGouvernementale : {}", publicationGouvernementale);
        if (publicationGouvernementale.getId() == null) {
            return createPublicationGouvernementale(publicationGouvernementale);
        }
        PublicationGouvernementale result = publicationGouvernementaleRepository.save(publicationGouvernementale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicationGouvernementale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publication-gouvernementales : get all the publicationGouvernementales.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publicationGouvernementales in body
     */
    @GetMapping("/publication-gouvernementales")
    @Timed
    public List<PublicationGouvernementale> getAllPublicationGouvernementales() {
        log.debug("REST request to get all PublicationGouvernementales");
        return publicationGouvernementaleRepository.findAll();
        }

    /**
     * GET  /publication-gouvernementales/:id : get the "id" publicationGouvernementale.
     *
     * @param id the id of the publicationGouvernementale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicationGouvernementale, or with status 404 (Not Found)
     */
    @GetMapping("/publication-gouvernementales/{id}")
    @Timed
    public ResponseEntity<PublicationGouvernementale> getPublicationGouvernementale(@PathVariable Long id) {
        log.debug("REST request to get PublicationGouvernementale : {}", id);
        PublicationGouvernementale publicationGouvernementale = publicationGouvernementaleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(publicationGouvernementale));
    }

    /**
     * DELETE  /publication-gouvernementales/:id : delete the "id" publicationGouvernementale.
     *
     * @param id the id of the publicationGouvernementale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publication-gouvernementales/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicationGouvernementale(@PathVariable Long id) {
        log.debug("REST request to delete PublicationGouvernementale : {}", id);
        publicationGouvernementaleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
