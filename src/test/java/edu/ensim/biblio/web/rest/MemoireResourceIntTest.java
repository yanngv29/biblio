package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Memoire;
import edu.ensim.biblio.repository.MemoireRepository;
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

import edu.ensim.biblio.domain.enumeration.TypeMemoire;
/**
 * Test class for the MemoireResource REST controller.
 *
 * @see MemoireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class MemoireResourceIntTest {

    private static final String DEFAULT_ID_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMOIRE = "BBBBBBBBBB";

    private static final TypeMemoire DEFAULT_TYPE = TypeMemoire.THESE;
    private static final TypeMemoire UPDATED_TYPE = TypeMemoire.HDR;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_HAL = "AAAAAAAAAA";
    private static final String UPDATED_HAL = "BBBBBBBBBB";

    @Autowired
    private MemoireRepository memoireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemoireMockMvc;

    private Memoire memoire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemoireResource memoireResource = new MemoireResource(memoireRepository);
        this.restMemoireMockMvc = MockMvcBuilders.standaloneSetup(memoireResource)
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
    public static Memoire createEntity(EntityManager em) {
        Memoire memoire = new Memoire()
            .idMemoire(DEFAULT_ID_MEMOIRE)
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .lieu(DEFAULT_LIEU)
            .hal(DEFAULT_HAL);
        return memoire;
    }

    @Before
    public void initTest() {
        memoire = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemoire() throws Exception {
        int databaseSizeBeforeCreate = memoireRepository.findAll().size();

        // Create the Memoire
        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoire)))
            .andExpect(status().isCreated());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeCreate + 1);
        Memoire testMemoire = memoireList.get(memoireList.size() - 1);
        assertThat(testMemoire.getIdMemoire()).isEqualTo(DEFAULT_ID_MEMOIRE);
        assertThat(testMemoire.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMemoire.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMemoire.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testMemoire.getHal()).isEqualTo(DEFAULT_HAL);
    }

    @Test
    @Transactional
    public void createMemoireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memoireRepository.findAll().size();

        // Create the Memoire with an existing ID
        memoire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoire)))
            .andExpect(status().isBadRequest());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdMemoireIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setIdMemoire(null);

        // Create the Memoire, which fails.

        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoire)))
            .andExpect(status().isBadRequest());

        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemoires() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);

        // Get all the memoireList
        restMemoireMockMvc.perform(get("/api/memoires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memoire.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMemoire").value(hasItem(DEFAULT_ID_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].hal").value(hasItem(DEFAULT_HAL.toString())));
    }

    @Test
    @Transactional
    public void getMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);

        // Get the memoire
        restMemoireMockMvc.perform(get("/api/memoires/{id}", memoire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memoire.getId().intValue()))
            .andExpect(jsonPath("$.idMemoire").value(DEFAULT_ID_MEMOIRE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.hal").value(DEFAULT_HAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMemoire() throws Exception {
        // Get the memoire
        restMemoireMockMvc.perform(get("/api/memoires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);
        int databaseSizeBeforeUpdate = memoireRepository.findAll().size();

        // Update the memoire
        Memoire updatedMemoire = memoireRepository.findOne(memoire.getId());
        // Disconnect from session so that the updates on updatedMemoire are not directly saved in db
        em.detach(updatedMemoire);
        updatedMemoire
            .idMemoire(UPDATED_ID_MEMOIRE)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .lieu(UPDATED_LIEU)
            .hal(UPDATED_HAL);

        restMemoireMockMvc.perform(put("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemoire)))
            .andExpect(status().isOk());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeUpdate);
        Memoire testMemoire = memoireList.get(memoireList.size() - 1);
        assertThat(testMemoire.getIdMemoire()).isEqualTo(UPDATED_ID_MEMOIRE);
        assertThat(testMemoire.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMemoire.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMemoire.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testMemoire.getHal()).isEqualTo(UPDATED_HAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMemoire() throws Exception {
        int databaseSizeBeforeUpdate = memoireRepository.findAll().size();

        // Create the Memoire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemoireMockMvc.perform(put("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoire)))
            .andExpect(status().isCreated());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);
        int databaseSizeBeforeDelete = memoireRepository.findAll().size();

        // Get the memoire
        restMemoireMockMvc.perform(delete("/api/memoires/{id}", memoire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Memoire.class);
        Memoire memoire1 = new Memoire();
        memoire1.setId(1L);
        Memoire memoire2 = new Memoire();
        memoire2.setId(memoire1.getId());
        assertThat(memoire1).isEqualTo(memoire2);
        memoire2.setId(2L);
        assertThat(memoire1).isNotEqualTo(memoire2);
        memoire1.setId(null);
        assertThat(memoire1).isNotEqualTo(memoire2);
    }
}
