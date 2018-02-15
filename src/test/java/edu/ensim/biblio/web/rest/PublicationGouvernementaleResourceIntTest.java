package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.PublicationGouvernementale;
import edu.ensim.biblio.repository.PublicationGouvernementaleRepository;
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

/**
 * Test class for the PublicationGouvernementaleResource REST controller.
 *
 * @see PublicationGouvernementaleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class PublicationGouvernementaleResourceIntTest {

    private static final String DEFAULT_TITRE_PG = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_PG = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NUMERO_EDITION_PG = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_EDITION_PG = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_PG = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_PG = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION_PG = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION_PG = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_PG = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_PG = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_PG = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_PG = "BBBBBBBBBB";

    private static final String DEFAULT_DOI_PG = "AAAAAAAAAA";
    private static final String UPDATED_DOI_PG = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_PG = "AAAAAAAAAA";
    private static final String UPDATED_HAL_PG = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_OUVRAGE_PG = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_OUVRAGE_PG = "BBBBBBBBBB";

    @Autowired
    private PublicationGouvernementaleRepository publicationGouvernementaleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicationGouvernementaleMockMvc;

    private PublicationGouvernementale publicationGouvernementale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicationGouvernementaleResource publicationGouvernementaleResource = new PublicationGouvernementaleResource(publicationGouvernementaleRepository);
        this.restPublicationGouvernementaleMockMvc = MockMvcBuilders.standaloneSetup(publicationGouvernementaleResource)
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
    public static PublicationGouvernementale createEntity(EntityManager em) {
        PublicationGouvernementale publicationGouvernementale = new PublicationGouvernementale()
            .titrePG(DEFAULT_TITRE_PG)
            .datePG(DEFAULT_DATE_PG)
            .numeroEditionPG(DEFAULT_NUMERO_EDITION_PG)
            .lieuPG(DEFAULT_LIEU_PG)
            .maisonEditionPG(DEFAULT_MAISON_EDITION_PG)
            .languePG(DEFAULT_LANGUE_PG)
            .lienPG(DEFAULT_LIEN_PG)
            .doiPG(DEFAULT_DOI_PG)
            .halPG(DEFAULT_HAL_PG)
            .diversOuvragePG(DEFAULT_DIVERS_OUVRAGE_PG);
        return publicationGouvernementale;
    }

    @Before
    public void initTest() {
        publicationGouvernementale = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicationGouvernementale() throws Exception {
        int databaseSizeBeforeCreate = publicationGouvernementaleRepository.findAll().size();

        // Create the PublicationGouvernementale
        restPublicationGouvernementaleMockMvc.perform(post("/api/publication-gouvernementales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicationGouvernementale)))
            .andExpect(status().isCreated());

        // Validate the PublicationGouvernementale in the database
        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeCreate + 1);
        PublicationGouvernementale testPublicationGouvernementale = publicationGouvernementaleList.get(publicationGouvernementaleList.size() - 1);
        assertThat(testPublicationGouvernementale.getTitrePG()).isEqualTo(DEFAULT_TITRE_PG);
        assertThat(testPublicationGouvernementale.getDatePG()).isEqualTo(DEFAULT_DATE_PG);
        assertThat(testPublicationGouvernementale.getNumeroEditionPG()).isEqualTo(DEFAULT_NUMERO_EDITION_PG);
        assertThat(testPublicationGouvernementale.getLieuPG()).isEqualTo(DEFAULT_LIEU_PG);
        assertThat(testPublicationGouvernementale.getMaisonEditionPG()).isEqualTo(DEFAULT_MAISON_EDITION_PG);
        assertThat(testPublicationGouvernementale.getLanguePG()).isEqualTo(DEFAULT_LANGUE_PG);
        assertThat(testPublicationGouvernementale.getLienPG()).isEqualTo(DEFAULT_LIEN_PG);
        assertThat(testPublicationGouvernementale.getDoiPG()).isEqualTo(DEFAULT_DOI_PG);
        assertThat(testPublicationGouvernementale.getHalPG()).isEqualTo(DEFAULT_HAL_PG);
        assertThat(testPublicationGouvernementale.getDiversOuvragePG()).isEqualTo(DEFAULT_DIVERS_OUVRAGE_PG);
    }

    @Test
    @Transactional
    public void createPublicationGouvernementaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicationGouvernementaleRepository.findAll().size();

        // Create the PublicationGouvernementale with an existing ID
        publicationGouvernementale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationGouvernementaleMockMvc.perform(post("/api/publication-gouvernementales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicationGouvernementale)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationGouvernementale in the database
        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitrePGIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationGouvernementaleRepository.findAll().size();
        // set the field null
        publicationGouvernementale.setTitrePG(null);

        // Create the PublicationGouvernementale, which fails.

        restPublicationGouvernementaleMockMvc.perform(post("/api/publication-gouvernementales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicationGouvernementale)))
            .andExpect(status().isBadRequest());

        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicationGouvernementales() throws Exception {
        // Initialize the database
        publicationGouvernementaleRepository.saveAndFlush(publicationGouvernementale);

        // Get all the publicationGouvernementaleList
        restPublicationGouvernementaleMockMvc.perform(get("/api/publication-gouvernementales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationGouvernementale.getId().intValue())))
            .andExpect(jsonPath("$.[*].titrePG").value(hasItem(DEFAULT_TITRE_PG.toString())))
            .andExpect(jsonPath("$.[*].datePG").value(hasItem(DEFAULT_DATE_PG.toString())))
            .andExpect(jsonPath("$.[*].numeroEditionPG").value(hasItem(DEFAULT_NUMERO_EDITION_PG.toString())))
            .andExpect(jsonPath("$.[*].lieuPG").value(hasItem(DEFAULT_LIEU_PG.toString())))
            .andExpect(jsonPath("$.[*].maisonEditionPG").value(hasItem(DEFAULT_MAISON_EDITION_PG.toString())))
            .andExpect(jsonPath("$.[*].languePG").value(hasItem(DEFAULT_LANGUE_PG.toString())))
            .andExpect(jsonPath("$.[*].lienPG").value(hasItem(DEFAULT_LIEN_PG.toString())))
            .andExpect(jsonPath("$.[*].doiPG").value(hasItem(DEFAULT_DOI_PG.toString())))
            .andExpect(jsonPath("$.[*].halPG").value(hasItem(DEFAULT_HAL_PG.toString())))
            .andExpect(jsonPath("$.[*].diversOuvragePG").value(hasItem(DEFAULT_DIVERS_OUVRAGE_PG.toString())));
    }

    @Test
    @Transactional
    public void getPublicationGouvernementale() throws Exception {
        // Initialize the database
        publicationGouvernementaleRepository.saveAndFlush(publicationGouvernementale);

        // Get the publicationGouvernementale
        restPublicationGouvernementaleMockMvc.perform(get("/api/publication-gouvernementales/{id}", publicationGouvernementale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicationGouvernementale.getId().intValue()))
            .andExpect(jsonPath("$.titrePG").value(DEFAULT_TITRE_PG.toString()))
            .andExpect(jsonPath("$.datePG").value(DEFAULT_DATE_PG.toString()))
            .andExpect(jsonPath("$.numeroEditionPG").value(DEFAULT_NUMERO_EDITION_PG.toString()))
            .andExpect(jsonPath("$.lieuPG").value(DEFAULT_LIEU_PG.toString()))
            .andExpect(jsonPath("$.maisonEditionPG").value(DEFAULT_MAISON_EDITION_PG.toString()))
            .andExpect(jsonPath("$.languePG").value(DEFAULT_LANGUE_PG.toString()))
            .andExpect(jsonPath("$.lienPG").value(DEFAULT_LIEN_PG.toString()))
            .andExpect(jsonPath("$.doiPG").value(DEFAULT_DOI_PG.toString()))
            .andExpect(jsonPath("$.halPG").value(DEFAULT_HAL_PG.toString()))
            .andExpect(jsonPath("$.diversOuvragePG").value(DEFAULT_DIVERS_OUVRAGE_PG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublicationGouvernementale() throws Exception {
        // Get the publicationGouvernementale
        restPublicationGouvernementaleMockMvc.perform(get("/api/publication-gouvernementales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicationGouvernementale() throws Exception {
        // Initialize the database
        publicationGouvernementaleRepository.saveAndFlush(publicationGouvernementale);
        int databaseSizeBeforeUpdate = publicationGouvernementaleRepository.findAll().size();

        // Update the publicationGouvernementale
        PublicationGouvernementale updatedPublicationGouvernementale = publicationGouvernementaleRepository.findOne(publicationGouvernementale.getId());
        // Disconnect from session so that the updates on updatedPublicationGouvernementale are not directly saved in db
        em.detach(updatedPublicationGouvernementale);
        updatedPublicationGouvernementale
            .titrePG(UPDATED_TITRE_PG)
            .datePG(UPDATED_DATE_PG)
            .numeroEditionPG(UPDATED_NUMERO_EDITION_PG)
            .lieuPG(UPDATED_LIEU_PG)
            .maisonEditionPG(UPDATED_MAISON_EDITION_PG)
            .languePG(UPDATED_LANGUE_PG)
            .lienPG(UPDATED_LIEN_PG)
            .doiPG(UPDATED_DOI_PG)
            .halPG(UPDATED_HAL_PG)
            .diversOuvragePG(UPDATED_DIVERS_OUVRAGE_PG);

        restPublicationGouvernementaleMockMvc.perform(put("/api/publication-gouvernementales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicationGouvernementale)))
            .andExpect(status().isOk());

        // Validate the PublicationGouvernementale in the database
        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeUpdate);
        PublicationGouvernementale testPublicationGouvernementale = publicationGouvernementaleList.get(publicationGouvernementaleList.size() - 1);
        assertThat(testPublicationGouvernementale.getTitrePG()).isEqualTo(UPDATED_TITRE_PG);
        assertThat(testPublicationGouvernementale.getDatePG()).isEqualTo(UPDATED_DATE_PG);
        assertThat(testPublicationGouvernementale.getNumeroEditionPG()).isEqualTo(UPDATED_NUMERO_EDITION_PG);
        assertThat(testPublicationGouvernementale.getLieuPG()).isEqualTo(UPDATED_LIEU_PG);
        assertThat(testPublicationGouvernementale.getMaisonEditionPG()).isEqualTo(UPDATED_MAISON_EDITION_PG);
        assertThat(testPublicationGouvernementale.getLanguePG()).isEqualTo(UPDATED_LANGUE_PG);
        assertThat(testPublicationGouvernementale.getLienPG()).isEqualTo(UPDATED_LIEN_PG);
        assertThat(testPublicationGouvernementale.getDoiPG()).isEqualTo(UPDATED_DOI_PG);
        assertThat(testPublicationGouvernementale.getHalPG()).isEqualTo(UPDATED_HAL_PG);
        assertThat(testPublicationGouvernementale.getDiversOuvragePG()).isEqualTo(UPDATED_DIVERS_OUVRAGE_PG);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicationGouvernementale() throws Exception {
        int databaseSizeBeforeUpdate = publicationGouvernementaleRepository.findAll().size();

        // Create the PublicationGouvernementale

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublicationGouvernementaleMockMvc.perform(put("/api/publication-gouvernementales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicationGouvernementale)))
            .andExpect(status().isCreated());

        // Validate the PublicationGouvernementale in the database
        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublicationGouvernementale() throws Exception {
        // Initialize the database
        publicationGouvernementaleRepository.saveAndFlush(publicationGouvernementale);
        int databaseSizeBeforeDelete = publicationGouvernementaleRepository.findAll().size();

        // Get the publicationGouvernementale
        restPublicationGouvernementaleMockMvc.perform(delete("/api/publication-gouvernementales/{id}", publicationGouvernementale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PublicationGouvernementale> publicationGouvernementaleList = publicationGouvernementaleRepository.findAll();
        assertThat(publicationGouvernementaleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationGouvernementale.class);
        PublicationGouvernementale publicationGouvernementale1 = new PublicationGouvernementale();
        publicationGouvernementale1.setId(1L);
        PublicationGouvernementale publicationGouvernementale2 = new PublicationGouvernementale();
        publicationGouvernementale2.setId(publicationGouvernementale1.getId());
        assertThat(publicationGouvernementale1).isEqualTo(publicationGouvernementale2);
        publicationGouvernementale2.setId(2L);
        assertThat(publicationGouvernementale1).isNotEqualTo(publicationGouvernementale2);
        publicationGouvernementale1.setId(null);
        assertThat(publicationGouvernementale1).isNotEqualTo(publicationGouvernementale2);
    }
}
