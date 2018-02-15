package edu.ensim.biblio.web.rest;

import edu.ensim.biblio.BiblioApp;

import edu.ensim.biblio.domain.Article;
import edu.ensim.biblio.domain.Chercheur;
import edu.ensim.biblio.repository.ArticleRepository;
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

import edu.ensim.biblio.domain.enumeration.TypeArticle;
/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblioApp.class)
public class ArticleResourceIntTest {

    private static final String DEFAULT_TITRE_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_ARTICLE = "BBBBBBBBBB";

    private static final TypeArticle DEFAULT_TYPE_ARTICLE = TypeArticle.PUBLIE;
    private static final TypeArticle UPDATED_TYPE_ARTICLE = TypeArticle.EN_PREPUBLICATION;

    private static final String DEFAULT_PAGE_DEBUT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_DEBUT_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE_FIN_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_FIN_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_HAL_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_HAL_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERS_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_DIVERS_ARTICLE = "BBBBBBBBBB";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleMockMvc;

    private Article article;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleResource articleResource = new ArticleResource(articleRepository);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
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
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .titreArticle(DEFAULT_TITRE_ARTICLE)
            .typeArticle(DEFAULT_TYPE_ARTICLE)
            .pageDebutArticle(DEFAULT_PAGE_DEBUT_ARTICLE)
            .pageFinArticle(DEFAULT_PAGE_FIN_ARTICLE)
            .langueArticle(DEFAULT_LANGUE_ARTICLE)
            .lienArticle(DEFAULT_LIEN_ARTICLE)
            .halArticle(DEFAULT_HAL_ARTICLE)
            .diversArticle(DEFAULT_DIVERS_ARTICLE);
        // Add required entity
        Chercheur auteur = ChercheurResourceIntTest.createEntity(em);
        em.persist(auteur);
        em.flush();
        article.getAuteurs().add(auteur);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getTitreArticle()).isEqualTo(DEFAULT_TITRE_ARTICLE);
        assertThat(testArticle.getTypeArticle()).isEqualTo(DEFAULT_TYPE_ARTICLE);
        assertThat(testArticle.getPageDebutArticle()).isEqualTo(DEFAULT_PAGE_DEBUT_ARTICLE);
        assertThat(testArticle.getPageFinArticle()).isEqualTo(DEFAULT_PAGE_FIN_ARTICLE);
        assertThat(testArticle.getLangueArticle()).isEqualTo(DEFAULT_LANGUE_ARTICLE);
        assertThat(testArticle.getLienArticle()).isEqualTo(DEFAULT_LIEN_ARTICLE);
        assertThat(testArticle.getHalArticle()).isEqualTo(DEFAULT_HAL_ARTICLE);
        assertThat(testArticle.getDiversArticle()).isEqualTo(DEFAULT_DIVERS_ARTICLE);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitreArticleIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setTitreArticle(null);

        // Create the Article, which fails.

        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreArticle").value(hasItem(DEFAULT_TITRE_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].typeArticle").value(hasItem(DEFAULT_TYPE_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].pageDebutArticle").value(hasItem(DEFAULT_PAGE_DEBUT_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].pageFinArticle").value(hasItem(DEFAULT_PAGE_FIN_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].langueArticle").value(hasItem(DEFAULT_LANGUE_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].lienArticle").value(hasItem(DEFAULT_LIEN_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].halArticle").value(hasItem(DEFAULT_HAL_ARTICLE.toString())))
            .andExpect(jsonPath("$.[*].diversArticle").value(hasItem(DEFAULT_DIVERS_ARTICLE.toString())));
    }

    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.titreArticle").value(DEFAULT_TITRE_ARTICLE.toString()))
            .andExpect(jsonPath("$.typeArticle").value(DEFAULT_TYPE_ARTICLE.toString()))
            .andExpect(jsonPath("$.pageDebutArticle").value(DEFAULT_PAGE_DEBUT_ARTICLE.toString()))
            .andExpect(jsonPath("$.pageFinArticle").value(DEFAULT_PAGE_FIN_ARTICLE.toString()))
            .andExpect(jsonPath("$.langueArticle").value(DEFAULT_LANGUE_ARTICLE.toString()))
            .andExpect(jsonPath("$.lienArticle").value(DEFAULT_LIEN_ARTICLE.toString()))
            .andExpect(jsonPath("$.halArticle").value(DEFAULT_HAL_ARTICLE.toString()))
            .andExpect(jsonPath("$.diversArticle").value(DEFAULT_DIVERS_ARTICLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findOne(article.getId());
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .titreArticle(UPDATED_TITRE_ARTICLE)
            .typeArticle(UPDATED_TYPE_ARTICLE)
            .pageDebutArticle(UPDATED_PAGE_DEBUT_ARTICLE)
            .pageFinArticle(UPDATED_PAGE_FIN_ARTICLE)
            .langueArticle(UPDATED_LANGUE_ARTICLE)
            .lienArticle(UPDATED_LIEN_ARTICLE)
            .halArticle(UPDATED_HAL_ARTICLE)
            .diversArticle(UPDATED_DIVERS_ARTICLE);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getTitreArticle()).isEqualTo(UPDATED_TITRE_ARTICLE);
        assertThat(testArticle.getTypeArticle()).isEqualTo(UPDATED_TYPE_ARTICLE);
        assertThat(testArticle.getPageDebutArticle()).isEqualTo(UPDATED_PAGE_DEBUT_ARTICLE);
        assertThat(testArticle.getPageFinArticle()).isEqualTo(UPDATED_PAGE_FIN_ARTICLE);
        assertThat(testArticle.getLangueArticle()).isEqualTo(UPDATED_LANGUE_ARTICLE);
        assertThat(testArticle.getLienArticle()).isEqualTo(UPDATED_LIEN_ARTICLE);
        assertThat(testArticle.getHalArticle()).isEqualTo(UPDATED_HAL_ARTICLE);
        assertThat(testArticle.getDiversArticle()).isEqualTo(UPDATED_DIVERS_ARTICLE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);
        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = new Article();
        article1.setId(1L);
        Article article2 = new Article();
        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);
        article2.setId(2L);
        assertThat(article1).isNotEqualTo(article2);
        article1.setId(null);
        assertThat(article1).isNotEqualTo(article2);
    }
}
