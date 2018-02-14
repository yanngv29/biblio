package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Revue;
import edu.ensim.biblio.repository.RevueRepository;
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

import edu.ensim.biblio.domain.enumeration.Audience;
/**
 * Test class for the RevueResource REST controller.
 *
 * @see RevueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class RevueResourceIntTest {

    private static final String DEFAULT_ID_REVUE = "AAAAAAAAAA";
    private static final String UPDATED_ID_REVUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Audience DEFAULT_AUDIENCE = Audience.NATIONALE;
    private static final Audience UPDATED_AUDIENCE = Audience.INTERNATIONALE;

    private static final Boolean DEFAULT_COMITE_SELECTION = false;
    private static final Boolean UPDATED_COMITE_SELECTION = true;

    private static final String DEFAULT_MOIS = "AAAAAAAAAA";
    private static final String UPDATED_MOIS = "BBBBBBBBBB";

    private static final String DEFAULT_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ANNEE = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS = "BBBBBBBBBB";

    @Autowired
    private RevueRepository revueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRevueMockMvc;

    private Revue revue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RevueResource revueResource = new RevueResource(revueRepository);
        this.restRevueMockMvc = MockMvcBuilders.standaloneSetup(revueResource)
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
    public static Revue createEntity(EntityManager em) {
        Revue revue = new Revue()
            .idRevue(DEFAULT_ID_REVUE)
            .nom(DEFAULT_NOM)
            .audience(DEFAULT_AUDIENCE)
            .comiteSelection(DEFAULT_COMITE_SELECTION)
            .mois(DEFAULT_MOIS)
            .annee(DEFAULT_ANNEE)
            .volume(DEFAULT_VOLUME)
            .numeroVolume(DEFAULT_NUMERO_VOLUME)
            .lieu(DEFAULT_LIEU)
            .lien(DEFAULT_LIEN)
            .divers(DEFAULT_DIVERS);
        return revue;
    }

    @Before
    public void initTest() {
        revue = createEntity(em);
    }

    @Test
    @Transactional
    public void createRevue() throws Exception {
        int databaseSizeBeforeCreate = revueRepository.findAll().size();

        // Create the Revue
        restRevueMockMvc.perform(post("/api/revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(revue)))
            .andExpect(status().isCreated());

        // Validate the Revue in the database
        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeCreate + 1);
        Revue testRevue = revueList.get(revueList.size() - 1);
        assertThat(testRevue.getIdRevue()).isEqualTo(DEFAULT_ID_REVUE);
        assertThat(testRevue.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRevue.getAudience()).isEqualTo(DEFAULT_AUDIENCE);
        assertThat(testRevue.isComiteSelection()).isEqualTo(DEFAULT_COMITE_SELECTION);
        assertThat(testRevue.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testRevue.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testRevue.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testRevue.getNumeroVolume()).isEqualTo(DEFAULT_NUMERO_VOLUME);
        assertThat(testRevue.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testRevue.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testRevue.getDivers()).isEqualTo(DEFAULT_DIVERS);
    }

    @Test
    @Transactional
    public void createRevueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = revueRepository.findAll().size();

        // Create the Revue with an existing ID
        revue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevueMockMvc.perform(post("/api/revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(revue)))
            .andExpect(status().isBadRequest());

        // Validate the Revue in the database
        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdRevueIsRequired() throws Exception {
        int databaseSizeBeforeTest = revueRepository.findAll().size();
        // set the field null
        revue.setIdRevue(null);

        // Create the Revue, which fails.

        restRevueMockMvc.perform(post("/api/revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(revue)))
            .andExpect(status().isBadRequest());

        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRevues() throws Exception {
        // Initialize the database
        revueRepository.saveAndFlush(revue);

        // Get all the revueList
        restRevueMockMvc.perform(get("/api/revues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revue.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRevue").value(hasItem(DEFAULT_ID_REVUE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].audience").value(hasItem(DEFAULT_AUDIENCE.toString())))
            .andExpect(jsonPath("$.[*].comiteSelection").value(hasItem(DEFAULT_COMITE_SELECTION.booleanValue())))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].numeroVolume").value(hasItem(DEFAULT_NUMERO_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].divers").value(hasItem(DEFAULT_DIVERS.toString())));
    }

    @Test
    @Transactional
    public void getRevue() throws Exception {
        // Initialize the database
        revueRepository.saveAndFlush(revue);

        // Get the revue
        restRevueMockMvc.perform(get("/api/revues/{id}", revue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(revue.getId().intValue()))
            .andExpect(jsonPath("$.idRevue").value(DEFAULT_ID_REVUE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.audience").value(DEFAULT_AUDIENCE.toString()))
            .andExpect(jsonPath("$.comiteSelection").value(DEFAULT_COMITE_SELECTION.booleanValue()))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.toString()))
            .andExpect(jsonPath("$.numeroVolume").value(DEFAULT_NUMERO_VOLUME.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.divers").value(DEFAULT_DIVERS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRevue() throws Exception {
        // Get the revue
        restRevueMockMvc.perform(get("/api/revues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRevue() throws Exception {
        // Initialize the database
        revueRepository.saveAndFlush(revue);
        int databaseSizeBeforeUpdate = revueRepository.findAll().size();

        // Update the revue
        Revue updatedRevue = revueRepository.findOne(revue.getId());
        // Disconnect from session so that the updates on updatedRevue are not directly saved in db
        em.detach(updatedRevue);
        updatedRevue
            .idRevue(UPDATED_ID_REVUE)
            .nom(UPDATED_NOM)
            .audience(UPDATED_AUDIENCE)
            .comiteSelection(UPDATED_COMITE_SELECTION)
            .mois(UPDATED_MOIS)
            .annee(UPDATED_ANNEE)
            .volume(UPDATED_VOLUME)
            .numeroVolume(UPDATED_NUMERO_VOLUME)
            .lieu(UPDATED_LIEU)
            .lien(UPDATED_LIEN)
            .divers(UPDATED_DIVERS);

        restRevueMockMvc.perform(put("/api/revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRevue)))
            .andExpect(status().isOk());

        // Validate the Revue in the database
        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeUpdate);
        Revue testRevue = revueList.get(revueList.size() - 1);
        assertThat(testRevue.getIdRevue()).isEqualTo(UPDATED_ID_REVUE);
        assertThat(testRevue.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRevue.getAudience()).isEqualTo(UPDATED_AUDIENCE);
        assertThat(testRevue.isComiteSelection()).isEqualTo(UPDATED_COMITE_SELECTION);
        assertThat(testRevue.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testRevue.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testRevue.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testRevue.getNumeroVolume()).isEqualTo(UPDATED_NUMERO_VOLUME);
        assertThat(testRevue.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testRevue.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testRevue.getDivers()).isEqualTo(UPDATED_DIVERS);
    }

    @Test
    @Transactional
    public void updateNonExistingRevue() throws Exception {
        int databaseSizeBeforeUpdate = revueRepository.findAll().size();

        // Create the Revue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRevueMockMvc.perform(put("/api/revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(revue)))
            .andExpect(status().isCreated());

        // Validate the Revue in the database
        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRevue() throws Exception {
        // Initialize the database
        revueRepository.saveAndFlush(revue);
        int databaseSizeBeforeDelete = revueRepository.findAll().size();

        // Get the revue
        restRevueMockMvc.perform(delete("/api/revues/{id}", revue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Revue> revueList = revueRepository.findAll();
        assertThat(revueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Revue.class);
        Revue revue1 = new Revue();
        revue1.setId(1L);
        Revue revue2 = new Revue();
        revue2.setId(revue1.getId());
        assertThat(revue1).isEqualTo(revue2);
        revue2.setId(2L);
        assertThat(revue1).isNotEqualTo(revue2);
        revue1.setId(null);
        assertThat(revue1).isNotEqualTo(revue2);
    }
}
