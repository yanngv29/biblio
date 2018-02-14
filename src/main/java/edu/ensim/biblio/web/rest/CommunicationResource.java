package edu.ensim.biblio.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.ensim.biblio.domain.Communication;

import edu.ensim.biblio.repository.CommunicationRepository;
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
 * REST controller for managing Communication.
 */
@RestController
@RequestMapping("/api")
public class CommunicationResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationResource.class);

    private static final String ENTITY_NAME = "communication";

    private final CommunicationRepository communicationRepository;

    public CommunicationResource(CommunicationRepository communicationRepository) {
        this.communicationRepository = communicationRepository;
    }

    /**
     * POST  /communications : Create a new communication.
     *
     * @param communication the communication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communication, or with status 400 (Bad Request) if the communication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communications")
    @Timed
    public ResponseEntity<Communication> createCommunication(@Valid @RequestBody Communication communication) throws URISyntaxException {
        log.debug("REST request to save Communication : {}", communication);
        if (communication.getId() != null) {
            throw new BadRequestAlertException("A new communication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Communication result = communicationRepository.save(communication);
        return ResponseEntity.created(new URI("/api/communications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communications : Updates an existing communication.
     *
     * @param communication the communication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communication,
     * or with status 400 (Bad Request) if the communication is not valid,
     * or with status 500 (Internal Server Error) if the communication couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communications")
    @Timed
    public ResponseEntity<Communication> updateCommunication(@Valid @RequestBody Communication communication) throws URISyntaxException {
        log.debug("REST request to update Communication : {}", communication);
        if (communication.getId() == null) {
            return createCommunication(communication);
        }
        Communication result = communicationRepository.save(communication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communications : get all the communications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communications in body
     */
    @GetMapping("/communications")
    @Timed
    public List<Communication> getAllCommunications() {
        log.debug("REST request to get all Communications");
        return communicationRepository.findAll();
        }

    /**
     * GET  /communications/:id : get the "id" communication.
     *
     * @param id the id of the communication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communication, or with status 404 (Not Found)
     */
    @GetMapping("/communications/{id}")
    @Timed
    public ResponseEntity<Communication> getCommunication(@PathVariable Long id) {
        log.debug("REST request to get Communication : {}", id);
        Communication communication = communicationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(communication));
    }

    /**
     * DELETE  /communications/:id : delete the "id" communication.
     *
     * @param id the id of the communication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communications/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommunication(@PathVariable Long id) {
        log.debug("REST request to delete Communication : {}", id);
        communicationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
