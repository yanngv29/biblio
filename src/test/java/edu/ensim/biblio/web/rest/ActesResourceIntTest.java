package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Actes;
import edu.ensim.biblio.repository.ActesRepository;
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
 * Test class for the ActesResource REST controller.
 *
 * @see ActesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ActesResourceIntTest {

    private static final String DEFAULT_TITRE_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_TITRE_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_TITRE_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ACTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE_ACTE = 1;
    private static final Integer UPDATED_ANNEE_ACTE = 2;

    private static final Integer DEFAULT_NUMERO_EDITION_ACTE = 1;
    private static final Integer UPDATED_NUMERO_EDITION_ACTE = 2;

    private static final Integer DEFAULT_VOLUME_ACTE = 1;
    private static final Integer UPDATED_VOLUME_ACTE = 2;

    private static final String DEFAULT_TRADUCTION_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_TRADUCTION_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_MAISON_EDITION_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_MAISON_EDITION_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_HAL_ACTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_ACTE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_ACTE = "BBBBBBBBBB";

    @Autowired
    private ActesRepository actesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActesMockMvc;

    private Actes actes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActesResource actesResource = new ActesResource(actesRepository);
        this.restActesMockMvc = MockMvcBuilders.standaloneSetup(actesResource)
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
    public static Actes createEntity(EntityManager em) {
        Actes actes = new Actes()
            .titreActe(DEFAULT_TITRE_ACTE)
            .sousTitreActe(DEFAULT_SOUS_TITRE_ACTE)
            .typeActe(DEFAULT_TYPE_ACTE)
            .anneeActe(DEFAULT_ANNEE_ACTE)
            .numeroEditionActe(DEFAULT_NUMERO_EDITION_ACTE)
            .volumeActe(DEFAULT_VOLUME_ACTE)
            .traductionActe(DEFAULT_TRADUCTION_ACTE)
            .lieuActe(DEFAULT_LIEU_ACTE)
            .maisonEditionActe(DEFAULT_MAISON_EDITION_ACTE)
            .collectionActe(DEFAULT_COLLECTION_ACTE)
            .langueActe(DEFAULT_LANGUE_ACTE)
            .lienActe(DEFAULT_LIEN_ACTE)
            .halActe(DEFAULT_HAL_ACTE)
            .diversActe(DEFAULT_DIVERS_ACTE);
        return actes;
    }

    @Before
    public void initTest() {
        actes = createEntity(em);
    }

    @Test
    @Transactional
    public void createActes() throws Exception {
        int databaseSizeBeforeCreate = actesRepository.findAll().size();

        // Create the Actes
        restActesMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actes)))
            .andExpect(status().isCreated());

        // Validate the Actes in the database
        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeCreate + 1);
        Actes testActes = actesList.get(actesList.size() - 1);
        assertThat(testActes.getTitreActe()).isEqualTo(DEFAULT_TITRE_ACTE);
        assertThat(testActes.getSousTitreActe()).isEqualTo(DEFAULT_SOUS_TITRE_ACTE);
        assertThat(testActes.getTypeActe()).isEqualTo(DEFAULT_TYPE_ACTE);
        assertThat(testActes.getAnneeActe()).isEqualTo(DEFAULT_ANNEE_ACTE);
        assertThat(testActes.getNumeroEditionActe()).isEqualTo(DEFAULT_NUMERO_EDITION_ACTE);
        assertThat(testActes.getVolumeActe()).isEqualTo(DEFAULT_VOLUME_ACTE);
        assertThat(testActes.getTraductionActe()).isEqualTo(DEFAULT_TRADUCTION_ACTE);
        assertThat(testActes.getLieuActe()).isEqualTo(DEFAULT_LIEU_ACTE);
        assertThat(testActes.getMaisonEditionActe()).isEqualTo(DEFAULT_MAISON_EDITION_ACTE);
        assertThat(testActes.getCollectionActe()).isEqualTo(DEFAULT_COLLECTION_ACTE);
        assertThat(testActes.getLangueActe()).isEqualTo(DEFAULT_LANGUE_ACTE);
        assertThat(testActes.getLienActe()).isEqualTo(DEFAULT_LIEN_ACTE);
        assertThat(testActes.getHalActe()).isEqualTo(DEFAULT_HAL_ACTE);
        assertThat(testActes.getDiversActe()).isEqualTo(DEFAULT_DIVERS_ACTE);
    }

    @Test
    @Transactional
    public void createActesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actesRepository.findAll().size();

        // Create the Actes with an existing ID
        actes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActesMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actes)))
            .andExpect(status().isBadRequest());

        // Validate the Actes in the database
        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitreActeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actesRepository.findAll().size();
        // set the field null
        actes.setTitreActe(null);

        // Create the Actes, which fails.

        restActesMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actes)))
            .andExpect(status().isBadRequest());

        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActes() throws Exception {
        // Initialize the database
        actesRepository.saveAndFlush(actes);

        // Get all the actesList
        restActesMockMvc.perform(get("/api/actes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actes.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreActe").value(hasItem(DEFAULT_TITRE_ACTE.toString())))
            .andExpect(jsonPath("$.[*].sousTitreActe").value(hasItem(DEFAULT_SOUS_TITRE_ACTE.toString())))
            .andExpect(jsonPath("$.[*].typeActe").value(hasItem(DEFAULT_TYPE_ACTE.toString())))
            .andExpect(jsonPath("$.[*].anneeActe").value(hasItem(DEFAULT_ANNEE_ACTE)))
            .andExpect(jsonPath("$.[*].numeroEditionActe").value(hasItem(DEFAULT_NUMERO_EDITION_ACTE)))
            .andExpect(jsonPath("$.[*].volumeActe").value(hasItem(DEFAULT_VOLUME_ACTE)))
            .andExpect(jsonPath("$.[*].traductionActe").value(hasItem(DEFAULT_TRADUCTION_ACTE.toString())))
            .andExpect(jsonPath("$.[*].lieuActe").value(hasItem(DEFAULT_LIEU_ACTE.toString())))
            .andExpect(jsonPath("$.[*].maisonEditionActe").value(hasItem(DEFAULT_MAISON_EDITION_ACTE.toString())))
            .andExpect(jsonPath("$.[*].collectionActe").value(hasItem(DEFAULT_COLLECTION_ACTE.toString())))
            .andExpect(jsonPath("$.[*].langueActe").value(hasItem(DEFAULT_LANGUE_ACTE.toString())))
            .andExpect(jsonPath("$.[*].lienActe").value(hasItem(DEFAULT_LIEN_ACTE.toString())))
            .andExpect(jsonPath("$.[*].halActe").value(hasItem(DEFAULT_HAL_ACTE.toString())))
            .andExpect(jsonPath("$.[*].diversActe").value(hasItem(DEFAULT_DIVERS_ACTE.toString())));
    }

    @Test
    @Transactional
    public void getActes() throws Exception {
        // Initialize the database
        actesRepository.saveAndFlush(actes);

        // Get the actes
        restActesMockMvc.perform(get("/api/actes/{id}", actes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actes.getId().intValue()))
            .andExpect(jsonPath("$.titreActe").value(DEFAULT_TITRE_ACTE.toString()))
            .andExpect(jsonPath("$.sousTitreActe").value(DEFAULT_SOUS_TITRE_ACTE.toString()))
            .andExpect(jsonPath("$.typeActe").value(DEFAULT_TYPE_ACTE.toString()))
            .andExpect(jsonPath("$.anneeActe").value(DEFAULT_ANNEE_ACTE))
            .andExpect(jsonPath("$.numeroEditionActe").value(DEFAULT_NUMERO_EDITION_ACTE))
            .andExpect(jsonPath("$.volumeActe").value(DEFAULT_VOLUME_ACTE))
            .andExpect(jsonPath("$.traductionActe").value(DEFAULT_TRADUCTION_ACTE.toString()))
            .andExpect(jsonPath("$.lieuActe").value(DEFAULT_LIEU_ACTE.toString()))
            .andExpect(jsonPath("$.maisonEditionActe").value(DEFAULT_MAISON_EDITION_ACTE.toString()))
            .andExpect(jsonPath("$.collectionActe").value(DEFAULT_COLLECTION_ACTE.toString()))
            .andExpect(jsonPath("$.langueActe").value(DEFAULT_LANGUE_ACTE.toString()))
            .andExpect(jsonPath("$.lienActe").value(DEFAULT_LIEN_ACTE.toString()))
            .andExpect(jsonPath("$.halActe").value(DEFAULT_HAL_ACTE.toString()))
            .andExpect(jsonPath("$.diversActe").value(DEFAULT_DIVERS_ACTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActes() throws Exception {
        // Get the actes
        restActesMockMvc.perform(get("/api/actes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActes() throws Exception {
        // Initialize the database
        actesRepository.saveAndFlush(actes);
        int databaseSizeBeforeUpdate = actesRepository.findAll().size();

        // Update the actes
        Actes updatedActes = actesRepository.findOne(actes.getId());
        // Disconnect from session so that the updates on updatedActes are not directly saved in db
        em.detach(updatedActes);
        updatedActes
            .titreActe(UPDATED_TITRE_ACTE)
            .sousTitreActe(UPDATED_SOUS_TITRE_ACTE)
            .typeActe(UPDATED_TYPE_ACTE)
            .anneeActe(UPDATED_ANNEE_ACTE)
            .numeroEditionActe(UPDATED_NUMERO_EDITION_ACTE)
            .volumeActe(UPDATED_VOLUME_ACTE)
            .traductionActe(UPDATED_TRADUCTION_ACTE)
            .lieuActe(UPDATED_LIEU_ACTE)
            .maisonEditionActe(UPDATED_MAISON_EDITION_ACTE)
            .collectionActe(UPDATED_COLLECTION_ACTE)
            .langueActe(UPDATED_LANGUE_ACTE)
            .lienActe(UPDATED_LIEN_ACTE)
            .halActe(UPDATED_HAL_ACTE)
            .diversActe(UPDATED_DIVERS_ACTE);

        restActesMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActes)))
            .andExpect(status().isOk());

        // Validate the Actes in the database
        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeUpdate);
        Actes testActes = actesList.get(actesList.size() - 1);
        assertThat(testActes.getTitreActe()).isEqualTo(UPDATED_TITRE_ACTE);
        assertThat(testActes.getSousTitreActe()).isEqualTo(UPDATED_SOUS_TITRE_ACTE);
        assertThat(testActes.getTypeActe()).isEqualTo(UPDATED_TYPE_ACTE);
        assertThat(testActes.getAnneeActe()).isEqualTo(UPDATED_ANNEE_ACTE);
        assertThat(testActes.getNumeroEditionActe()).isEqualTo(UPDATED_NUMERO_EDITION_ACTE);
        assertThat(testActes.getVolumeActe()).isEqualTo(UPDATED_VOLUME_ACTE);
        assertThat(testActes.getTraductionActe()).isEqualTo(UPDATED_TRADUCTION_ACTE);
        assertThat(testActes.getLieuActe()).isEqualTo(UPDATED_LIEU_ACTE);
        assertThat(testActes.getMaisonEditionActe()).isEqualTo(UPDATED_MAISON_EDITION_ACTE);
        assertThat(testActes.getCollectionActe()).isEqualTo(UPDATED_COLLECTION_ACTE);
        assertThat(testActes.getLangueActe()).isEqualTo(UPDATED_LANGUE_ACTE);
        assertThat(testActes.getLienActe()).isEqualTo(UPDATED_LIEN_ACTE);
        assertThat(testActes.getHalActe()).isEqualTo(UPDATED_HAL_ACTE);
        assertThat(testActes.getDiversActe()).isEqualTo(UPDATED_DIVERS_ACTE);
    }

    @Test
    @Transactional
    public void updateNonExistingActes() throws Exception {
        int databaseSizeBeforeUpdate = actesRepository.findAll().size();

        // Create the Actes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActesMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actes)))
            .andExpect(status().isCreated());

        // Validate the Actes in the database
        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActes() throws Exception {
        // Initialize the database
        actesRepository.saveAndFlush(actes);
        int databaseSizeBeforeDelete = actesRepository.findAll().size();

        // Get the actes
        restActesMockMvc.perform(delete("/api/actes/{id}", actes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Actes> actesList = actesRepository.findAll();
        assertThat(actesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actes.class);
        Actes actes1 = new Actes();
        actes1.setId(1L);
        Actes actes2 = new Actes();
        actes2.setId(actes1.getId());
        assertThat(actes1).isEqualTo(actes2);
        actes2.setId(2L);
        assertThat(actes1).isNotEqualTo(actes2);
        actes1.setId(null);
        assertThat(actes1).isNotEqualTo(actes2);
    }
}
