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

    private static final String DEFAULT_TITRE_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_OUVRAGE = "BBBBBBBBBB";

    private static final TypeOuvrage DEFAULT_TYPE_OUVRAGE = TypeOuvrage.SCIENTIFIQUE;
    private static final TypeOuvrage UPDATED_TYPE_OUVRAGE = TypeOuvrage.VULGARISATION;

    private static final TypeParticipation DEFAULT_PARTICIPATION_OUVRAGE = TypeParticipation.COMPLETE;
    private static final TypeParticipation UPDATED_PARTICIPATION_OUVRAGE = TypeParticipation.PARTIELLE;

    private static final Integer DEFAULT_ANNEE_OUVRAGE = 1;
    private static final Integer UPDATED_ANNEE_OUVRAGE = 2;

    private static final Integer DEFAULT_NUMERO_EDITION_OUVRAGE = 1;
    private static final Integer UPDATED_NUMERO_EDITION_OUVRAGE = 2;

    private static final Integer DEFAULT_VOLUME_OUVRAGE = 1;
    private static final Integer UPDATED_VOLUME_OUVRAGE = 2;

    private static final String DEFAULT_TRADUCTION_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_TRADUCTION_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DOI_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_DOI_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_HAL_OUVRAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_OUVRAGE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_OUVRAGE = "BBBBBBBBBB";

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
            .titreOuvrage(DEFAULT_TITRE_OUVRAGE)
            .typeOuvrage(DEFAULT_TYPE_OUVRAGE)
            .participationOuvrage(DEFAULT_PARTICIPATION_OUVRAGE)
            .anneeOuvrage(DEFAULT_ANNEE_OUVRAGE)
            .numeroEditionOuvrage(DEFAULT_NUMERO_EDITION_OUVRAGE)
            .volumeOuvrage(DEFAULT_VOLUME_OUVRAGE)
            .traductionOuvrage(DEFAULT_TRADUCTION_OUVRAGE)
            .lieuOuvrage(DEFAULT_LIEU_OUVRAGE)
            .maisonEditionOuvrage(DEFAULT_MAISON_EDITION_OUVRAGE)
            .collectionOuvrage(DEFAULT_COLLECTION_OUVRAGE)
            .langueOuvrage(DEFAULT_LANGUE_OUVRAGE)
            .lienOuvrage(DEFAULT_LIEN_OUVRAGE)
            .doiOuvrage(DEFAULT_DOI_OUVRAGE)
            .halOuvrage(DEFAULT_HAL_OUVRAGE)
            .diversOuvrage(DEFAULT_DIVERS_OUVRAGE);
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
        assertThat(testOuvrage.getTitreOuvrage()).isEqualTo(DEFAULT_TITRE_OUVRAGE);
        assertThat(testOuvrage.getTypeOuvrage()).isEqualTo(DEFAULT_TYPE_OUVRAGE);
        assertThat(testOuvrage.getParticipationOuvrage()).isEqualTo(DEFAULT_PARTICIPATION_OUVRAGE);
        assertThat(testOuvrage.getAnneeOuvrage()).isEqualTo(DEFAULT_ANNEE_OUVRAGE);
        assertThat(testOuvrage.getNumeroEditionOuvrage()).isEqualTo(DEFAULT_NUMERO_EDITION_OUVRAGE);
        assertThat(testOuvrage.getVolumeOuvrage()).isEqualTo(DEFAULT_VOLUME_OUVRAGE);
        assertThat(testOuvrage.getTraductionOuvrage()).isEqualTo(DEFAULT_TRADUCTION_OUVRAGE);
        assertThat(testOuvrage.getLieuOuvrage()).isEqualTo(DEFAULT_LIEU_OUVRAGE);
        assertThat(testOuvrage.getMaisonEditionOuvrage()).isEqualTo(DEFAULT_MAISON_EDITION_OUVRAGE);
        assertThat(testOuvrage.getCollectionOuvrage()).isEqualTo(DEFAULT_COLLECTION_OUVRAGE);
        assertThat(testOuvrage.getLangueOuvrage()).isEqualTo(DEFAULT_LANGUE_OUVRAGE);
        assertThat(testOuvrage.getLienOuvrage()).isEqualTo(DEFAULT_LIEN_OUVRAGE);
        assertThat(testOuvrage.getDoiOuvrage()).isEqualTo(DEFAULT_DOI_OUVRAGE);
        assertThat(testOuvrage.getHalOuvrage()).isEqualTo(DEFAULT_HAL_OUVRAGE);
        assertThat(testOuvrage.getDiversOuvrage()).isEqualTo(DEFAULT_DIVERS_OUVRAGE);
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
    public void checkTitreOuvrageIsRequired() throws Exception {
        int databaseSizeBeforeTest = ouvrageRepository.findAll().size();
        // set the field null
        ouvrage.setTitreOuvrage(null);

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
    public void checkTypeOuvrageIsRequired() throws Exception {
        int databaseSizeBeforeTest = ouvrageRepository.findAll().size();
        // set the field null
        ouvrage.setTypeOuvrage(null);

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
            .andExpect(jsonPath("$.[*].titreOuvrage").value(hasItem(DEFAULT_TITRE_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].typeOuvrage").value(hasItem(DEFAULT_TYPE_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].participationOuvrage").value(hasItem(DEFAULT_PARTICIPATION_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].anneeOuvrage").value(hasItem(DEFAULT_ANNEE_OUVRAGE)))
            .andExpect(jsonPath("$.[*].numeroEditionOuvrage").value(hasItem(DEFAULT_NUMERO_EDITION_OUVRAGE)))
            .andExpect(jsonPath("$.[*].volumeOuvrage").value(hasItem(DEFAULT_VOLUME_OUVRAGE)))
            .andExpect(jsonPath("$.[*].traductionOuvrage").value(hasItem(DEFAULT_TRADUCTION_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].lieuOuvrage").value(hasItem(DEFAULT_LIEU_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].maisonEditionOuvrage").value(hasItem(DEFAULT_MAISON_EDITION_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].collectionOuvrage").value(hasItem(DEFAULT_COLLECTION_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].langueOuvrage").value(hasItem(DEFAULT_LANGUE_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].lienOuvrage").value(hasItem(DEFAULT_LIEN_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].doiOuvrage").value(hasItem(DEFAULT_DOI_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].halOuvrage").value(hasItem(DEFAULT_HAL_OUVRAGE.toString())))
            .andExpect(jsonPath("$.[*].diversOuvrage").value(hasItem(DEFAULT_DIVERS_OUVRAGE.toString())));
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
            .andExpect(jsonPath("$.titreOuvrage").value(DEFAULT_TITRE_OUVRAGE.toString()))
            .andExpect(jsonPath("$.typeOuvrage").value(DEFAULT_TYPE_OUVRAGE.toString()))
            .andExpect(jsonPath("$.participationOuvrage").value(DEFAULT_PARTICIPATION_OUVRAGE.toString()))
            .andExpect(jsonPath("$.anneeOuvrage").value(DEFAULT_ANNEE_OUVRAGE))
            .andExpect(jsonPath("$.numeroEditionOuvrage").value(DEFAULT_NUMERO_EDITION_OUVRAGE))
            .andExpect(jsonPath("$.volumeOuvrage").value(DEFAULT_VOLUME_OUVRAGE))
            .andExpect(jsonPath("$.traductionOuvrage").value(DEFAULT_TRADUCTION_OUVRAGE.toString()))
            .andExpect(jsonPath("$.lieuOuvrage").value(DEFAULT_LIEU_OUVRAGE.toString()))
            .andExpect(jsonPath("$.maisonEditionOuvrage").value(DEFAULT_MAISON_EDITION_OUVRAGE.toString()))
            .andExpect(jsonPath("$.collectionOuvrage").value(DEFAULT_COLLECTION_OUVRAGE.toString()))
            .andExpect(jsonPath("$.langueOuvrage").value(DEFAULT_LANGUE_OUVRAGE.toString()))
            .andExpect(jsonPath("$.lienOuvrage").value(DEFAULT_LIEN_OUVRAGE.toString()))
            .andExpect(jsonPath("$.doiOuvrage").value(DEFAULT_DOI_OUVRAGE.toString()))
            .andExpect(jsonPath("$.halOuvrage").value(DEFAULT_HAL_OUVRAGE.toString()))
            .andExpect(jsonPath("$.diversOuvrage").value(DEFAULT_DIVERS_OUVRAGE.toString()));
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
            .titreOuvrage(UPDATED_TITRE_OUVRAGE)
            .typeOuvrage(UPDATED_TYPE_OUVRAGE)
            .participationOuvrage(UPDATED_PARTICIPATION_OUVRAGE)
            .anneeOuvrage(UPDATED_ANNEE_OUVRAGE)
            .numeroEditionOuvrage(UPDATED_NUMERO_EDITION_OUVRAGE)
            .volumeOuvrage(UPDATED_VOLUME_OUVRAGE)
            .traductionOuvrage(UPDATED_TRADUCTION_OUVRAGE)
            .lieuOuvrage(UPDATED_LIEU_OUVRAGE)
            .maisonEditionOuvrage(UPDATED_MAISON_EDITION_OUVRAGE)
            .collectionOuvrage(UPDATED_COLLECTION_OUVRAGE)
            .langueOuvrage(UPDATED_LANGUE_OUVRAGE)
            .lienOuvrage(UPDATED_LIEN_OUVRAGE)
            .doiOuvrage(UPDATED_DOI_OUVRAGE)
            .halOuvrage(UPDATED_HAL_OUVRAGE)
            .diversOuvrage(UPDATED_DIVERS_OUVRAGE);

        restOuvrageMockMvc.perform(put("/api/ouvrages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOuvrage)))
            .andExpect(status().isOk());

        // Validate the Ouvrage in the database
        List<Ouvrage> ouvrageList = ouvrageRepository.findAll();
        assertThat(ouvrageList).hasSize(databaseSizeBeforeUpdate);
        Ouvrage testOuvrage = ouvrageList.get(ouvrageList.size() - 1);
        assertThat(testOuvrage.getTitreOuvrage()).isEqualTo(UPDATED_TITRE_OUVRAGE);
        assertThat(testOuvrage.getTypeOuvrage()).isEqualTo(UPDATED_TYPE_OUVRAGE);
        assertThat(testOuvrage.getParticipationOuvrage()).isEqualTo(UPDATED_PARTICIPATION_OUVRAGE);
        assertThat(testOuvrage.getAnneeOuvrage()).isEqualTo(UPDATED_ANNEE_OUVRAGE);
        assertThat(testOuvrage.getNumeroEditionOuvrage()).isEqualTo(UPDATED_NUMERO_EDITION_OUVRAGE);
        assertThat(testOuvrage.getVolumeOuvrage()).isEqualTo(UPDATED_VOLUME_OUVRAGE);
        assertThat(testOuvrage.getTraductionOuvrage()).isEqualTo(UPDATED_TRADUCTION_OUVRAGE);
        assertThat(testOuvrage.getLieuOuvrage()).isEqualTo(UPDATED_LIEU_OUVRAGE);
        assertThat(testOuvrage.getMaisonEditionOuvrage()).isEqualTo(UPDATED_MAISON_EDITION_OUVRAGE);
        assertThat(testOuvrage.getCollectionOuvrage()).isEqualTo(UPDATED_COLLECTION_OUVRAGE);
        assertThat(testOuvrage.getLangueOuvrage()).isEqualTo(UPDATED_LANGUE_OUVRAGE);
        assertThat(testOuvrage.getLienOuvrage()).isEqualTo(UPDATED_LIEN_OUVRAGE);
        assertThat(testOuvrage.getDoiOuvrage()).isEqualTo(UPDATED_DOI_OUVRAGE);
        assertThat(testOuvrage.getHalOuvrage()).isEqualTo(UPDATED_HAL_OUVRAGE);
        assertThat(testOuvrage.getDiversOuvrage()).isEqualTo(UPDATED_DIVERS_OUVRAGE);
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
