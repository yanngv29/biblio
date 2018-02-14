package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Acte;
import edu.ensim.biblio.repository.ActeRepository;
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

/**
 * Test class for the ActeResource REST controller.
 *
 * @see ActeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ActeResourceIntTest {

    private static final String DEFAULT_ID_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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
    private ActeRepository acteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActeMockMvc;

    private Acte acte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeResource acteResource = new ActeResource(acteRepository);
        this.restActeMockMvc = MockMvcBuilders.standaloneSetup(acteResource)
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
    public static Acte createEntity(EntityManager em) {
        Acte acte = new Acte()
            .idActe(DEFAULT_ID_ACTE)
            .titre(DEFAULT_TITRE)
            .type(DEFAULT_TYPE)
            .annee(DEFAULT_ANNEE)
            .numeroEdition(DEFAULT_NUMERO_EDITION)
            .volume(DEFAULT_VOLUME)
            .traduction(DEFAULT_TRADUCTION)
            .lieu(DEFAULT_LIEU)
            .maisonEdition(DEFAULT_MAISON_EDITION)
            .collection(DEFAULT_COLLECTION)
            .hal(DEFAULT_HAL);
        return acte;
    }

    @Before
    public void initTest() {
        acte = createEntity(em);
    }

    @Test
    @Transactional
    public void createActe() throws Exception {
        int databaseSizeBeforeCreate = acteRepository.findAll().size();

        // Create the Acte
        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acte)))
            .andExpect(status().isCreated());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeCreate + 1);
        Acte testActe = acteList.get(acteList.size() - 1);
        assertThat(testActe.getIdActe()).isEqualTo(DEFAULT_ID_ACTE);
        assertThat(testActe.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testActe.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testActe.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testActe.getNumeroEdition()).isEqualTo(DEFAULT_NUMERO_EDITION);
        assertThat(testActe.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testActe.getTraduction()).isEqualTo(DEFAULT_TRADUCTION);
        assertThat(testActe.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testActe.getMaisonEdition()).isEqualTo(DEFAULT_MAISON_EDITION);
        assertThat(testActe.getCollection()).isEqualTo(DEFAULT_COLLECTION);
        assertThat(testActe.getHal()).isEqualTo(DEFAULT_HAL);
    }

    @Test
    @Transactional
    public void createActeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteRepository.findAll().size();

        // Create the Acte with an existing ID
        acte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acte)))
            .andExpect(status().isBadRequest());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdActeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acteRepository.findAll().size();
        // set the field null
        acte.setIdActe(null);

        // Create the Acte, which fails.

        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acte)))
            .andExpect(status().isBadRequest());

        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActes() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList
        restActeMockMvc.perform(get("/api/actes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acte.getId().intValue())))
            .andExpect(jsonPath("$.[*].idActe").value(hasItem(DEFAULT_ID_ACTE.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
    public void getActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get the acte
        restActeMockMvc.perform(get("/api/actes/{id}", acte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acte.getId().intValue()))
            .andExpect(jsonPath("$.idActe").value(DEFAULT_ID_ACTE.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
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
    public void getNonExistingActe() throws Exception {
        // Get the acte
        restActeMockMvc.perform(get("/api/actes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);
        int databaseSizeBeforeUpdate = acteRepository.findAll().size();

        // Update the acte
        Acte updatedActe = acteRepository.findOne(acte.getId());
        // Disconnect from session so that the updates on updatedActe are not directly saved in db
        em.detach(updatedActe);
        updatedActe
            .idActe(UPDATED_ID_ACTE)
            .titre(UPDATED_TITRE)
            .type(UPDATED_TYPE)
            .annee(UPDATED_ANNEE)
            .numeroEdition(UPDATED_NUMERO_EDITION)
            .volume(UPDATED_VOLUME)
            .traduction(UPDATED_TRADUCTION)
            .lieu(UPDATED_LIEU)
            .maisonEdition(UPDATED_MAISON_EDITION)
            .collection(UPDATED_COLLECTION)
            .hal(UPDATED_HAL);

        restActeMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActe)))
            .andExpect(status().isOk());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeUpdate);
        Acte testActe = acteList.get(acteList.size() - 1);
        assertThat(testActe.getIdActe()).isEqualTo(UPDATED_ID_ACTE);
        assertThat(testActe.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testActe.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActe.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testActe.getNumeroEdition()).isEqualTo(UPDATED_NUMERO_EDITION);
        assertThat(testActe.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testActe.getTraduction()).isEqualTo(UPDATED_TRADUCTION);
        assertThat(testActe.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testActe.getMaisonEdition()).isEqualTo(UPDATED_MAISON_EDITION);
        assertThat(testActe.getCollection()).isEqualTo(UPDATED_COLLECTION);
        assertThat(testActe.getHal()).isEqualTo(UPDATED_HAL);
    }

    @Test
    @Transactional
    public void updateNonExistingActe() throws Exception {
        int databaseSizeBeforeUpdate = acteRepository.findAll().size();

        // Create the Acte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActeMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acte)))
            .andExpect(status().isCreated());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);
        int databaseSizeBeforeDelete = acteRepository.findAll().size();

        // Get the acte
        restActeMockMvc.perform(delete("/api/actes/{id}", acte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acte.class);
        Acte acte1 = new Acte();
        acte1.setId(1L);
        Acte acte2 = new Acte();
        acte2.setId(acte1.getId());
        assertThat(acte1).isEqualTo(acte2);
        acte2.setId(2L);
        assertThat(acte1).isNotEqualTo(acte2);
        acte1.setId(null);
        assertThat(acte1).isNotEqualTo(acte2);
    }
}
