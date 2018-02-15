package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Chercheur;
import edu.ensim.biblio.repository.ChercheurRepository;
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

import edu.ensim.biblio.domain.enumeration.TypeChercheur;
/**
 * Test class for the ChercheurResource REST controller.
 *
 * @see ChercheurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ChercheurResourceIntTest {

    private static final String DEFAULT_NOM_CHERCHEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CHERCHEUR = "BBBBBBBBBB";

    private static final TypeChercheur DEFAULT_TYPE_CHERCHEUR = TypeChercheur.PUBLIANT;
    private static final TypeChercheur UPDATED_TYPE_CHERCHEUR = TypeChercheur.ENCADRANT;

    private static final String DEFAULT_RANG_CHERCHEUR = "AAAAAAAAAA";
    private static final String UPDATED_RANG_CHERCHEUR = "BBBBBBBBBB";

    @Autowired
    private ChercheurRepository chercheurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChercheurMockMvc;

    private Chercheur chercheur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChercheurResource chercheurResource = new ChercheurResource(chercheurRepository);
        this.restChercheurMockMvc = MockMvcBuilders.standaloneSetup(chercheurResource)
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
    public static Chercheur createEntity(EntityManager em) {
        Chercheur chercheur = new Chercheur()
            .nomChercheur(DEFAULT_NOM_CHERCHEUR)
            .typeChercheur(DEFAULT_TYPE_CHERCHEUR)
            .rangChercheur(DEFAULT_RANG_CHERCHEUR);
        return chercheur;
    }

    @Before
    public void initTest() {
        chercheur = createEntity(em);
    }

    @Test
    @Transactional
    public void createChercheur() throws Exception {
        int databaseSizeBeforeCreate = chercheurRepository.findAll().size();

        // Create the Chercheur
        restChercheurMockMvc.perform(post("/api/chercheurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chercheur)))
            .andExpect(status().isCreated());

        // Validate the Chercheur in the database
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeCreate + 1);
        Chercheur testChercheur = chercheurList.get(chercheurList.size() - 1);
        assertThat(testChercheur.getNomChercheur()).isEqualTo(DEFAULT_NOM_CHERCHEUR);
        assertThat(testChercheur.getTypeChercheur()).isEqualTo(DEFAULT_TYPE_CHERCHEUR);
        assertThat(testChercheur.getRangChercheur()).isEqualTo(DEFAULT_RANG_CHERCHEUR);
    }

    @Test
    @Transactional
    public void createChercheurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chercheurRepository.findAll().size();

        // Create the Chercheur with an existing ID
        chercheur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChercheurMockMvc.perform(post("/api/chercheurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chercheur)))
            .andExpect(status().isBadRequest());

        // Validate the Chercheur in the database
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomChercheurIsRequired() throws Exception {
        int databaseSizeBeforeTest = chercheurRepository.findAll().size();
        // set the field null
        chercheur.setNomChercheur(null);

        // Create the Chercheur, which fails.

        restChercheurMockMvc.perform(post("/api/chercheurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chercheur)))
            .andExpect(status().isBadRequest());

        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChercheurs() throws Exception {
        // Initialize the database
        chercheurRepository.saveAndFlush(chercheur);

        // Get all the chercheurList
        restChercheurMockMvc.perform(get("/api/chercheurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chercheur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomChercheur").value(hasItem(DEFAULT_NOM_CHERCHEUR.toString())))
            .andExpect(jsonPath("$.[*].typeChercheur").value(hasItem(DEFAULT_TYPE_CHERCHEUR.toString())))
            .andExpect(jsonPath("$.[*].rangChercheur").value(hasItem(DEFAULT_RANG_CHERCHEUR.toString())));
    }

    @Test
    @Transactional
    public void getChercheur() throws Exception {
        // Initialize the database
        chercheurRepository.saveAndFlush(chercheur);

        // Get the chercheur
        restChercheurMockMvc.perform(get("/api/chercheurs/{id}", chercheur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chercheur.getId().intValue()))
            .andExpect(jsonPath("$.nomChercheur").value(DEFAULT_NOM_CHERCHEUR.toString()))
            .andExpect(jsonPath("$.typeChercheur").value(DEFAULT_TYPE_CHERCHEUR.toString()))
            .andExpect(jsonPath("$.rangChercheur").value(DEFAULT_RANG_CHERCHEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChercheur() throws Exception {
        // Get the chercheur
        restChercheurMockMvc.perform(get("/api/chercheurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChercheur() throws Exception {
        // Initialize the database
        chercheurRepository.saveAndFlush(chercheur);
        int databaseSizeBeforeUpdate = chercheurRepository.findAll().size();

        // Update the chercheur
        Chercheur updatedChercheur = chercheurRepository.findOne(chercheur.getId());
        // Disconnect from session so that the updates on updatedChercheur are not directly saved in db
        em.detach(updatedChercheur);
        updatedChercheur
            .nomChercheur(UPDATED_NOM_CHERCHEUR)
            .typeChercheur(UPDATED_TYPE_CHERCHEUR)
            .rangChercheur(UPDATED_RANG_CHERCHEUR);

        restChercheurMockMvc.perform(put("/api/chercheurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChercheur)))
            .andExpect(status().isOk());

        // Validate the Chercheur in the database
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeUpdate);
        Chercheur testChercheur = chercheurList.get(chercheurList.size() - 1);
        assertThat(testChercheur.getNomChercheur()).isEqualTo(UPDATED_NOM_CHERCHEUR);
        assertThat(testChercheur.getTypeChercheur()).isEqualTo(UPDATED_TYPE_CHERCHEUR);
        assertThat(testChercheur.getRangChercheur()).isEqualTo(UPDATED_RANG_CHERCHEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingChercheur() throws Exception {
        int databaseSizeBeforeUpdate = chercheurRepository.findAll().size();

        // Create the Chercheur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChercheurMockMvc.perform(put("/api/chercheurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chercheur)))
            .andExpect(status().isCreated());

        // Validate the Chercheur in the database
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChercheur() throws Exception {
        // Initialize the database
        chercheurRepository.saveAndFlush(chercheur);
        int databaseSizeBeforeDelete = chercheurRepository.findAll().size();

        // Get the chercheur
        restChercheurMockMvc.perform(delete("/api/chercheurs/{id}", chercheur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chercheur> chercheurList = chercheurRepository.findAll();
        assertThat(chercheurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chercheur.class);
        Chercheur chercheur1 = new Chercheur();
        chercheur1.setId(1L);
        Chercheur chercheur2 = new Chercheur();
        chercheur2.setId(chercheur1.getId());
        assertThat(chercheur1).isEqualTo(chercheur2);
        chercheur2.setId(2L);
        assertThat(chercheur1).isNotEqualTo(chercheur2);
        chercheur1.setId(null);
        assertThat(chercheur1).isNotEqualTo(chercheur2);
    }
}
