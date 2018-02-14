package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Numéro d'une revue
 */
@ApiModel(description = "Numéro d'une revue")
@Entity
@Table(name = "numero_revue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NumeroRevue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_numero_revue", nullable = false)
    private String idNumeroRevue;

    @Column(name = "mois")
    private String mois;

    @Column(name = "annee")
    private String annee;

    @Column(name = "volume")
    private String volume;

    @Column(name = "numero_volume")
    private String numeroVolume;

    @Column(name = "lien")
    private String lien;

    @Column(name = "doi")
    private String doi;

    @ManyToOne
    private Revue revue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumeroRevue() {
        return idNumeroRevue;
    }

    public NumeroRevue idNumeroRevue(String idNumeroRevue) {
        this.idNumeroRevue = idNumeroRevue;
        return this;
    }

    public void setIdNumeroRevue(String idNumeroRevue) {
        this.idNumeroRevue = idNumeroRevue;
    }

    public String getMois() {
        return mois;
    }

    public NumeroRevue mois(String mois) {
        this.mois = mois;
        return this;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getAnnee() {
        return annee;
    }

    public NumeroRevue annee(String annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getVolume() {
        return volume;
    }

    public NumeroRevue volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNumeroVolume() {
        return numeroVolume;
    }

    public NumeroRevue numeroVolume(String numeroVolume) {
        this.numeroVolume = numeroVolume;
        return this;
    }

    public void setNumeroVolume(String numeroVolume) {
        this.numeroVolume = numeroVolume;
    }

    public String getLien() {
        return lien;
    }

    public NumeroRevue lien(String lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getDoi() {
        return doi;
    }

    public NumeroRevue doi(String doi) {
        this.doi = doi;
        return this;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Revue getRevue() {
        return revue;
    }

    public NumeroRevue revue(Revue revue) {
        this.revue = revue;
        return this;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
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
        NumeroRevue numeroRevue = (NumeroRevue) o;
        if (numeroRevue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numeroRevue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumeroRevue{" +
            "id=" + getId() +
            ", idNumeroRevue='" + getIdNumeroRevue() + "'" +
            ", mois='" + getMois() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", volume='" + getVolume() + "'" +
            ", numeroVolume='" + getNumeroVolume() + "'" +
            ", lien='" + getLien() + "'" +
            ", doi='" + getDoi() + "'" +
            "}";
    }
}
