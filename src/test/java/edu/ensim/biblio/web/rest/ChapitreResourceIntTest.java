package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Chapitre;
import edu.ensim.biblio.repository.ChapitreRepository;
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
 * Test class for the ChapitreResource REST controller.
 *
 * @see ChapitreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ChapitreResourceIntTest {

    private static final String DEFAULT_TITRE_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_TITRE_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_TITRE_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE_DEBUT_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_DEBUT_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE_FIN_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_FIN_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_HAL_CHAPITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_CHAPITRE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_CHAPITRE = "BBBBBBBBBB";

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChapitreMockMvc;

    private Chapitre chapitre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChapitreResource chapitreResource = new ChapitreResource(chapitreRepository);
        this.restChapitreMockMvc = MockMvcBuilders.standaloneSetup(chapitreResource)
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
    public static Chapitre createEntity(EntityManager em) {
        Chapitre chapitre = new Chapitre()
            .titreChapitre(DEFAULT_TITRE_CHAPITRE)
            .sousTitreChapitre(DEFAULT_SOUS_TITRE_CHAPITRE)
            .pageDebutChapitre(DEFAULT_PAGE_DEBUT_CHAPITRE)
            .pageFinChapitre(DEFAULT_PAGE_FIN_CHAPITRE)
            .langueChapitre(DEFAULT_LANGUE_CHAPITRE)
            .lienChapitre(DEFAULT_LIEN_CHAPITRE)
            .halChapitre(DEFAULT_HAL_CHAPITRE)
            .diversChapitre(DEFAULT_DIVERS_CHAPITRE);
        return chapitre;
    }

    @Before
    public void initTest() {
        chapitre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapitre() throws Exception {
        int databaseSizeBeforeCreate = chapitreRepository.findAll().size();

        // Create the Chapitre
        restChapitreMockMvc.perform(post("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isCreated());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeCreate + 1);
        Chapitre testChapitre = chapitreList.get(chapitreList.size() - 1);
        assertThat(testChapitre.getTitreChapitre()).isEqualTo(DEFAULT_TITRE_CHAPITRE);
        assertThat(testChapitre.getSousTitreChapitre()).isEqualTo(DEFAULT_SOUS_TITRE_CHAPITRE);
        assertThat(testChapitre.getPageDebutChapitre()).isEqualTo(DEFAULT_PAGE_DEBUT_CHAPITRE);
        assertThat(testChapitre.getPageFinChapitre()).isEqualTo(DEFAULT_PAGE_FIN_CHAPITRE);
        assertThat(testChapitre.getLangueChapitre()).isEqualTo(DEFAULT_LANGUE_CHAPITRE);
        assertThat(testChapitre.getLienChapitre()).isEqualTo(DEFAULT_LIEN_CHAPITRE);
        assertThat(testChapitre.getHalChapitre()).isEqualTo(DEFAULT_HAL_CHAPITRE);
        assertThat(testChapitre.getDiversChapitre()).isEqualTo(DEFAULT_DIVERS_CHAPITRE);
    }

    @Test
    @Transactional
    public void createChapitreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chapitreRepository.findAll().size();

        // Create the Chapitre with an existing ID
        chapitre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChapitreMockMvc.perform(post("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isBadRequest());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitreChapitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = chapitreRepository.findAll().size();
        // set the field null
        chapitre.setTitreChapitre(null);

        // Create the Chapitre, which fails.

        restChapitreMockMvc.perform(post("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isBadRequest());

        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChapitres() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get all the chapitreList
        restChapitreMockMvc.perform(get("/api/chapitres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapitre.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreChapitre").value(hasItem(DEFAULT_TITRE_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].sousTitreChapitre").value(hasItem(DEFAULT_SOUS_TITRE_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].pageDebutChapitre").value(hasItem(DEFAULT_PAGE_DEBUT_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].pageFinChapitre").value(hasItem(DEFAULT_PAGE_FIN_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].langueChapitre").value(hasItem(DEFAULT_LANGUE_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].lienChapitre").value(hasItem(DEFAULT_LIEN_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].halChapitre").value(hasItem(DEFAULT_HAL_CHAPITRE.toString())))
            .andExpect(jsonPath("$.[*].diversChapitre").value(hasItem(DEFAULT_DIVERS_CHAPITRE.toString())));
    }

    @Test
    @Transactional
    public void getChapitre() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);

        // Get the chapitre
        restChapitreMockMvc.perform(get("/api/chapitres/{id}", chapitre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapitre.getId().intValue()))
            .andExpect(jsonPath("$.titreChapitre").value(DEFAULT_TITRE_CHAPITRE.toString()))
            .andExpect(jsonPath("$.sousTitreChapitre").value(DEFAULT_SOUS_TITRE_CHAPITRE.toString()))
            .andExpect(jsonPath("$.pageDebutChapitre").value(DEFAULT_PAGE_DEBUT_CHAPITRE.toString()))
            .andExpect(jsonPath("$.pageFinChapitre").value(DEFAULT_PAGE_FIN_CHAPITRE.toString()))
            .andExpect(jsonPath("$.langueChapitre").value(DEFAULT_LANGUE_CHAPITRE.toString()))
            .andExpect(jsonPath("$.lienChapitre").value(DEFAULT_LIEN_CHAPITRE.toString()))
            .andExpect(jsonPath("$.halChapitre").value(DEFAULT_HAL_CHAPITRE.toString()))
            .andExpect(jsonPath("$.diversChapitre").value(DEFAULT_DIVERS_CHAPITRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChapitre() throws Exception {
        // Get the chapitre
        restChapitreMockMvc.perform(get("/api/chapitres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapitre() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);
        int databaseSizeBeforeUpdate = chapitreRepository.findAll().size();

        // Update the chapitre
        Chapitre updatedChapitre = chapitreRepository.findOne(chapitre.getId());
        // Disconnect from session so that the updates on updatedChapitre are not directly saved in db
        em.detach(updatedChapitre);
        updatedChapitre
            .titreChapitre(UPDATED_TITRE_CHAPITRE)
            .sousTitreChapitre(UPDATED_SOUS_TITRE_CHAPITRE)
            .pageDebutChapitre(UPDATED_PAGE_DEBUT_CHAPITRE)
            .pageFinChapitre(UPDATED_PAGE_FIN_CHAPITRE)
            .langueChapitre(UPDATED_LANGUE_CHAPITRE)
            .lienChapitre(UPDATED_LIEN_CHAPITRE)
            .halChapitre(UPDATED_HAL_CHAPITRE)
            .diversChapitre(UPDATED_DIVERS_CHAPITRE);

        restChapitreMockMvc.perform(put("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChapitre)))
            .andExpect(status().isOk());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeUpdate);
        Chapitre testChapitre = chapitreList.get(chapitreList.size() - 1);
        assertThat(testChapitre.getTitreChapitre()).isEqualTo(UPDATED_TITRE_CHAPITRE);
        assertThat(testChapitre.getSousTitreChapitre()).isEqualTo(UPDATED_SOUS_TITRE_CHAPITRE);
        assertThat(testChapitre.getPageDebutChapitre()).isEqualTo(UPDATED_PAGE_DEBUT_CHAPITRE);
        assertThat(testChapitre.getPageFinChapitre()).isEqualTo(UPDATED_PAGE_FIN_CHAPITRE);
        assertThat(testChapitre.getLangueChapitre()).isEqualTo(UPDATED_LANGUE_CHAPITRE);
        assertThat(testChapitre.getLienChapitre()).isEqualTo(UPDATED_LIEN_CHAPITRE);
        assertThat(testChapitre.getHalChapitre()).isEqualTo(UPDATED_HAL_CHAPITRE);
        assertThat(testChapitre.getDiversChapitre()).isEqualTo(UPDATED_DIVERS_CHAPITRE);
    }

    @Test
    @Transactional
    public void updateNonExistingChapitre() throws Exception {
        int databaseSizeBeforeUpdate = chapitreRepository.findAll().size();

        // Create the Chapitre

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChapitreMockMvc.perform(put("/api/chapitres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapitre)))
            .andExpect(status().isCreated());

        // Validate the Chapitre in the database
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChapitre() throws Exception {
        // Initialize the database
        chapitreRepository.saveAndFlush(chapitre);
        int databaseSizeBeforeDelete = chapitreRepository.findAll().size();

        // Get the chapitre
        restChapitreMockMvc.perform(delete("/api/chapitres/{id}", chapitre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chapitre> chapitreList = chapitreRepository.findAll();
        assertThat(chapitreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapitre.class);
        Chapitre chapitre1 = new Chapitre();
        chapitre1.setId(1L);
        Chapitre chapitre2 = new Chapitre();
        chapitre2.setId(chapitre1.getId());
        assertThat(chapitre1).isEqualTo(chapitre2);
        chapitre2.setId(2L);
        assertThat(chapitre1).isNotEqualTo(chapitre2);
        chapitre1.setId(null);
        assertThat(chapitre1).isNotEqualTo(chapitre2);
    }
}
