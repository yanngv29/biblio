package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Contribution;
import edu.ensim.biblio.repository.ContributionRepository;
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

import edu.ensim.biblio.domain.enumeration.Participation;
/**
 * Test class for the ContributionResource REST controller.
 *
 * @see ContributionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ContributionResourceIntTest {

    private static final String DEFAULT_ID_CHERCHEUR = "AAAAAAAAAA";
    private static final String UPDATED_ID_CHERCHEUR = "BBBBBBBBBB";

    private static final Participation DEFAULT_TYPE = Participation.PUBLICATION;
    private static final Participation UPDATED_TYPE = Participation.ENCADREMENT;

    private static final String DEFAULT_RANG = "AAAAAAAAAA";
    private static final String UPDATED_RANG = "BBBBBBBBBB";

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributionMockMvc;

    private Contribution contribution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContributionResource contributionResource = new ContributionResource(contributionRepository);
        this.restContributionMockMvc = MockMvcBuilders.standaloneSetup(contributionResource)
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
    public static Contribution createEntity(EntityManager em) {
        Contribution contribution = new Contribution()
            .idChercheur(DEFAULT_ID_CHERCHEUR)
            .type(DEFAULT_TYPE)
            .rang(DEFAULT_RANG);
        return contribution;
    }

    @Before
    public void initTest() {
        contribution = createEntity(em);
    }

    @Test
    @Transactional
    public void createContribution() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate + 1);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getIdChercheur()).isEqualTo(DEFAULT_ID_CHERCHEUR);
        assertThat(testContribution.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContribution.getRang()).isEqualTo(DEFAULT_RANG);
    }

    @Test
    @Transactional
    public void createContributionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution with an existing ID
        contribution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdChercheurIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setIdChercheur(null);

        // Create the Contribution, which fails.

        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContributions() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get all the contributionList
        restContributionMockMvc.perform(get("/api/contributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].idChercheur").value(hasItem(DEFAULT_ID_CHERCHEUR.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rang").value(hasItem(DEFAULT_RANG.toString())));
    }

    @Test
    @Transactional
    public void getContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", contribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contribution.getId().intValue()))
            .andExpect(jsonPath("$.idChercheur").value(DEFAULT_ID_CHERCHEUR.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rang").value(DEFAULT_RANG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContribution() throws Exception {
        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution
        Contribution updatedContribution = contributionRepository.findOne(contribution.getId());
        // Disconnect from session so that the updates on updatedContribution are not directly saved in db
        em.detach(updatedContribution);
        updatedContribution
            .idChercheur(UPDATED_ID_CHERCHEUR)
            .type(UPDATED_TYPE)
            .rang(UPDATED_RANG);

        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContribution)))
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getIdChercheur()).isEqualTo(UPDATED_ID_CHERCHEUR);
        assertThat(testContribution.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContribution.getRang()).isEqualTo(UPDATED_RANG);
    }

    @Test
    @Transactional
    public void updateNonExistingContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Create the Contribution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);
        int databaseSizeBeforeDelete = contributionRepository.findAll().size();

        // Get the contribution
        restContributionMockMvc.perform(delete("/api/contributions/{id}", contribution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contribution.class);
        Contribution contribution1 = new Contribution();
        contribution1.setId(1L);
        Contribution contribution2 = new Contribution();
        contribution2.setId(contribution1.getId());
        assertThat(contribution1).isEqualTo(contribution2);
        contribution2.setId(2L);
        assertThat(contribution1).isNotEqualTo(contribution2);
        contribution1.setId(null);
        assertThat(contribution1).isNotEqualTo(contribution2);
    }
}
