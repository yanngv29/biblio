package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.NumeroRevue;
import edu.ensim.biblio.repository.NumeroRevueRepository;
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
 * Test class for the NumeroRevueResource REST controller.
 *
 * @see NumeroRevueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class NumeroRevueResourceIntTest {

    private static final String DEFAULT_ID_NUMERO_REVUE = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMERO_REVUE = "BBBBBBBBBB";

    private static final String DEFAULT_MOIS = "AAAAAAAAAA";
    private static final String UPDATED_MOIS = "BBBBBBBBBB";

    private static final String DEFAULT_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ANNEE = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String DEFAULT_DOI = "AAAAAAAAAA";
    private static final String UPDATED_DOI = "BBBBBBBBBB";

    @Autowired
    private NumeroRevueRepository numeroRevueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNumeroRevueMockMvc;

    private NumeroRevue numeroRevue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumeroRevueResource numeroRevueResource = new NumeroRevueResource(numeroRevueRepository);
        this.restNumeroRevueMockMvc = MockMvcBuilders.standaloneSetup(numeroRevueResource)
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
    public static NumeroRevue createEntity(EntityManager em) {
        NumeroRevue numeroRevue = new NumeroRevue()
            .idNumeroRevue(DEFAULT_ID_NUMERO_REVUE)
            .mois(DEFAULT_MOIS)
            .annee(DEFAULT_ANNEE)
            .volume(DEFAULT_VOLUME)
            .numeroVolume(DEFAULT_NUMERO_VOLUME)
            .lien(DEFAULT_LIEN)
            .doi(DEFAULT_DOI);
        return numeroRevue;
    }

    @Before
    public void initTest() {
        numeroRevue = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumeroRevue() throws Exception {
        int databaseSizeBeforeCreate = numeroRevueRepository.findAll().size();

        // Create the NumeroRevue
        restNumeroRevueMockMvc.perform(post("/api/numero-revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroRevue)))
            .andExpect(status().isCreated());

        // Validate the NumeroRevue in the database
        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeCreate + 1);
        NumeroRevue testNumeroRevue = numeroRevueList.get(numeroRevueList.size() - 1);
        assertThat(testNumeroRevue.getIdNumeroRevue()).isEqualTo(DEFAULT_ID_NUMERO_REVUE);
        assertThat(testNumeroRevue.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testNumeroRevue.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testNumeroRevue.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testNumeroRevue.getNumeroVolume()).isEqualTo(DEFAULT_NUMERO_VOLUME);
        assertThat(testNumeroRevue.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testNumeroRevue.getDoi()).isEqualTo(DEFAULT_DOI);
    }

    @Test
    @Transactional
    public void createNumeroRevueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numeroRevueRepository.findAll().size();

        // Create the NumeroRevue with an existing ID
        numeroRevue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumeroRevueMockMvc.perform(post("/api/numero-revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroRevue)))
            .andExpect(status().isBadRequest());

        // Validate the NumeroRevue in the database
        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdNumeroRevueIsRequired() throws Exception {
        int databaseSizeBeforeTest = numeroRevueRepository.findAll().size();
        // set the field null
        numeroRevue.setIdNumeroRevue(null);

        // Create the NumeroRevue, which fails.

        restNumeroRevueMockMvc.perform(post("/api/numero-revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroRevue)))
            .andExpect(status().isBadRequest());

        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNumeroRevues() throws Exception {
        // Initialize the database
        numeroRevueRepository.saveAndFlush(numeroRevue);

        // Get all the numeroRevueList
        restNumeroRevueMockMvc.perform(get("/api/numero-revues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numeroRevue.getId().intValue())))
            .andExpect(jsonPath("$.[*].idNumeroRevue").value(hasItem(DEFAULT_ID_NUMERO_REVUE.toString())))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].numeroVolume").value(hasItem(DEFAULT_NUMERO_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].doi").value(hasItem(DEFAULT_DOI.toString())));
    }

    @Test
    @Transactional
    public void getNumeroRevue() throws Exception {
        // Initialize the database
        numeroRevueRepository.saveAndFlush(numeroRevue);

        // Get the numeroRevue
        restNumeroRevueMockMvc.perform(get("/api/numero-revues/{id}", numeroRevue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numeroRevue.getId().intValue()))
            .andExpect(jsonPath("$.idNumeroRevue").value(DEFAULT_ID_NUMERO_REVUE.toString()))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.toString()))
            .andExpect(jsonPath("$.numeroVolume").value(DEFAULT_NUMERO_VOLUME.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.doi").value(DEFAULT_DOI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNumeroRevue() throws Exception {
        // Get the numeroRevue
        restNumeroRevueMockMvc.perform(get("/api/numero-revues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumeroRevue() throws Exception {
        // Initialize the database
        numeroRevueRepository.saveAndFlush(numeroRevue);
        int databaseSizeBeforeUpdate = numeroRevueRepository.findAll().size();

        // Update the numeroRevue
        NumeroRevue updatedNumeroRevue = numeroRevueRepository.findOne(numeroRevue.getId());
        // Disconnect from session so that the updates on updatedNumeroRevue are not directly saved in db
        em.detach(updatedNumeroRevue);
        updatedNumeroRevue
            .idNumeroRevue(UPDATED_ID_NUMERO_REVUE)
            .mois(UPDATED_MOIS)
            .annee(UPDATED_ANNEE)
            .volume(UPDATED_VOLUME)
            .numeroVolume(UPDATED_NUMERO_VOLUME)
            .lien(UPDATED_LIEN)
            .doi(UPDATED_DOI);

        restNumeroRevueMockMvc.perform(put("/api/numero-revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNumeroRevue)))
            .andExpect(status().isOk());

        // Validate the NumeroRevue in the database
        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeUpdate);
        NumeroRevue testNumeroRevue = numeroRevueList.get(numeroRevueList.size() - 1);
        assertThat(testNumeroRevue.getIdNumeroRevue()).isEqualTo(UPDATED_ID_NUMERO_REVUE);
        assertThat(testNumeroRevue.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testNumeroRevue.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testNumeroRevue.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testNumeroRevue.getNumeroVolume()).isEqualTo(UPDATED_NUMERO_VOLUME);
        assertThat(testNumeroRevue.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testNumeroRevue.getDoi()).isEqualTo(UPDATED_DOI);
    }

    @Test
    @Transactional
    public void updateNonExistingNumeroRevue() throws Exception {
        int databaseSizeBeforeUpdate = numeroRevueRepository.findAll().size();

        // Create the NumeroRevue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNumeroRevueMockMvc.perform(put("/api/numero-revues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroRevue)))
            .andExpect(status().isCreated());

        // Validate the NumeroRevue in the database
        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNumeroRevue() throws Exception {
        // Initialize the database
        numeroRevueRepository.saveAndFlush(numeroRevue);
        int databaseSizeBeforeDelete = numeroRevueRepository.findAll().size();

        // Get the numeroRevue
        restNumeroRevueMockMvc.perform(delete("/api/numero-revues/{id}", numeroRevue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NumeroRevue> numeroRevueList = numeroRevueRepository.findAll();
        assertThat(numeroRevueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroRevue.class);
        NumeroRevue numeroRevue1 = new NumeroRevue();
        numeroRevue1.setId(1L);
        NumeroRevue numeroRevue2 = new NumeroRevue();
        numeroRevue2.setId(numeroRevue1.getId());
        assertThat(numeroRevue1).isEqualTo(numeroRevue2);
        numeroRevue2.setId(2L);
        assertThat(numeroRevue1).isNotEqualTo(numeroRevue2);
        numeroRevue1.setId(null);
        assertThat(numeroRevue1).isNotEqualTo(numeroRevue2);
    }
}
