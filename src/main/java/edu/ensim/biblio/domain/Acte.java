package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Actes de conférence : identifiant obligatoire
 */
@ApiModel(description = "Actes de conférence : identifiant obligatoire")
@Entity
@Table(name = "acte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Acte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_acte", nullable = false)
    private String idActe;

    @Column(name = "titre")
    private String titre;

    @Column(name = "jhi_type")
    private String type;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Conference conference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdActe() {
        return idActe;
    }

    public Acte idActe(String idActe) {
        this.idActe = idActe;
        return this;
    }

    public void setIdActe(String idActe) {
        this.idActe = idActe;
    }

    public String getTitre() {
        return titre;
    }

    public Acte titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public Acte type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAnnee() {
        return annee;
    }

    public Acte annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNumeroEdition() {
        return numeroEdition;
    }

    public Acte numeroEdition(Integer numeroEdition) {
        this.numeroEdition = numeroEdition;
        return this;
    }

    public void setNumeroEdition(Integer numeroEdition) {
        this.numeroEdition = numeroEdition;
    }

    public Integer getVolume() {
        return volume;
    }

    public Acte volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getTraduction() {
        return traduction;
    }

    public Acte traduction(String traduction) {
        this.traduction = traduction;
        return this;
    }

    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }

    public String getLieu() {
        return lieu;
    }

    public Acte lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public Acte maisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
        return this;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public String getCollection() {
        return collection;
    }

    public Acte collection(String collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getHal() {
        return hal;
    }

    public Acte hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
    }

    public Conference getConference() {
        return conference;
    }

    public Acte conference(Conference conference) {
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
        Acte acte = (Acte) o;
        if (acte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acte{" +
            "id=" + getId() +
            ", idActe='" + getIdActe() + "'" +
            ", titre='" + getTitre() + "'" +
            ", type='" + getType() + "'" +
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
