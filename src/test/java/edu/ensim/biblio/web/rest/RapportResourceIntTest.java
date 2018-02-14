package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Rapport;
import edu.ensim.biblio.repository.RapportRepository;
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

import edu.ensim.biblio.domain.enumeration.TypeRapport;
/**
 * Test class for the RapportResource REST controller.
 *
 * @see RapportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class RapportResourceIntTest {

    private static final String DEFAULT_ID_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMOIRE = "BBBBBBBBBB";

    private static final TypeRapport DEFAULT_TYPE = TypeRapport.RECHERCHE;
    private static final TypeRapport UPDATED_TYPE = TypeRapport.PROJET;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION = "BBBBBBBBBB";

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRapportMockMvc;

    private Rapport rapport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RapportResource rapportResource = new RapportResource(rapportRepository);
        this.restRapportMockMvc = MockMvcBuilders.standaloneSetup(rapportResource)
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
    public static Rapport createEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .idMemoire(DEFAULT_ID_MEMOIRE)
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .lieu(DEFAULT_LIEU)
            .maisonEdition(DEFAULT_MAISON_EDITION);
        return rapport;
    }

    @Before
    public void initTest() {
        rapport = createEntity(em);
    }

    @Test
    @Transactional
    public void createRapport() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isCreated());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate + 1);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getIdMemoire()).isEqualTo(DEFAULT_ID_MEMOIRE);
        assertThat(testRapport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRapport.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRapport.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testRapport.getMaisonEdition()).isEqualTo(DEFAULT_MAISON_EDITION);
    }

    @Test
    @Transactional
    public void createRapportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport with an existing ID
        rapport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdMemoireIsRequired() throws Exception {
        int databaseSizeBeforeTest = rapportRepository.findAll().size();
        // set the field null
        rapport.setIdMemoire(null);

        // Create the Rapport, which fails.

        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isBadRequest());

        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRapports() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get all the rapportList
        restRapportMockMvc.perform(get("/api/rapports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMemoire").value(hasItem(DEFAULT_ID_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].maisonEdition").value(hasItem(DEFAULT_MAISON_EDITION.toString())));
    }

    @Test
    @Transactional
    public void getRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rapport.getId().intValue()))
            .andExpect(jsonPath("$.idMemoire").value(DEFAULT_ID_MEMOIRE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.maisonEdition").value(DEFAULT_MAISON_EDITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRapport() throws Exception {
        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport
        Rapport updatedRapport = rapportRepository.findOne(rapport.getId());
        // Disconnect from session so that the updates on updatedRapport are not directly saved in db
        em.detach(updatedRapport);
        updatedRapport
            .idMemoire(UPDATED_ID_MEMOIRE)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .lieu(UPDATED_LIEU)
            .maisonEdition(UPDATED_MAISON_EDITION);

        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRapport)))
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getIdMemoire()).isEqualTo(UPDATED_ID_MEMOIRE);
        assertThat(testRapport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRapport.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRapport.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testRapport.getMaisonEdition()).isEqualTo(UPDATED_MAISON_EDITION);
    }

    @Test
    @Transactional
    public void updateNonExistingRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Create the Rapport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isCreated());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);
        int databaseSizeBeforeDelete = rapportRepository.findAll().size();

        // Get the rapport
        restRapportMockMvc.perform(delete("/api/rapports/{id}", rapport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rapport.class);
        Rapport rapport1 = new Rapport();
        rapport1.setId(1L);
        Rapport rapport2 = new Rapport();
        rapport2.setId(rapport1.getId());
        assertThat(rapport1).isEqualTo(rapport2);
        rapport2.setId(2L);
        assertThat(rapport1).isNotEqualTo(rapport2);
        rapport1.setId(null);
        assertThat(rapport1).isNotEqualTo(rapport2);
    }
}
