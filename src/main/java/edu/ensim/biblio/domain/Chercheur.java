package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.TypeChercheur;

/**
 * Contribution
 */
@ApiModel(description = "Contribution")
@Entity
@Table(name = "chercheur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chercheur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_chercheur", nullable = false)
    private String nomChercheur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_chercheur")
    private TypeChercheur typeChercheur;

    @Column(name = "rang_chercheur")
    private String rangChercheur;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_actes",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="actes_id", referencedColumnName="id"))
    private Set<Actes> actes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_article",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="articles_id", referencedColumnName="id"))
    private Set<Article> articles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_chapitre",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="chapitres_id", referencedColumnName="id"))
    private Set<Chapitre> chapitres = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_communication",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="communications_id", referencedColumnName="id"))
    private Set<Communication> communications = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_ouvrage",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ouvrages_id", referencedColumnName="id"))
    private Set<Ouvrage> ouvrages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_publication_gouvernementale",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="publication_gouvernementales_id", referencedColumnName="id"))
    private Set<PublicationGouvernementale> publicationGouvernementales = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_revue",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="revues_id", referencedColumnName="id"))
    private Set<NumeroRevue> revues = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_memoire",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="memoires_id", referencedColumnName="id"))
    private Set<Memoire> memoires = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chercheur_rapport",
               joinColumns = @JoinColumn(name="chercheurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="rapports_id", referencedColumnName="id"))
    private Set<Rapport> rapports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomChercheur() {
        return nomChercheur;
    }

    public Chercheur nomChercheur(String nomChercheur) {
        this.nomChercheur = nomChercheur;
        return this;
    }

    public void setNomChercheur(String nomChercheur) {
        this.nomChercheur = nomChercheur;
    }

    public TypeChercheur getTypeChercheur() {
        return typeChercheur;
    }

    public Chercheur typeChercheur(TypeChercheur typeChercheur) {
        this.typeChercheur = typeChercheur;
        return this;
    }

    public void setTypeChercheur(TypeChercheur typeChercheur) {
        this.typeChercheur = typeChercheur;
    }

    public String getRangChercheur() {
        return rangChercheur;
    }

    public Chercheur rangChercheur(String rangChercheur) {
        this.rangChercheur = rangChercheur;
        return this;
    }

    public void setRangChercheur(String rangChercheur) {
        this.rangChercheur = rangChercheur;
    }

    public Set<Actes> getActes() {
        return actes;
    }

    public Chercheur actes(Set<Actes> actes) {
        this.actes = actes;
        return this;
    }

    public Chercheur addActes(Actes actes) {
        this.actes.add(actes);
        return this;
    }

    public Chercheur removeActes(Actes actes) {
        this.actes.remove(actes);
        return this;
    }

    public void setActes(Set<Actes> actes) {
        this.actes = actes;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Chercheur articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Chercheur addArticle(Article article) {
        this.articles.add(article);
        article.getAuteurs().add(this);
        return this;
    }

    public Chercheur removeArticle(Article article) {
        this.articles.remove(article);
        article.getAuteurs().remove(this);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Chapitre> getChapitres() {
        return chapitres;
    }

    public Chercheur chapitres(Set<Chapitre> chapitres) {
        this.chapitres = chapitres;
        return this;
    }

    public Chercheur addChapitre(Chapitre chapitre) {
        this.chapitres.add(chapitre);
        return this;
    }

    public Chercheur removeChapitre(Chapitre chapitre) {
        this.chapitres.remove(chapitre);
        return this;
    }

    public void setChapitres(Set<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public Set<Communication> getCommunications() {
        return communications;
    }

    public Chercheur communications(Set<Communication> communications) {
        this.communications = communications;
        return this;
    }

    public Chercheur addCommunication(Communication communication) {
        this.communications.add(communication);
        return this;
    }

    public Chercheur removeCommunication(Communication communication) {
        this.communications.remove(communication);
        return this;
    }

    public void setCommunications(Set<Communication> communications) {
        this.communications = communications;
    }

    public Set<Ouvrage> getOuvrages() {
        return ouvrages;
    }

    public Chercheur ouvrages(Set<Ouvrage> ouvrages) {
        this.ouvrages = ouvrages;
        return this;
    }

    public Chercheur addOuvrage(Ouvrage ouvrage) {
        this.ouvrages.add(ouvrage);
        return this;
    }

    public Chercheur removeOuvrage(Ouvrage ouvrage) {
        this.ouvrages.remove(ouvrage);
        return this;
    }

    public void setOuvrages(Set<Ouvrage> ouvrages) {
        this.ouvrages = ouvrages;
    }

    public Set<PublicationGouvernementale> getPublicationGouvernementales() {
        return publicationGouvernementales;
    }

    public Chercheur publicationGouvernementales(Set<PublicationGouvernementale> publicationGouvernementales) {
        this.publicationGouvernementales = publicationGouvernementales;
        return this;
    }

    public Chercheur addPublicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementales.add(publicationGouvernementale);
        return this;
    }

    public Chercheur removePublicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementales.remove(publicationGouvernementale);
        return this;
    }

    public void setPublicationGouvernementales(Set<PublicationGouvernementale> publicationGouvernementales) {
        this.publicationGouvernementales = publicationGouvernementales;
    }

    public Set<NumeroRevue> getRevues() {
        return revues;
    }

    public Chercheur revues(Set<NumeroRevue> numeroRevues) {
        this.revues = numeroRevues;
        return this;
    }

    public Chercheur addRevue(NumeroRevue numeroRevue) {
        this.revues.add(numeroRevue);
        return this;
    }

    public Chercheur removeRevue(NumeroRevue numeroRevue) {
        this.revues.remove(numeroRevue);
        return this;
    }

    public void setRevues(Set<NumeroRevue> numeroRevues) {
        this.revues = numeroRevues;
    }

    public Set<Memoire> getMemoires() {
        return memoires;
    }

    public Chercheur memoires(Set<Memoire> memoires) {
        this.memoires = memoires;
        return this;
    }

    public Chercheur addMemoire(Memoire memoire) {
        this.memoires.add(memoire);
        return this;
    }

    public Chercheur removeMemoire(Memoire memoire) {
        this.memoires.remove(memoire);
        return this;
    }

    public void setMemoires(Set<Memoire> memoires) {
        this.memoires = memoires;
    }

    public Set<Rapport> getRapports() {
        return rapports;
    }

    public Chercheur rapports(Set<Rapport> rapports) {
        this.rapports = rapports;
        return this;
    }

    public Chercheur addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        return this;
    }

    public Chercheur removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        this.rapports = rapports;
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
        Chercheur chercheur = (Chercheur) o;
        if (chercheur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chercheur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chercheur{" +
            "id=" + getId() +
            ", nomChercheur='" + getNomChercheur() + "'" +
            ", typeChercheur='" + getTypeChercheur() + "'" +
            ", rangChercheur='" + getRangChercheur() + "'" +
            "}";
    }
}
