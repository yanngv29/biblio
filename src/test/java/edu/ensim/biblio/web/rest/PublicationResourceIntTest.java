package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Publication;
import edu.ensim.biblio.repository.PublicationRepository;
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
 * Test class for the PublicationResource REST controller.
 *
 * @see PublicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class PublicationResourceIntTest {

    private static final String DEFAULT_ID_PUBLICATION = "AAAAAAAAAA";
    private static final String UPDATED_ID_PUBLICATION = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String DEFAULT_DOI = "AAAAAAAAAA";
    private static final String UPDATED_DOI = "BBBBBBBBBB";

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicationMockMvc;

    private Publication publication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicationResource publicationResource = new PublicationResource(publicationRepository);
        this.restPublicationMockMvc = MockMvcBuilders.standaloneSetup(publicationResource)
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
    public static Publication createEntity(EntityManager em) {
        Publication publication = new Publication()
            .idPublication(DEFAULT_ID_PUBLICATION)
            .titre(DEFAULT_TITRE)
            .divers(DEFAULT_DIVERS)
            .lien(DEFAULT_LIEN)
            .doi(DEFAULT_DOI);
        return publication;
    }

    @Before
    public void initTest() {
        publication = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublication() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // Create the Publication
        restPublicationMockMvc.perform(post("/api/publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publication)))
            .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeCreate + 1);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getIdPublication()).isEqualTo(DEFAULT_ID_PUBLICATION);
        assertThat(testPublication.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPublication.getDivers()).isEqualTo(DEFAULT_DIVERS);
        assertThat(testPublication.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testPublication.getDoi()).isEqualTo(DEFAULT_DOI);
    }

    @Test
    @Transactional
    public void createPublicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // Create the Publication with an existing ID
        publication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationMockMvc.perform(post("/api/publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publication)))
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdPublicationIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setIdPublication(null);

        // Create the Publication, which fails.

        restPublicationMockMvc.perform(post("/api/publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publication)))
            .andExpect(status().isBadRequest());

        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublications() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get all the publicationList
        restPublicationMockMvc.perform(get("/api/publications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPublication").value(hasItem(DEFAULT_ID_PUBLICATION.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].divers").value(hasItem(DEFAULT_DIVERS.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].doi").value(hasItem(DEFAULT_DOI.toString())));
    }

    @Test
    @Transactional
    public void getPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publication.getId().intValue()))
            .andExpect(jsonPath("$.idPublication").value(DEFAULT_ID_PUBLICATION.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.divers").value(DEFAULT_DIVERS.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.doi").value(DEFAULT_DOI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication
        Publication updatedPublication = publicationRepository.findOne(publication.getId());
        // Disconnect from session so that the updates on updatedPublication are not directly saved in db
        em.detach(updatedPublication);
        updatedPublication
            .idPublication(UPDATED_ID_PUBLICATION)
            .titre(UPDATED_TITRE)
            .divers(UPDATED_DIVERS)
            .lien(UPDATED_LIEN)
            .doi(UPDATED_DOI);

        restPublicationMockMvc.perform(put("/api/publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublication)))
            .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getIdPublication()).isEqualTo(UPDATED_ID_PUBLICATION);
        assertThat(testPublication.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPublication.getDivers()).isEqualTo(UPDATED_DIVERS);
        assertThat(testPublication.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testPublication.getDoi()).isEqualTo(UPDATED_DOI);
    }

    @Test
    @Transactional
    public void updateNonExistingPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Create the Publication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublicationMockMvc.perform(put("/api/publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publication)))
            .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);
        int databaseSizeBeforeDelete = publicationRepository.findAll().size();

        // Get the publication
        restPublicationMockMvc.perform(delete("/api/publications/{id}", publication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publication.class);
        Publication publication1 = new Publication();
        publication1.setId(1L);
        Publication publication2 = new Publication();
        publication2.setId(publication1.getId());
        assertThat(publication1).isEqualTo(publication2);
        publication2.setId(2L);
        assertThat(publication1).isNotEqualTo(publication2);
        publication1.setId(null);
        assertThat(publication1).isNotEqualTo(publication2);
    }
}
