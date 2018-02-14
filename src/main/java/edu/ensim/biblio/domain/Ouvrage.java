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

import edu.ensim.biblio.domain.enumeration.TypeOuvrage;

import edu.ensim.biblio.domain.enumeration.TypeParticipation;

/**
 * Acte de conférence : identifiant obligatoire
 */
@ApiModel(description = "Acte de conférence : identifiant obligatoire")
@Entity
@Table(name = "ouvrage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ouvrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_ouvrage", nullable = false)
    private String idOuvrage;

    @Column(name = "titre")
    private String titre;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeOuvrage type;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation")
    private TypeParticipation participation;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "numero_edition")
    private Integer numeroEdition;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "traduction")
    private String traduction;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "maison_edition")
    private String maisonEdition;

    @Column(name = "collection")
    private String collection;

    @Column(name = "hal")
    private String hal;

    @OneToMany(mappedBy = "ouvrage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notation> notations = new HashSet<>();

    @OneToMany(mappedBy = "ouvrage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chapitre> chapitres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdOuvrage() {
        return idOuvrage;
    }

    public Ouvrage idOuvrage(String idOuvrage) {
        this.idOuvrage = idOuvrage;
        return this;
    }

    public void setIdOuvrage(String idOuvrage) {
        this.idOuvrage = idOuvrage;
    }

    public String getTitre() {
        return titre;
    }

    public Ouvrage titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public TypeOuvrage getType() {
        return type;
    }

    public Ouvrage type(TypeOuvrage type) {
        this.type = type;
        return this;
    }

    public void setType(TypeOuvrage type) {
        this.type = type;
    }

    public TypeParticipation getParticipation() {
        return participation;
    }

    public Ouvrage participation(TypeParticipation participation) {
        this.participation = participation;
        return this;
    }

    public void setParticipation(TypeParticipation participation) {
        this.participation = participation;
    }

    public Integer getAnnee() {
        return annee;
    }

    public Ouvrage annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNumeroEdition() {
        return numeroEdition;
    }

    public Ouvrage numeroEdition(Integer numeroEdition) {
        this.numeroEdition = numeroEdition;
        return this;
    }

    public void setNumeroEdition(Integer numeroEdition) {
        this.numeroEdition = numeroEdition;
    }

    public Integer getVolume() {
        return volume;
    }

    public Ouvrage volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getTraduction() {
        return traduction;
    }

    public Ouvrage traduction(String traduction) {
        this.traduction = traduction;
        return this;
    }

    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }

    public String getLieu() {
        return lieu;
    }

    public Ouvrage lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public Ouvrage maisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
        return this;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public String getCollection() {
        return collection;
    }

    public Ouvrage collection(String collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getHal() {
        return hal;
    }

    public Ouvrage hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public Ouvrage notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public Ouvrage addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setOuvrage(this);
        return this;
    }

    public Ouvrage removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setOuvrage(null);
        return this;
    }

    public void setNotations(Set<Notation> notations) {
        this.notations = notations;
    }

    public Set<Chapitre> getChapitres() {
        return chapitres;
    }

    public Ouvrage chapitres(Set<Chapitre> chapitres) {
        this.chapitres = chapitres;
        return this;
    }

    public Ouvrage addChapitre(Chapitre chapitre) {
        this.chapitres.add(chapitre);
        chapitre.setOuvrage(this);
        return this;
    }

    public Ouvrage removeChapitre(Chapitre chapitre) {
        this.chapitres.remove(chapitre);
        chapitre.setOuvrage(null);
        return this;
    }

    public void setChapitres(Set<Chapitre> chapitres) {
        this.chapitres = chapitres;
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
        Ouvrage ouvrage = (Ouvrage) o;
        if (ouvrage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ouvrage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ouvrage{" +
            "id=" + getId() +
            ", idOuvrage='" + getIdOuvrage() + "'" +
            ", titre='" + getTitre() + "'" +
            ", type='" + getType() + "'" +
            ", participation='" + getParticipation() + "'" +
            ", annee=" + getAnnee() +
            ", numeroEdition=" + getNumeroEdition() +
            ", volume=" + getVolume() +
            ", traduction='" + getTraduction() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", maisonEdition='" + getMaisonEdition() + "'" +
            ", collection='" + getCollection() + "'" +
            ", hal='" + getHal() + "'" +
            "}";
    }
}
