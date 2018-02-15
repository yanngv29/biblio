package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.TypeArticle;

/**
 * Article : type et identifiant obligatoires
 */
@ApiModel(description = "Article : type et identifiant obligatoires")
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_article", nullable = false)
    private String titreArticle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_article")
    private TypeArticle typeArticle;

    @Column(name = "page_debut_article")
    private String pageDebutArticle;

    @Column(name = "page_fin_article")
    private String pageFinArticle;

    @Column(name = "langue_article")
    private String langueArticle;

    @Column(name = "lien_article")
    private String lienArticle;

    @Column(name = "hal_article")
    private String halArticle;

    @Column(name = "divers_article")
    private String diversArticle;

    @ManyToOne
    private NumeroRevue numeroRevue;

    @ManyToOne
    private Actes actes;

    @ManyToOne
    private Conference conference;

    @ManyToMany(mappedBy = "articles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chercheur> auteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreArticle() {
        return titreArticle;
    }

    public Article titreArticle(String titreArticle) {
        this.titreArticle = titreArticle;
        return this;
    }

    public void setTitreArticle(String titreArticle) {
        this.titreArticle = titreArticle;
    }

    public TypeArticle getTypeArticle() {
        return typeArticle;
    }

    public Article typeArticle(TypeArticle typeArticle) {
        this.typeArticle = typeArticle;
        return this;
    }

    public void setTypeArticle(TypeArticle typeArticle) {
        this.typeArticle = typeArticle;
    }

    public String getPageDebutArticle() {
        return pageDebutArticle;
    }

    public Article pageDebutArticle(String pageDebutArticle) {
        this.pageDebutArticle = pageDebutArticle;
        return this;
    }

    public void setPageDebutArticle(String pageDebutArticle) {
        this.pageDebutArticle = pageDebutArticle;
    }

    public String getPageFinArticle() {
        return pageFinArticle;
    }

    public Article pageFinArticle(String pageFinArticle) {
        this.pageFinArticle = pageFinArticle;
        return this;
    }

    public void setPageFinArticle(String pageFinArticle) {
        this.pageFinArticle = pageFinArticle;
    }

    public String getLangueArticle() {
        return langueArticle;
    }

    public Article langueArticle(String langueArticle) {
        this.langueArticle = langueArticle;
        return this;
    }

    public void setLangueArticle(String langueArticle) {
        this.langueArticle = langueArticle;
    }

    public String getLienArticle() {
        return lienArticle;
    }

    public Article lienArticle(String lienArticle) {
        this.lienArticle = lienArticle;
        return this;
    }

    public void setLienArticle(String lienArticle) {
        this.lienArticle = lienArticle;
    }

    public String getHalArticle() {
        return halArticle;
    }

    public Article halArticle(String halArticle) {
        this.halArticle = halArticle;
        return this;
    }

    public void setHalArticle(String halArticle) {
        this.halArticle = halArticle;
    }

    public String getDiversArticle() {
        return diversArticle;
    }

    public Article diversArticle(String diversArticle) {
        this.diversArticle = diversArticle;
        return this;
    }

    public void setDiversArticle(String diversArticle) {
        this.diversArticle = diversArticle;
    }

    public NumeroRevue getNumeroRevue() {
        return numeroRevue;
    }

    public Article numeroRevue(NumeroRevue numeroRevue) {
        this.numeroRevue = numeroRevue;
        return this;
    }

    public void setNumeroRevue(NumeroRevue numeroRevue) {
        this.numeroRevue = numeroRevue;
    }

    public Actes getActes() {
        return actes;
    }

    public Article actes(Actes actes) {
        this.actes = actes;
        return this;
    }

    public void setActes(Actes actes) {
        this.actes = actes;
    }

    public Conference getConference() {
        return conference;
    }

    public Article conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Set<Chercheur> getAuteurs() {
        return auteurs;
    }

    public Article auteurs(Set<Chercheur> chercheurs) {
        this.auteurs = chercheurs;
        return this;
    }

    public Article addAuteur(Chercheur chercheur) {
        this.auteurs.add(chercheur);
        chercheur.getArticles().add(this);
        return this;
    }

    public Article removeAuteur(Chercheur chercheur) {
        this.auteurs.remove(chercheur);
        chercheur.getArticles().remove(this);
        return this;
    }

    public void setAuteurs(Set<Chercheur> chercheurs) {
        this.auteurs = chercheurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if (article.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", titreArticle='" + getTitreArticle() + "'" +
            ", typeArticle='" + getTypeArticle() + "'" +
            ", pageDebutArticle='" + getPageDebutArticle() + "'" +
            ", pageFinArticle='" + getPageFinArticle() + "'" +
            ", langueArticle='" + getLangueArticle() + "'" +
            ", lienArticle='" + getLienArticle() + "'" +
            ", halArticle='" + getHalArticle() + "'" +
            ", diversArticle='" + getDiversArticle() + "'" +
            "}";
    }
}
