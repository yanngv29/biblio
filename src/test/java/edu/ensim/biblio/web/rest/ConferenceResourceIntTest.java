package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Conference;
import edu.ensim.biblio.repository.ConferenceRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static edu.ensim.biblio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ensim.biblio.domain.enumeration.TypeConference;
import edu.ensim.biblio.domain.enumeration.Audience;
/**
 * Test class for the ConferenceResource REST controller.
 *
 * @see ConferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ConferenceResourceIntTest {

    private static final String DEFAULT_NOM_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONFERENCE = "BBBBBBBBBB";

    private static final TypeConference DEFAULT_TYPE_CONFERENCE = TypeConference.CONFERENCE;
    private static final TypeConference UPDATED_TYPE_CONFERENCE = TypeConference.COLLOQUE;

    private static final Audience DEFAULT_AUDIENCE_CONFERENCE = Audience.NATIONALE;
    private static final Audience UPDATED_AUDIENCE_CONFERENCE = Audience.INTERNATIONALE;

    private static final Boolean DEFAULT_COMITE_SELECTION_CONFERENCE = false;
    private static final Boolean UPDATED_COMITE_SELECTION_CONFERENCE = true;

    private static final Instant DEFAULT_DATE_DEBUT_CONFERENCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT_CONFERENCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN_CONFERENCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN_CONFERENCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VILLE_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CONFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_CONFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_CONFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_SITE_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_SITE_CONFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_CONFERENCE = "BBBBBBBBBB";

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConferenceMockMvc;

    private Conference conference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConferenceResource conferenceResource = new ConferenceResource(conferenceRepository);
        this.restConferenceMockMvc = MockMvcBuilders.standaloneSetup(conferenceResource)
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
    public static Conference createEntity(EntityManager em) {
        Conference conference = new Conference()
            .nomConference(DEFAULT_NOM_CONFERENCE)
            .typeConference(DEFAULT_TYPE_CONFERENCE)
            .audienceConference(DEFAULT_AUDIENCE_CONFERENCE)
            .comiteSelectionConference(DEFAULT_COMITE_SELECTION_CONFERENCE)
            .dateDebutConference(DEFAULT_DATE_DEBUT_CONFERENCE)
            .dateFinConference(DEFAULT_DATE_FIN_CONFERENCE)
            .villeConference(DEFAULT_VILLE_CONFERENCE)
            .paysConference(DEFAULT_PAYS_CONFERENCE)
            .langueConference(DEFAULT_LANGUE_CONFERENCE)
            .lienSiteConference(DEFAULT_LIEN_SITE_CONFERENCE)
            .diversConference(DEFAULT_DIVERS_CONFERENCE);
        return conference;
    }

    @Before
    public void initTest() {
        conference = createEntity(em);
    }

    @Test
    @Transactional
    public void createConference() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conference)))
            .andExpect(status().isCreated());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getNomConference()).isEqualTo(DEFAULT_NOM_CONFERENCE);
        assertThat(testConference.getTypeConference()).isEqualTo(DEFAULT_TYPE_CONFERENCE);
        assertThat(testConference.getAudienceConference()).isEqualTo(DEFAULT_AUDIENCE_CONFERENCE);
        assertThat(testConference.isComiteSelectionConference()).isEqualTo(DEFAULT_COMITE_SELECTION_CONFERENCE);
        assertThat(testConference.getDateDebutConference()).isEqualTo(DEFAULT_DATE_DEBUT_CONFERENCE);
        assertThat(testConference.getDateFinConference()).isEqualTo(DEFAULT_DATE_FIN_CONFERENCE);
        assertThat(testConference.getVilleConference()).isEqualTo(DEFAULT_VILLE_CONFERENCE);
        assertThat(testConference.getPaysConference()).isEqualTo(DEFAULT_PAYS_CONFERENCE);
        assertThat(testConference.getLangueConference()).isEqualTo(DEFAULT_LANGUE_CONFERENCE);
        assertThat(testConference.getLienSiteConference()).isEqualTo(DEFAULT_LIEN_SITE_CONFERENCE);
        assertThat(testConference.getDiversConference()).isEqualTo(DEFAULT_DIVERS_CONFERENCE);
    }

    @Test
    @Transactional
    public void createConferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference with an existing ID
        conference.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conference)))
            .andExpect(status().isBadRequest());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomConferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRepository.findAll().size();
        // set the field null
        conference.setNomConference(null);

        // Create the Conference, which fails.

        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conference)))
            .andExpect(status().isBadRequest());

        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConferences() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get all the conferenceList
        restConferenceMockMvc.perform(get("/api/conferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conference.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomConference").value(hasItem(DEFAULT_NOM_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].typeConference").value(hasItem(DEFAULT_TYPE_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].audienceConference").value(hasItem(DEFAULT_AUDIENCE_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].comiteSelectionConference").value(hasItem(DEFAULT_COMITE_SELECTION_CONFERENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateDebutConference").value(hasItem(DEFAULT_DATE_DEBUT_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].dateFinConference").value(hasItem(DEFAULT_DATE_FIN_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].villeConference").value(hasItem(DEFAULT_VILLE_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].paysConference").value(hasItem(DEFAULT_PAYS_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].langueConference").value(hasItem(DEFAULT_LANGUE_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].lienSiteConference").value(hasItem(DEFAULT_LIEN_SITE_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].diversConference").value(hasItem(DEFAULT_DIVERS_CONFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", conference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conference.getId().intValue()))
            .andExpect(jsonPath("$.nomConference").value(DEFAULT_NOM_CONFERENCE.toString()))
            .andExpect(jsonPath("$.typeConference").value(DEFAULT_TYPE_CONFERENCE.toString()))
            .andExpect(jsonPath("$.audienceConference").value(DEFAULT_AUDIENCE_CONFERENCE.toString()))
            .andExpect(jsonPath("$.comiteSelectionConference").value(DEFAULT_COMITE_SELECTION_CONFERENCE.booleanValue()))
            .andExpect(jsonPath("$.dateDebutConference").value(DEFAULT_DATE_DEBUT_CONFERENCE.toString()))
            .andExpect(jsonPath("$.dateFinConference").value(DEFAULT_DATE_FIN_CONFERENCE.toString()))
            .andExpect(jsonPath("$.villeConference").value(DEFAULT_VILLE_CONFERENCE.toString()))
            .andExpect(jsonPath("$.paysConference").value(DEFAULT_PAYS_CONFERENCE.toString()))
            .andExpect(jsonPath("$.langueConference").value(DEFAULT_LANGUE_CONFERENCE.toString()))
            .andExpect(jsonPath("$.lienSiteConference").value(DEFAULT_LIEN_SITE_CONFERENCE.toString()))
            .andExpect(jsonPath("$.diversConference").value(DEFAULT_DIVERS_CONFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConference() throws Exception {
        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);
        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Update the conference
        Conference updatedConference = conferenceRepository.findOne(conference.getId());
        // Disconnect from session so that the updates on updatedConference are not directly saved in db
        em.detach(updatedConference);
        updatedConference
            .nomConference(UPDATED_NOM_CONFERENCE)
            .typeConference(UPDATED_TYPE_CONFERENCE)
            .audienceConference(UPDATED_AUDIENCE_CONFERENCE)
            .comiteSelectionConference(UPDATED_COMITE_SELECTION_CONFERENCE)
            .dateDebutConference(UPDATED_DATE_DEBUT_CONFERENCE)
            .dateFinConference(UPDATED_DATE_FIN_CONFERENCE)
            .villeConference(UPDATED_VILLE_CONFERENCE)
            .paysConference(UPDATED_PAYS_CONFERENCE)
            .langueConference(UPDATED_LANGUE_CONFERENCE)
            .lienSiteConference(UPDATED_LIEN_SITE_CONFERENCE)
            .diversConference(UPDATED_DIVERS_CONFERENCE);

        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConference)))
            .andExpect(status().isOk());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getNomConference()).isEqualTo(UPDATED_NOM_CONFERENCE);
        assertThat(testConference.getTypeConference()).isEqualTo(UPDATED_TYPE_CONFERENCE);
        assertThat(testConference.getAudienceConference()).isEqualTo(UPDATED_AUDIENCE_CONFERENCE);
        assertThat(testConference.isComiteSelectionConference()).isEqualTo(UPDATED_COMITE_SELECTION_CONFERENCE);
        assertThat(testConference.getDateDebutConference()).isEqualTo(UPDATED_DATE_DEBUT_CONFERENCE);
        assertThat(testConference.getDateFinConference()).isEqualTo(UPDATED_DATE_FIN_CONFERENCE);
        assertThat(testConference.getVilleConference()).isEqualTo(UPDATED_VILLE_CONFERENCE);
        assertThat(testConference.getPaysConference()).isEqualTo(UPDATED_PAYS_CONFERENCE);
        assertThat(testConference.getLangueConference()).isEqualTo(UPDATED_LANGUE_CONFERENCE);
        assertThat(testConference.getLienSiteConference()).isEqualTo(UPDATED_LIEN_SITE_CONFERENCE);
        assertThat(testConference.getDiversConference()).isEqualTo(UPDATED_DIVERS_CONFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingConference() throws Exception {
        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Create the Conference

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conference)))
            .andExpect(status().isCreated());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);
        int databaseSizeBeforeDelete = conferenceRepository.findAll().size();

        // Get the conference
        restConferenceMockMvc.perform(delete("/api/conferences/{id}", conference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conference.class);
        Conference conference1 = new Conference();
        conference1.setId(1L);
        Conference conference2 = new Conference();
        conference2.setId(conference1.getId());
        assertThat(conference1).isEqualTo(conference2);
        conference2.setId(2L);
        assertThat(conference1).isNotEqualTo(conference2);
        conference1.setId(null);
        assertThat(conference1).isNotEqualTo(conference2);
    }
}
