package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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
    @Column(name = "id_article", nullable = false)
    private String idArticle;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeArticle type;

    @Column(name = "page_debut")
    private String pageDebut;

    @Column(name = "page_fin")
    private String pageFin;

    @Column(name = "hal")
    private String hal;

    @ManyToOne
    private NumeroRevue numeroRevue;

    @ManyToOne
    private Acte acte;

    @ManyToOne
    private Conference conference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdArticle() {
        return idArticle;
    }

    public Article idArticle(String idArticle) {
        this.idArticle = idArticle;
        return this;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public TypeArticle getType() {
        return type;
    }

    public Article type(TypeArticle type) {
        this.type = type;
        return this;
    }

    public void setType(TypeArticle type) {
        this.type = type;
    }

    public String getPageDebut() {
        return pageDebut;
    }

    public Article pageDebut(String pageDebut) {
        this.pageDebut = pageDebut;
        return this;
    }

    public void setPageDebut(String pageDebut) {
        this.pageDebut = pageDebut;
    }

    public String getPageFin() {
        return pageFin;
    }

    public Article pageFin(String pageFin) {
        this.pageFin = pageFin;
        return this;
    }

    public void setPageFin(String pageFin) {
        this.pageFin = pageFin;
    }

    public String getHal() {
        return hal;
    }

    public Article hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
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

    public Acte getActe() {
        return acte;
    }

    public Article acte(Acte acte) {
        this.acte = acte;
        return this;
    }

    public void setActe(Acte acte) {
        this.acte = acte;
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
            ", idArticle='" + getIdArticle() + "'" +
            ", type='" + getType() + "'" +
            ", pageDebut='" + getPageDebut() + "'" +
            ", pageFin='" + getPageFin() + "'" +
            ", hal='" + getHal() + "'" +
            "}";
    }
}
