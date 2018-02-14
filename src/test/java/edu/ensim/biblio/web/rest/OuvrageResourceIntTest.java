package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Ouvrage;
import edu.ensim.biblio.repository.OuvrageRepository;
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

import edu.ensim.biblio.domain.enumeration.TypeOuvrage;
import edu.ensim.biblio.domain.enumeration.TypeParticipation;
/**
 * Test class for the OuvrageResource REST controller.
 *
 * @see OuvrageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class OuvrageResourceIntTest {

    private static final String DEFAULT_ID_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_ID_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final TypeOuvrage DEFAULT_TYPE = TypeOuvrage.SCIENTIFIQUE;
    private static final TypeOuvrage UPDATED_TYPE = TypeOuvrage.VULGARISATION;

    private static final TypeParticipation DEFAULT_PARTICIPATION = TypeParticipation.COMPLETE;
    private static final TypeParticipation UPDATED_PARTICIPATION = TypeParticipation.PARTIELLE;

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_NUMERO_EDITION = 1;
    private static final Integer UPDATED_NUMERO_EDITION = 2;

    private static final Integer DEFAULT_VOLUME = 1;
    private static final Integer UPDATED_VOLUME = 2;

    private static final String DEFAULT_TRADUCTION = "AAAAAAAAAA";
    private static final String UPDATED_TRADUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION = "BBBBBBBBBB";

    private static final String DEFAULT_HAL = "AAAAAAAAAA";
    private static final String UPDATED_HAL = "BBBBBBBBBB";

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOuvrageMockMvc;

    private Ouvrage ouvrage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OuvrageResource ouvrageResource = new OuvrageResource(ouvrageRepository);
        this.restOuvrageMockMvc = MockMvcBuilders.standaloneSetup(ouvrageResource)
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
    public static Ouvrage createEntity(EntityManager em) {
        Ouvrage ouvrage = new Ouvrage()
            .idOuvrage(DEFAULT_ID_OUVRAGE)
            .titre(DEFAULT_TITRE)
            .type(DEFAULT_TYPE)
            .participation(DEFAULT_PARTICIPATION)
            .annee(DEFAULT_ANNEE)
            .numeroEdition(DEFAULT_NUMERO_EDITION)
            .volume(DEFAULT_VOLUME)
            .traduction(DEFAULT_TRADUCTION)
            .lieu(DEFAULT_LIEU)
            .maisonEdition(DEFAULT_MAISON_EDITION)
            .collection(DEFAULT_COLLECTION)
            .hal(DEFAULT_HAL);
        return ouvrage;
    }

    @Before
    public void initTest() {
        ouvrage = createEntity(em);
    }

    @Test
    @Transactional
    public void createOuvrage() throws Exception {
        int databaseSizeBeforeCreate = ouvrageRepository.findAll().size();

        // Create the Ouvrage
        restOuvrageMockMvc.perform(post("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ouvrage)))
            .andExpect(status().isCreated());

        // Validate the Ouvrage in the database
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeCreate + 1);
        Ouvrage testOuvrage = ouvrageList.get(ouvrageList.size() - 1);
        assertThat(testOuvrage.getIdOuvrage()).isEqualTo(DEFAULT_ID_OUVRAGE);
        assertThat(testOuvrage.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testOuvrage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOuvrage.getParticipation()).isEqualTo(DEFAULT_PARTICIPATION);
        assertThat(testOuvrage.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testOuvrage.getNumeroEdition()).isEqualTo(DEFAULT_NUMERO_EDITION);
        assertThat(testOuvrage.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testOuvrage.getTraduction()).isEqualTo(DEFAULT_TRADUCTION);
        assertThat(testOuvrage.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testOuvrage.getMaisonEdition()).isEqualTo(DEFAULT_MAISON_EDITION);
        assertThat(testOuvrage.getCollection()).isEqualTo(DEFAULT_COLLECTION);
        assertThat(testOuvrage.getHal()).isEqualTo(DEFAULT_HAL);
    }

    @Test
    @Transactional
    public void createOuvrageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ouvrageRepository.findAll().size();

        // Create the Ouvrage with an existing ID
        ouvrage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOuvrageMockMvc.perform(post("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ouvrage)))
            .andExpect(status().isBadRequest());

        // Validate the Ouvrage in the database
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdOuvrageIsRequired() throws Exception {
        int databaseSizeBeforeTest = ouvrageRepository.findAll().size();
        // set the field null
        ouvrage.setIdOuvrage(null);

        // Create the Ouvrage, which fails.

        restOuvrageMockMvc.perform(post("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ouvrage)))
            .andExpect(status().isBadRequest());

        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOuvrages() throws Exception {
        // Initialize the database
        ouvrageRepository.saveAndFlush(ouvrage);

        // Get all the ouvrageList
        restOuvrageMockMvc.perform(get("/api/ouvrages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ouvrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].idOuvrage").value(hasItem(DEFAULT_ID_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].participation").value(hasItem(DEFAULT_PARTICIPATION.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].numeroEdition").value(hasItem(DEFAULT_NUMERO_EDITION)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].traduction").value(hasItem(DEFAULT_TRADUCTION.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].maisonEdition").value(hasItem(DEFAULT_MAISON_EDITION.toString())))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION.toString())))
            .andExpect(jsonPath("$.[*].hal").value(hasItem(DEFAULT_HAL.toString())));
    }

    @Test
    @Transactional
    public void getOuvrage() throws Exception {
        // Initialize the database
        ouvrageRepository.saveAndFlush(ouvrage);

        // Get the ouvrage
        restOuvrageMockMvc.perform(get("/api/ouvrages/{id}", ouvrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ouvrage.getId().intValue()))
            .andExpect(jsonPath("$.idOuvrage").value(DEFAULT_ID_OUVRAGE.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.participation").value(DEFAULT_PARTICIPATION.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.numeroEdition").value(DEFAULT_NUMERO_EDITION))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.traduction").value(DEFAULT_TRADUCTION.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.maisonEdition").value(DEFAULT_MAISON_EDITION.toString()))
            .andExpect(jsonPath("$.collection").value(DEFAULT_COLLECTION.toString()))
            .andExpect(jsonPath("$.hal").value(DEFAULT_HAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOuvrage() throws Exception {
        // Get the ouvrage
        restOuvrageMockMvc.perform(get("/api/ouvrages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOuvrage() throws Exception {
        // Initialize the database
        ouvrageRepository.saveAndFlush(ouvrage);
        int databaseSizeBeforeUpdate = ouvrageRepository.findAll().size();

        // Update the ouvrage
        Ouvrage updatedOuvrage = ouvrageRepository.findOne(ouvrage.getId());
        // Disconnect from session so that the updates on updatedOuvrage are not directly saved in db
        em.detach(updatedOuvrage);
        updatedOuvrage
            .idOuvrage(UPDATED_ID_OUVRAGE)
            .titre(UPDATED_TITRE)
            .type(UPDATED_TYPE)
            .participation(UPDATED_PARTICIPATION)
            .annee(UPDATED_ANNEE)
            .numeroEdition(UPDATED_NUMERO_EDITION)
            .volume(UPDATED_VOLUME)
            .traduction(UPDATED_TRADUCTION)
            .lieu(UPDATED_LIEU)
            .maisonEdition(UPDATED_MAISON_EDITION)
            .collection(UPDATED_COLLECTION)
            .hal(UPDATED_HAL);

        restOuvrageMockMvc.perform(put("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOuvrage)))
            .andExpect(status().isOk());

        // Validate the Ouvrage in the database
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeUpdate);
        Ouvrage testOuvrage = ouvrageList.get(ouvrageList.size() - 1);
        assertThat(testOuvrage.getIdOuvrage()).isEqualTo(UPDATED_ID_OUVRAGE);
        assertThat(testOuvrage.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testOuvrage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOuvrage.getParticipation()).isEqualTo(UPDATED_PARTICIPATION);
        assertThat(testOuvrage.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testOuvrage.getNumeroEdition()).isEqualTo(UPDATED_NUMERO_EDITION);
        assertThat(testOuvrage.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testOuvrage.getTraduction()).isEqualTo(UPDATED_TRADUCTION);
        assertThat(testOuvrage.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testOuvrage.getMaisonEdition()).isEqualTo(UPDATED_MAISON_EDITION);
        assertThat(testOuvrage.getCollection()).isEqualTo(UPDATED_COLLECTION);
        assertThat(testOuvrage.getHal()).isEqualTo(UPDATED_HAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOuvrage() throws Exception {
        int databaseSizeBeforeUpdate = ouvrageRepository.findAll().size();

        // Create the Ouvrage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOuvrageMockMvc.perform(put("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ouvrage)))
            .andExpect(status().isCreated());

        // Validate the Ouvrage in the database
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOuvrage() throws Exception {
        // Initialize the database
        ouvrageRepository.saveAndFlush(ouvrage);
        int databaseSizeBeforeDelete = ouvrageRepository.findAll().size();

        // Get the ouvrage
        restOuvrageMockMvc.perform(delete("/api/ouvrages/{id}", ouvrage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ouvrage.class);
        Ouvrage ouvrage1 = new Ouvrage();
        ouvrage1.setId(1L);
        Ouvrage ouvrage2 = new Ouvrage();
        ouvrage2.setId(ouvrage1.getId());
        assertThat(ouvrage1).isEqualTo(ouvrage2);
        ouvrage2.setId(2L);
        assertThat(ouvrage1).isNotEqualTo(ouvrage2);
        ouvrage1.setId(null);
        assertThat(ouvrage1).isNotEqualTo(ouvrage2);
    }
}
