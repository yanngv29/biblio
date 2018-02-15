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

    private static final String DEFAULT_TITRE_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_MEMOIRE = "BBBBBBBBBB";

    private static final TypeMemoire DEFAULT_TYPE_MEMOIRE = TypeMemoire.THESE;
    private static final TypeMemoire UPDATED_TYPE_MEMOIRE = TypeMemoire.HDR;

    private static final Instant DEFAULT_DATE_MEMOIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MEMOIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_MEMOIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_MEMOIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_MEMOIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DOI_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_DOI_MEMOIRE = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_HAL_MEMOIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_MEMOIRE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_MEMOIRE = "BBBBBBBBBB";

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
            .titreMemoire(DEFAULT_TITRE_MEMOIRE)
            .typeMemoire(DEFAULT_TYPE_MEMOIRE)
            .dateMemoire(DEFAULT_DATE_MEMOIRE)
            .lieuMemoire(DEFAULT_LIEU_MEMOIRE)
            .langueMemoire(DEFAULT_LANGUE_MEMOIRE)
            .lienMemoire(DEFAULT_LIEN_MEMOIRE)
            .doiMemoire(DEFAULT_DOI_MEMOIRE)
            .halMemoire(DEFAULT_HAL_MEMOIRE)
            .diversMemoire(DEFAULT_DIVERS_MEMOIRE);
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
        assertThat(testMemoire.getTitreMemoire()).isEqualTo(DEFAULT_TITRE_MEMOIRE);
        assertThat(testMemoire.getTypeMemoire()).isEqualTo(DEFAULT_TYPE_MEMOIRE);
        assertThat(testMemoire.getDateMemoire()).isEqualTo(DEFAULT_DATE_MEMOIRE);
        assertThat(testMemoire.getLieuMemoire()).isEqualTo(DEFAULT_LIEU_MEMOIRE);
        assertThat(testMemoire.getLangueMemoire()).isEqualTo(DEFAULT_LANGUE_MEMOIRE);
        assertThat(testMemoire.getLienMemoire()).isEqualTo(DEFAULT_LIEN_MEMOIRE);
        assertThat(testMemoire.getDoiMemoire()).isEqualTo(DEFAULT_DOI_MEMOIRE);
        assertThat(testMemoire.getHalMemoire()).isEqualTo(DEFAULT_HAL_MEMOIRE);
        assertThat(testMemoire.getDiversMemoire()).isEqualTo(DEFAULT_DIVERS_MEMOIRE);
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
    public void checkTitreMemoireIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setTitreMemoire(null);

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
    public void checkTypeMemoireIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setTypeMemoire(null);

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
            .andExpect(jsonPath("$.[*].titreMemoire").value(hasItem(DEFAULT_TITRE_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].typeMemoire").value(hasItem(DEFAULT_TYPE_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].dateMemoire").value(hasItem(DEFAULT_DATE_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].lieuMemoire").value(hasItem(DEFAULT_LIEU_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].langueMemoire").value(hasItem(DEFAULT_LANGUE_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].lienMemoire").value(hasItem(DEFAULT_LIEN_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].doiMemoire").value(hasItem(DEFAULT_DOI_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].halMemoire").value(hasItem(DEFAULT_HAL_MEMOIRE.toString())))
            .andExpect(jsonPath("$.[*].diversMemoire").value(hasItem(DEFAULT_DIVERS_MEMOIRE.toString())));
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
            .andExpect(jsonPath("$.titreMemoire").value(DEFAULT_TITRE_MEMOIRE.toString()))
            .andExpect(jsonPath("$.typeMemoire").value(DEFAULT_TYPE_MEMOIRE.toString()))
            .andExpect(jsonPath("$.dateMemoire").value(DEFAULT_DATE_MEMOIRE.toString()))
            .andExpect(jsonPath("$.lieuMemoire").value(DEFAULT_LIEU_MEMOIRE.toString()))
            .andExpect(jsonPath("$.langueMemoire").value(DEFAULT_LANGUE_MEMOIRE.toString()))
            .andExpect(jsonPath("$.lienMemoire").value(DEFAULT_LIEN_MEMOIRE.toString()))
            .andExpect(jsonPath("$.doiMemoire").value(DEFAULT_DOI_MEMOIRE.toString()))
            .andExpect(jsonPath("$.halMemoire").value(DEFAULT_HAL_MEMOIRE.toString()))
            .andExpect(jsonPath("$.diversMemoire").value(DEFAULT_DIVERS_MEMOIRE.toString()));
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
            .titreMemoire(UPDATED_TITRE_MEMOIRE)
            .typeMemoire(UPDATED_TYPE_MEMOIRE)
            .dateMemoire(UPDATED_DATE_MEMOIRE)
            .lieuMemoire(UPDATED_LIEU_MEMOIRE)
            .langueMemoire(UPDATED_LANGUE_MEMOIRE)
            .lienMemoire(UPDATED_LIEN_MEMOIRE)
            .doiMemoire(UPDATED_DOI_MEMOIRE)
            .halMemoire(UPDATED_HAL_MEMOIRE)
            .diversMemoire(UPDATED_DIVERS_MEMOIRE);

        restMemoireMockMvc.perform(put("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemoire)))
            .andExpect(status().isOk());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeUpdate);
        Memoire testMemoire = memoireList.get(memoireList.size() - 1);
        assertThat(testMemoire.getTitreMemoire()).isEqualTo(UPDATED_TITRE_MEMOIRE);
        assertThat(testMemoire.getTypeMemoire()).isEqualTo(UPDATED_TYPE_MEMOIRE);
        assertThat(testMemoire.getDateMemoire()).isEqualTo(UPDATED_DATE_MEMOIRE);
        assertThat(testMemoire.getLieuMemoire()).isEqualTo(UPDATED_LIEU_MEMOIRE);
        assertThat(testMemoire.getLangueMemoire()).isEqualTo(UPDATED_LANGUE_MEMOIRE);
        assertThat(testMemoire.getLienMemoire()).isEqualTo(UPDATED_LIEN_MEMOIRE);
        assertThat(testMemoire.getDoiMemoire()).isEqualTo(UPDATED_DOI_MEMOIRE);
        assertThat(testMemoire.getHalMemoire()).isEqualTo(UPDATED_HAL_MEMOIRE);
        assertThat(testMemoire.getDiversMemoire()).isEqualTo(UPDATED_DIVERS_MEMOIRE);
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
