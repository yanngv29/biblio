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

import edu.ensim.biblio.domain.enumeration.Audience;

/**
 * Revue : identifiant, nom et année obligatoires
 */
@ApiModel(description = "Revue : identifiant, nom et année obligatoires")
@Entity
@Table(name = "revue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Revue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_revue", nullable = false)
    private String idRevue;

    @Column(name = "nom")
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience")
    private Audience audience;

    @Column(name = "comite_selection")
    private Boolean comiteSelection;

    @Column(name = "mois")
    private String mois;

    @Column(name = "annee")
    private String annee;

    @Column(name = "volume")
    private String volume;

    @Column(name = "numero_volume")
    private String numeroVolume;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "lien")
    private String lien;

    @Column(name = "divers")
    private String divers;

    @OneToMany(mappedBy = "revue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notation> notations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdRevue() {
        return idRevue;
    }

    public Revue idRevue(String idRevue) {
        this.idRevue = idRevue;
        return this;
    }

    public void setIdRevue(String idRevue) {
        this.idRevue = idRevue;
    }

    public String getNom() {
        return nom;
    }

    public Revue nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Audience getAudience() {
        return audience;
    }

    public Revue audience(Audience audience) {
        this.audience = audience;
        return this;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Boolean isComiteSelection() {
        return comiteSelection;
    }

    public Revue comiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
        return this;
    }

    public void setComiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
    }

    public String getMois() {
        return mois;
    }

    public Revue mois(String mois) {
        this.mois = mois;
        return this;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getAnnee() {
        return annee;
    }

    public Revue annee(String annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getVolume() {
        return volume;
    }

    public Revue volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNumeroVolume() {
        return numeroVolume;
    }

    public Revue numeroVolume(String numeroVolume) {
        this.numeroVolume = numeroVolume;
        return this;
    }

    public void setNumeroVolume(String numeroVolume) {
        this.numeroVolume = numeroVolume;
    }

    public String getLieu() {
        return lieu;
    }

    public Revue lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getLien() {
        return lien;
    }

    public Revue lien(String lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getDivers() {
        return divers;
    }

    public Revue divers(String divers) {
        this.divers = divers;
        return this;
    }

    public void setDivers(String divers) {
        this.divers = divers;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public Revue notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public Revue addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setRevue(this);
        return this;
    }

    public Revue removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setRevue(null);
        return this;
    }

    public void setNotations(Set<Notation> notations) {
        this.notations = notations;
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
        Revue revue = (Revue) o;
        if (revue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), revue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Revue{" +
            "id=" + getId() +
            ", idRevue='" + getIdRevue() + "'" +
            ", nom='" + getNom() + "'" +
            ", audience='" + getAudience() + "'" +
            ", comiteSelection='" + isComiteSelection() + "'" +
            ", mois='" + getMois() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", volume='" + getVolume() + "'" +
            ", numeroVolume='" + getNumeroVolume() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", lien='" + getLien() + "'" +
            ", divers='" + getDivers() + "'" +
            "}";
    }
}
