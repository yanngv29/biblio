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

    private static final String DEFAULT_ID_CONFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_ID_CONFERENCE = "BBBBBBBBBB";

    private static final TypeConference DEFAULT_TYPE = TypeConference.CONFERENCE;
    private static final TypeConference UPDATED_TYPE = TypeConference.COLLOQUE;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Audience DEFAULT_AUDIENCE = Audience.NATIONALE;
    private static final Audience UPDATED_AUDIENCE = Audience.INTERNATIONALE;

    private static final Boolean DEFAULT_COMITE_SELECTION = false;
    private static final Boolean UPDATED_COMITE_SELECTION = true;

    private static final String DEFAULT_EDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_EDITEUR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_SITE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_ACTES = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_ACTES = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS = "BBBBBBBBBB";

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
            .idConference(DEFAULT_ID_CONFERENCE)
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .audience(DEFAULT_AUDIENCE)
            .comiteSelection(DEFAULT_COMITE_SELECTION)
            .editeur(DEFAULT_EDITEUR)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .lienSite(DEFAULT_LIEN_SITE)
            .lienActes(DEFAULT_LIEN_ACTES)
            .divers(DEFAULT_DIVERS);
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
        assertThat(testConference.getIdConference()).isEqualTo(DEFAULT_ID_CONFERENCE);
        assertThat(testConference.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConference.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConference.getAudience()).isEqualTo(DEFAULT_AUDIENCE);
        assertThat(testConference.isComiteSelection()).isEqualTo(DEFAULT_COMITE_SELECTION);
        assertThat(testConference.getEditeur()).isEqualTo(DEFAULT_EDITEUR);
        assertThat(testConference.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testConference.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testConference.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testConference.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testConference.getLienSite()).isEqualTo(DEFAULT_LIEN_SITE);
        assertThat(testConference.getLienActes()).isEqualTo(DEFAULT_LIEN_ACTES);
        assertThat(testConference.getDivers()).isEqualTo(DEFAULT_DIVERS);
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
    public void checkIdConferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRepository.findAll().size();
        // set the field null
        conference.setIdConference(null);

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
            .andExpect(jsonPath("$.[*].idConference").value(hasItem(DEFAULT_ID_CONFERENCE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].audience").value(hasItem(DEFAULT_AUDIENCE.toString())))
            .andExpect(jsonPath("$.[*].comiteSelection").value(hasItem(DEFAULT_COMITE_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].lienSite").value(hasItem(DEFAULT_LIEN_SITE.toString())))
            .andExpect(jsonPath("$.[*].lienActes").value(hasItem(DEFAULT_LIEN_ACTES.toString())))
            .andExpect(jsonPath("$.[*].divers").value(hasItem(DEFAULT_DIVERS.toString())));
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
            .andExpect(jsonPath("$.idConference").value(DEFAULT_ID_CONFERENCE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.audience").value(DEFAULT_AUDIENCE.toString()))
            .andExpect(jsonPath("$.comiteSelection").value(DEFAULT_COMITE_SELECTION.booleanValue()))
            .andExpect(jsonPath("$.editeur").value(DEFAULT_EDITEUR.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()))
            .andExpect(jsonPath("$.lienSite").value(DEFAULT_LIEN_SITE.toString()))
            .andExpect(jsonPath("$.lienActes").value(DEFAULT_LIEN_ACTES.toString()))
            .andExpect(jsonPath("$.divers").value(DEFAULT_DIVERS.toString()));
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
            .idConference(UPDATED_ID_CONFERENCE)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .audience(UPDATED_AUDIENCE)
            .comiteSelection(UPDATED_COMITE_SELECTION)
            .editeur(UPDATED_EDITEUR)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .lienSite(UPDATED_LIEN_SITE)
            .lienActes(UPDATED_LIEN_ACTES)
            .divers(UPDATED_DIVERS);

        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConference)))
            .andExpect(status().isOk());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getIdConference()).isEqualTo(UPDATED_ID_CONFERENCE);
        assertThat(testConference.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConference.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConference.getAudience()).isEqualTo(UPDATED_AUDIENCE);
        assertThat(testConference.isComiteSelection()).isEqualTo(UPDATED_COMITE_SELECTION);
        assertThat(testConference.getEditeur()).isEqualTo(UPDATED_EDITEUR);
        assertThat(testConference.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testConference.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testConference.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testConference.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testConference.getLienSite()).isEqualTo(UPDATED_LIEN_SITE);
        assertThat(testConference.getLienActes()).isEqualTo(UPDATED_LIEN_ACTES);
        assertThat(testConference.getDivers()).isEqualTo(UPDATED_DIVERS);
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
