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

    private static final String DEFAULT_TITRE_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_TITRE_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_TITRE_RAPPORT = "BBBBBBBBBB";

    private static final TypeRapport DEFAULT_TYPE_RAPPORT = TypeRapport.RECHERCHE;
    private static final TypeRapport UPDATED_TYPE_RAPPORT = TypeRapport.PROJET;

    private static final Instant DEFAULT_DATE_RAPPORT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RAPPORT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_DOI_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_DOI_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_HAL_RAPPORT = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_OUVRAGE_RAPPORT = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_OUVRAGE_RAPPORT = "BBBBBBBBBB";

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
            .titreRapport(DEFAULT_TITRE_RAPPORT)
            .sousTitreRapport(DEFAULT_SOUS_TITRE_RAPPORT)
            .typeRapport(DEFAULT_TYPE_RAPPORT)
            .dateRapport(DEFAULT_DATE_RAPPORT)
            .lieuRapport(DEFAULT_LIEU_RAPPORT)
            .maisonEditionRapport(DEFAULT_MAISON_EDITION_RAPPORT)
            .langueRapport(DEFAULT_LANGUE_RAPPORT)
            .lienRapport(DEFAULT_LIEN_RAPPORT)
            .doiRapport(DEFAULT_DOI_RAPPORT)
            .halRapport(DEFAULT_HAL_RAPPORT)
            .diversOuvrageRapport(DEFAULT_DIVERS_OUVRAGE_RAPPORT);
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
        assertThat(testRapport.getTitreRapport()).isEqualTo(DEFAULT_TITRE_RAPPORT);
        assertThat(testRapport.getSousTitreRapport()).isEqualTo(DEFAULT_SOUS_TITRE_RAPPORT);
        assertThat(testRapport.getTypeRapport()).isEqualTo(DEFAULT_TYPE_RAPPORT);
        assertThat(testRapport.getDateRapport()).isEqualTo(DEFAULT_DATE_RAPPORT);
        assertThat(testRapport.getLieuRapport()).isEqualTo(DEFAULT_LIEU_RAPPORT);
        assertThat(testRapport.getMaisonEditionRapport()).isEqualTo(DEFAULT_MAISON_EDITION_RAPPORT);
        assertThat(testRapport.getLangueRapport()).isEqualTo(DEFAULT_LANGUE_RAPPORT);
        assertThat(testRapport.getLienRapport()).isEqualTo(DEFAULT_LIEN_RAPPORT);
        assertThat(testRapport.getDoiRapport()).isEqualTo(DEFAULT_DOI_RAPPORT);
        assertThat(testRapport.getHalRapport()).isEqualTo(DEFAULT_HAL_RAPPORT);
        assertThat(testRapport.getDiversOuvrageRapport()).isEqualTo(DEFAULT_DIVERS_OUVRAGE_RAPPORT);
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
    public void checkTitreRapportIsRequired() throws Exception {
        int databaseSizeBeforeTest = rapportRepository.findAll().size();
        // set the field null
        rapport.setTitreRapport(null);

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
            .andExpect(jsonPath("$.[*].titreRapport").value(hasItem(DEFAULT_TITRE_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].sousTitreRapport").value(hasItem(DEFAULT_SOUS_TITRE_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].typeRapport").value(hasItem(DEFAULT_TYPE_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].dateRapport").value(hasItem(DEFAULT_DATE_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].lieuRapport").value(hasItem(DEFAULT_LIEU_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].maisonEditionRapport").value(hasItem(DEFAULT_MAISON_EDITION_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].langueRapport").value(hasItem(DEFAULT_LANGUE_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].lienRapport").value(hasItem(DEFAULT_LIEN_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].doiRapport").value(hasItem(DEFAULT_DOI_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].halRapport").value(hasItem(DEFAULT_HAL_RAPPORT.toString())))
            .andExpect(jsonPath("$.[*].diversOuvrageRapport").value(hasItem(DEFAULT_DIVERS_OUVRAGE_RAPPORT.toString())));
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
            .andExpect(jsonPath("$.titreRapport").value(DEFAULT_TITRE_RAPPORT.toString()))
            .andExpect(jsonPath("$.sousTitreRapport").value(DEFAULT_SOUS_TITRE_RAPPORT.toString()))
            .andExpect(jsonPath("$.typeRapport").value(DEFAULT_TYPE_RAPPORT.toString()))
            .andExpect(jsonPath("$.dateRapport").value(DEFAULT_DATE_RAPPORT.toString()))
            .andExpect(jsonPath("$.lieuRapport").value(DEFAULT_LIEU_RAPPORT.toString()))
            .andExpect(jsonPath("$.maisonEditionRapport").value(DEFAULT_MAISON_EDITION_RAPPORT.toString()))
            .andExpect(jsonPath("$.langueRapport").value(DEFAULT_LANGUE_RAPPORT.toString()))
            .andExpect(jsonPath("$.lienRapport").value(DEFAULT_LIEN_RAPPORT.toString()))
            .andExpect(jsonPath("$.doiRapport").value(DEFAULT_DOI_RAPPORT.toString()))
            .andExpect(jsonPath("$.halRapport").value(DEFAULT_HAL_RAPPORT.toString()))
            .andExpect(jsonPath("$.diversOuvrageRapport").value(DEFAULT_DIVERS_OUVRAGE_RAPPORT.toString()));
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
            .titreRapport(UPDATED_TITRE_RAPPORT)
            .sousTitreRapport(UPDATED_SOUS_TITRE_RAPPORT)
            .typeRapport(UPDATED_TYPE_RAPPORT)
            .dateRapport(UPDATED_DATE_RAPPORT)
            .lieuRapport(UPDATED_LIEU_RAPPORT)
            .maisonEditionRapport(UPDATED_MAISON_EDITION_RAPPORT)
            .langueRapport(UPDATED_LANGUE_RAPPORT)
            .lienRapport(UPDATED_LIEN_RAPPORT)
            .doiRapport(UPDATED_DOI_RAPPORT)
            .halRapport(UPDATED_HAL_RAPPORT)
            .diversOuvrageRapport(UPDATED_DIVERS_OUVRAGE_RAPPORT);

        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRapport)))
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getTitreRapport()).isEqualTo(UPDATED_TITRE_RAPPORT);
        assertThat(testRapport.getSousTitreRapport()).isEqualTo(UPDATED_SOUS_TITRE_RAPPORT);
        assertThat(testRapport.getTypeRapport()).isEqualTo(UPDATED_TYPE_RAPPORT);
        assertThat(testRapport.getDateRapport()).isEqualTo(UPDATED_DATE_RAPPORT);
        assertThat(testRapport.getLieuRapport()).isEqualTo(UPDATED_LIEU_RAPPORT);
        assertThat(testRapport.getMaisonEditionRapport()).isEqualTo(UPDATED_MAISON_EDITION_RAPPORT);
        assertThat(testRapport.getLangueRapport()).isEqualTo(UPDATED_LANGUE_RAPPORT);
        assertThat(testRapport.getLienRapport()).isEqualTo(UPDATED_LIEN_RAPPORT);
        assertThat(testRapport.getDoiRapport()).isEqualTo(UPDATED_DOI_RAPPORT);
        assertThat(testRapport.getHalRapport()).isEqualTo(UPDATED_HAL_RAPPORT);
        assertThat(testRapport.getDiversOuvrageRapport()).isEqualTo(UPDATED_DIVERS_OUVRAGE_RAPPORT);
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
