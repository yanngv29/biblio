package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Conference;

import edu.ensim.biblio.repository.ConferenceRepository;
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
 * REST controller for managing Conference.
 */
@RestController
@RequestMapping("/api")
public class ConferenceResource {

    private final Logger log = LoggerFactory.getLogger(ConferenceResource.class);

    private static final String ENTITY_NAME = "conference";

    private final ConferenceRepository conferenceRepository;

    public ConferenceResource(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    /**
     * POST  /conferences : Create a new conference.
     *
     * @param conference the conference to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conference, or with status 400 (Bad Request) if the conference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conferences")
    @Timed
    public ResponseEntity<Conference> createConference(@Valid @RequestBody Conference conference) throws URISyntaxException {
        log.debug("REST request to save Conference : {}", conference);
        if (conference.getId() != null) {
            throw new BadRequestAlertException("A new conference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conference result = conferenceRepository.save(conference);
        return ResponseEntity.created(new URI("/api/conferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conferences : Updates an existing conference.
     *
     * @param conference the conference to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conference,
     * or with status 400 (Bad Request) if the conference is not valid,
     * or with status 500 (Internal Server Error) if the conference couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conferences")
    @Timed
    public ResponseEntity<Conference> updateConference(@Valid @RequestBody Conference conference) throws URISyntaxException {
        log.debug("REST request to update Conference : {}", conference);
        if (conference.getId() == null) {
            return createConference(conference);
        }
        Conference result = conferenceRepository.save(conference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conference.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conferences : get all the conferences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conferences in body
     */
    @GetMapping("/conferences")
    @Timed
    public List<Conference> getAllConferences() {
        log.debug("REST request to get all Conferences");
        return conferenceRepository.findAll();
        }

    /**
     * GET  /conferences/:id : get the "id" conference.
     *
     * @param id the id of the conference to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conference, or with status 404 (Not Found)
     */
    @GetMapping("/conferences/{id}")
    @Timed
    public ResponseEntity<Conference> getConference(@PathVariable Long id) {
        log.debug("REST request to get Conference : {}", id);
        Conference conference = conferenceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conference));
    }

    /**
     * DELETE  /conferences/:id : delete the "id" conference.
     *
     * @param id the id of the conference to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        log.debug("REST request to delete Conference : {}", id);
        conferenceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
