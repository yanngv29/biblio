package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Notation;
import edu.ensim.biblio.repository.NotationRepository;
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

import edu.ensim.biblio.domain.enumeration.Rang;
/**
 * Test class for the NotationResource REST controller.
 *
 * @see NotationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class NotationResourceIntTest {

    private static final String DEFAULT_ID_NOTATION = "AAAAAAAAAA";
    private static final String UPDATED_ID_NOTATION = "BBBBBBBBBB";

    private static final String DEFAULT_CNU = "AAAAAAAAAA";
    private static final String UPDATED_CNU = "BBBBBBBBBB";

    private static final Rang DEFAULT_RANG = Rang.A;
    private static final Rang UPDATED_RANG = Rang.B;

    private static final String DEFAULT_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FIN = "BBBBBBBBBB";

    @Autowired
    private NotationRepository notationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotationMockMvc;

    private Notation notation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotationResource notationResource = new NotationResource(notationRepository);
        this.restNotationMockMvc = MockMvcBuilders.standaloneSetup(notationResource)
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
    public static Notation createEntity(EntityManager em) {
        Notation notation = new Notation()
            .idNotation(DEFAULT_ID_NOTATION)
            .cnu(DEFAULT_CNU)
            .rang(DEFAULT_RANG)
            .debut(DEFAULT_DEBUT)
            .fin(DEFAULT_FIN);
        return notation;
    }

    @Before
    public void initTest() {
        notation = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotation() throws Exception {
        int databaseSizeBeforeCreate = notationRepository.findAll().size();

        // Create the Notation
        restNotationMockMvc.perform(post("/api/notations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isCreated());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeCreate + 1);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getIdNotation()).isEqualTo(DEFAULT_ID_NOTATION);
        assertThat(testNotation.getCnu()).isEqualTo(DEFAULT_CNU);
        assertThat(testNotation.getRang()).isEqualTo(DEFAULT_RANG);
        assertThat(testNotation.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testNotation.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    public void createNotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notationRepository.findAll().size();

        // Create the Notation with an existing ID
        notation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotationMockMvc.perform(post("/api/notations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdNotationIsRequired() throws Exception {
        int databaseSizeBeforeTest = notationRepository.findAll().size();
        // set the field null
        notation.setIdNotation(null);

        // Create the Notation, which fails.

        restNotationMockMvc.perform(post("/api/notations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isBadRequest());

        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotations() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        // Get all the notationList
        restNotationMockMvc.perform(get("/api/notations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notation.getId().intValue())))
            .andExpect(jsonPath("$.[*].idNotation").value(hasItem(DEFAULT_ID_NOTATION.toString())))
            .andExpect(jsonPath("$.[*].cnu").value(hasItem(DEFAULT_CNU.toString())))
            .andExpect(jsonPath("$.[*].rang").value(hasItem(DEFAULT_RANG.toString())))
            .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }

    @Test
    @Transactional
    public void getNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        // Get the notation
        restNotationMockMvc.perform(get("/api/notations/{id}", notation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notation.getId().intValue()))
            .andExpect(jsonPath("$.idNotation").value(DEFAULT_ID_NOTATION.toString()))
            .andExpect(jsonPath("$.cnu").value(DEFAULT_CNU.toString()))
            .andExpect(jsonPath("$.rang").value(DEFAULT_RANG.toString()))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotation() throws Exception {
        // Get the notation
        restNotationMockMvc.perform(get("/api/notations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();

        // Update the notation
        Notation updatedNotation = notationRepository.findOne(notation.getId());
        // Disconnect from session so that the updates on updatedNotation are not directly saved in db
        em.detach(updatedNotation);
        updatedNotation
            .idNotation(UPDATED_ID_NOTATION)
            .cnu(UPDATED_CNU)
            .rang(UPDATED_RANG)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN);

        restNotationMockMvc.perform(put("/api/notations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotation)))
            .andExpect(status().isOk());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getIdNotation()).isEqualTo(UPDATED_ID_NOTATION);
        assertThat(testNotation.getCnu()).isEqualTo(UPDATED_CNU);
        assertThat(testNotation.getRang()).isEqualTo(UPDATED_RANG);
        assertThat(testNotation.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testNotation.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();

        // Create the Notation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotationMockMvc.perform(put("/api/notations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isCreated());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);
        int databaseSizeBeforeDelete = notationRepository.findAll().size();

        // Get the notation
        restNotationMockMvc.perform(delete("/api/notations/{id}", notation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notation.class);
        Notation notation1 = new Notation();
        notation1.setId(1L);
        Notation notation2 = new Notation();
        notation2.setId(notation1.getId());
        assertThat(notation1).isEqualTo(notation2);
        notation2.setId(2L);
        assertThat(notation1).isNotEqualTo(notation2);
        notation1.setId(null);
        assertThat(notation1).isNotEqualTo(notation2);
    }
}
