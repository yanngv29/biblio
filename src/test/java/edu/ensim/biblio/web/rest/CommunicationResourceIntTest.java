package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Communication;
import edu.ensim.biblio.repository.CommunicationRepository;
import edu.ensim.biblio.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static edu.ensim.biblio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ensim.biblio.domain.enumeration.TypeCommunication;
/**
 * Test class for the CommunicationResource REST controller.
 *
 * @see CommunicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class CommunicationResourceIntTest {

    private static final String DEFAULT_TITRE_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_COMMUNICATION = "BBBBBBBBBB";

    private static final TypeCommunication DEFAULT_TYPE_COMMUNICATION = TypeCommunication.AFFICHE;
    private static final TypeCommunication UPDATED_TYPE_COMMUNICATION = TypeCommunication.ATELIER;

    private static final String DEFAULT_LANGUE_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_COMMUNICATION = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_COMMUNICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DOI_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_DOI_COMMUNICATION = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_HAL_COMMUNICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_COMMUNICATION = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_COMMUNICATION = "BBBBBBBBBB";

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommunicationMockMvc;

    private Communication communication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommunicationResource communicationResource = new CommunicationResource(communicationRepository);
        this.restCommunicationMockMvc = MockMvcBuilders.standaloneSetup(communicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Communication createEntity(EntityManager em) {
        Communication communication = new Communication()
            .titreCommunication(DEFAULT_TITRE_COMMUNICATION)
            .typeCommunication(DEFAULT_TYPE_COMMUNICATION)
            .langueCommunication(DEFAULT_LANGUE_COMMUNICATION)
            .lienCommunication(DEFAULT_LIEN_COMMUNICATION)
            .doiCommunication(DEFAULT_DOI_COMMUNICATION)
            .halCommunication(DEFAULT_HAL_COMMUNICATION)
            .diversCommunication(DEFAULT_DIVERS_COMMUNICATION);
        return communication;
    }

    @Before
    public void initTest() {
        communication = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunication() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();

        // Create the Communication
        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isCreated());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate + 1);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getTitreCommunication()).isEqualTo(DEFAULT_TITRE_COMMUNICATION);
        assertThat(testCommunication.getTypeCommunication()).isEqualTo(DEFAULT_TYPE_COMMUNICATION);
        assertThat(testCommunication.getLangueCommunication()).isEqualTo(DEFAULT_LANGUE_COMMUNICATION);
        assertThat(testCommunication.getLienCommunication()).isEqualTo(DEFAULT_LIEN_COMMUNICATION);
        assertThat(testCommunication.getDoiCommunication()).isEqualTo(DEFAULT_DOI_COMMUNICATION);
        assertThat(testCommunication.getHalCommunication()).isEqualTo(DEFAULT_HAL_COMMUNICATION);
        assertThat(testCommunication.getDiversCommunication()).isEqualTo(DEFAULT_DIVERS_COMMUNICATION);
    }

    @Test
    @Transactional
    public void createCommunicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();

        // Create the Communication with an existing ID
        communication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationMockMvc.perform(post("/api/communications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommunications() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList
        restCommunicationMockMvc.perform(get("/api/communications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communication.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreCommunication").value(hasItem(DEFAULT_TITRE_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].typeCommunication").value(hasItem(DEFAULT_TYPE_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].langueCommunication").value(hasItem(DEFAULT_LANGUE_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].lienCommunication").value(hasItem(DEFAULT_LIEN_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].doiCommunication").value(hasItem(DEFAULT_DOI_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].halCommunication").value(hasItem(DEFAULT_HAL_COMMUNICATION.toString())))
            .andExpect(jsonPath("$.[*].diversCommunication").value(hasItem(DEFAULT_DIVERS_COMMUNICATION.toString())));
    }

    @Test
    @Transactional
    public void getCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", communication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(communication.getId().intValue()))
            .andExpect(jsonPath("$.titreCommunication").value(DEFAULT_TITRE_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.typeCommunication").value(DEFAULT_TYPE_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.langueCommunication").value(DEFAULT_LANGUE_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.lienCommunication").value(DEFAULT_LIEN_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.doiCommunication").value(DEFAULT_DOI_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.halCommunication").value(DEFAULT_HAL_COMMUNICATION.toString()))
            .andExpect(jsonPath("$.diversCommunication").value(DEFAULT_DIVERS_COMMUNICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommunication() throws Exception {
        // Get the communication
        restCommunicationMockMvc.perform(get("/api/communications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication
        Communication updatedCommunication = communicationRepository.findOne(communication.getId());
        // Disconnect from session so that the updates on updatedCommunication are not directly saved in db
        em.detach(updatedCommunication);
        updatedCommunication
            .titreCommunication(UPDATED_TITRE_COMMUNICATION)
            .typeCommunication(UPDATED_TYPE_COMMUNICATION)
            .langueCommunication(UPDATED_LANGUE_COMMUNICATION)
            .lienCommunication(UPDATED_LIEN_COMMUNICATION)
            .doiCommunication(UPDATED_DOI_COMMUNICATION)
            .halCommunication(UPDATED_HAL_COMMUNICATION)
            .diversCommunication(UPDATED_DIVERS_COMMUNICATION);

        restCommunicationMockMvc.perform(put("/api/communications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunication)))
            .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getTitreCommunication()).isEqualTo(UPDATED_TITRE_COMMUNICATION);
        assertThat(testCommunication.getTypeCommunication()).isEqualTo(UPDATED_TYPE_COMMUNICATION);
        assertThat(testCommunication.getLangueCommunication()).isEqualTo(UPDATED_LANGUE_COMMUNICATION);
        assertThat(testCommunication.getLienCommunication()).isEqualTo(UPDATED_LIEN_COMMUNICATION);
        assertThat(testCommunication.getDoiCommunication()).isEqualTo(UPDATED_DOI_COMMUNICATION);
        assertThat(testCommunication.getHalCommunication()).isEqualTo(UPDATED_HAL_COMMUNICATION);
        assertThat(testCommunication.getDiversCommunication()).isEqualTo(UPDATED_DIVERS_COMMUNICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Create the Communication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommunicationMockMvc.perform(put("/api/communications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isCreated());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);
        int databaseSizeBeforeDelete = communicationRepository.findAll().size();

        // Get the communication
        restCommunicationMockMvc.perform(delete("/api/communications/{id}", communication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Communication.class);
        Communication communication1 = new Communication();
        communication1.setId(1L);
        Communication communication2 = new Communication();
        communication2.setId(communication1.getId());
        assertThat(communication1).isEqualTo(communication2);
        communication2.setId(2L);
        assertThat(communication1).isNotEqualTo(communication2);
        communication1.setId(null);
        assertThat(communication1).isNotEqualTo(communication2);
    }
}
