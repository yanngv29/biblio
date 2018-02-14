package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Publication : obligatoirement constituée d'un titre et d'un identifiant
 */
@ApiModel(description = "Publication : obligatoirement constituée d'un titre et d'un identifiant")
@Entity
@Table(name = "publication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_publication", nullable = false)
    private String idPublication;

    @Column(name = "titre")
    private String titre;

    @Column(name = "divers")
    private String divers;

    @Column(name = "lien")
    private String lien;

    @Column(name = "doi")
    private String doi;

    @OneToOne
    @JoinColumn(unique = true)
    private Article article;

    @OneToOne
    @JoinColumn(unique = true)
    private Communication communication;

    @OneToOne
    @JoinColumn(unique = true)
    private Memoire memoire;

    @OneToOne
    @JoinColumn(unique = true)
    private Ouvrage ouvrage;

    @OneToOne
    @JoinColumn(unique = true)
    private PublicationGouvernementale publicationGouvernementale;

    @OneToOne
    @JoinColumn(unique = true)
    private Rapport rapport;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPublication() {
        return idPublication;
    }

    public Publication idPublication(String idPublication) {
        this.idPublication = idPublication;
        return this;
    }

    public void setIdPublication(String idPublication) {
        this.idPublication = idPublication;
    }

    public String getTitre() {
        return titre;
    }

    public Publication titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDivers() {
        return divers;
    }

    public Publication divers(String divers) {
        this.divers = divers;
        return this;
    }

    public void setDivers(String divers) {
        this.divers = divers;
    }

    public String getLien() {
        return lien;
    }

    public Publication lien(String lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getDoi() {
        return doi;
    }

    public Publication doi(String doi) {
        this.doi = doi;
        return this;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Article getArticle() {
        return article;
    }

    public Publication article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Communication getCommunication() {
        return communication;
    }

    public Publication communication(Communication communication) {
        this.communication = communication;
        return this;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public Memoire getMemoire() {
        return memoire;
    }

    public Publication memoire(Memoire memoire) {
        this.memoire = memoire;
        return this;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public Publication ouvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        return this;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public PublicationGouvernementale getPublicationGouvernementale() {
        return publicationGouvernementale;
    }

    public Publication publicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
        return this;
    }

    public void setPublicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public Publication rapport(Rapport rapport) {
        this.rapport = rapport;
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
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
        Publication publication = (Publication) o;
        if (publication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Publication{" +
            "id=" + getId() +
            ", idPublication='" + getIdPublication() + "'" +
            ", titre='" + getTitre() + "'" +
            ", divers='" + getDivers() + "'" +
            ", lien='" + getLien() + "'" +
            ", doi='" + getDoi() + "'" +
            "}";
    }
}
