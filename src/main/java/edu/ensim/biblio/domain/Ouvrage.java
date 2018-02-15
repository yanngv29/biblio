package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @Column(name = "titre_ouvrage", nullable = false)
    private String titreOuvrage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ouvrage", nullable = false)
    private TypeOuvrage typeOuvrage;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_ouvrage")
    private TypeParticipation participationOuvrage;

    @Column(name = "annee_ouvrage")
    private Integer anneeOuvrage;

    @Column(name = "numero_edition_ouvrage")
    private Integer numeroEditionOuvrage;

    @Column(name = "volume_ouvrage")
    private Integer volumeOuvrage;

    @Column(name = "traduction_ouvrage")
    private String traductionOuvrage;

    @Column(name = "lieu_ouvrage")
    private String lieuOuvrage;

    @Column(name = "maison_edition_ouvrage")
    private String maisonEditionOuvrage;

    @Column(name = "collection_ouvrage")
    private String collectionOuvrage;

    @Column(name = "langue_ouvrage")
    private String langueOuvrage;

    @Column(name = "lien_ouvrage")
    private String lienOuvrage;

    @Column(name = "doi_ouvrage")
    private String doiOuvrage;

    @Column(name = "hal_ouvrage")
    private String halOuvrage;

    @Column(name = "divers_ouvrage")
    private String diversOuvrage;

    /**
     * Ouvrage{chapitre} to Chapitre,
     */
    @ApiModelProperty(value = "Ouvrage{chapitre} to Chapitre,")
    @OneToMany(mappedBy = "ouvrage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Note> notations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreOuvrage() {
        return titreOuvrage;
    }

    public Ouvrage titreOuvrage(String titreOuvrage) {
        this.titreOuvrage = titreOuvrage;
        return this;
    }

    public void setTitreOuvrage(String titreOuvrage) {
        this.titreOuvrage = titreOuvrage;
    }

    public TypeOuvrage getTypeOuvrage() {
        return typeOuvrage;
    }

    public Ouvrage typeOuvrage(TypeOuvrage typeOuvrage) {
        this.typeOuvrage = typeOuvrage;
        return this;
    }

    public void setTypeOuvrage(TypeOuvrage typeOuvrage) {
        this.typeOuvrage = typeOuvrage;
    }

    public TypeParticipation getParticipationOuvrage() {
        return participationOuvrage;
    }

    public Ouvrage participationOuvrage(TypeParticipation participationOuvrage) {
        this.participationOuvrage = participationOuvrage;
        return this;
    }

    public void setParticipationOuvrage(TypeParticipation participationOuvrage) {
        this.participationOuvrage = participationOuvrage;
    }

    public Integer getAnneeOuvrage() {
        return anneeOuvrage;
    }

    public Ouvrage anneeOuvrage(Integer anneeOuvrage) {
        this.anneeOuvrage = anneeOuvrage;
        return this;
    }

    public void setAnneeOuvrage(Integer anneeOuvrage) {
        this.anneeOuvrage = anneeOuvrage;
    }

    public Integer getNumeroEditionOuvrage() {
        return numeroEditionOuvrage;
    }

    public Ouvrage numeroEditionOuvrage(Integer numeroEditionOuvrage) {
        this.numeroEditionOuvrage = numeroEditionOuvrage;
        return this;
    }

    public void setNumeroEditionOuvrage(Integer numeroEditionOuvrage) {
        this.numeroEditionOuvrage = numeroEditionOuvrage;
    }

    public Integer getVolumeOuvrage() {
        return volumeOuvrage;
    }

    public Ouvrage volumeOuvrage(Integer volumeOuvrage) {
        this.volumeOuvrage = volumeOuvrage;
        return this;
    }

    public void setVolumeOuvrage(Integer volumeOuvrage) {
        this.volumeOuvrage = volumeOuvrage;
    }

    public String getTraductionOuvrage() {
        return traductionOuvrage;
    }

    public Ouvrage traductionOuvrage(String traductionOuvrage) {
        this.traductionOuvrage = traductionOuvrage;
        return this;
    }

    public void setTraductionOuvrage(String traductionOuvrage) {
        this.traductionOuvrage = traductionOuvrage;
    }

    public String getLieuOuvrage() {
        return lieuOuvrage;
    }

    public Ouvrage lieuOuvrage(String lieuOuvrage) {
        this.lieuOuvrage = lieuOuvrage;
        return this;
    }

    public void setLieuOuvrage(String lieuOuvrage) {
        this.lieuOuvrage = lieuOuvrage;
    }

    public String getMaisonEditionOuvrage() {
        return maisonEditionOuvrage;
    }

    public Ouvrage maisonEditionOuvrage(String maisonEditionOuvrage) {
        this.maisonEditionOuvrage = maisonEditionOuvrage;
        return this;
    }

    public void setMaisonEditionOuvrage(String maisonEditionOuvrage) {
        this.maisonEditionOuvrage = maisonEditionOuvrage;
    }

    public String getCollectionOuvrage() {
        return collectionOuvrage;
    }

    public Ouvrage collectionOuvrage(String collectionOuvrage) {
        this.collectionOuvrage = collectionOuvrage;
        return this;
    }

    public void setCollectionOuvrage(String collectionOuvrage) {
        this.collectionOuvrage = collectionOuvrage;
    }

    public String getLangueOuvrage() {
        return langueOuvrage;
    }

    public Ouvrage langueOuvrage(String langueOuvrage) {
        this.langueOuvrage = langueOuvrage;
        return this;
    }

    public void setLangueOuvrage(String langueOuvrage) {
        this.langueOuvrage = langueOuvrage;
    }

    public String getLienOuvrage() {
        return lienOuvrage;
    }

    public Ouvrage lienOuvrage(String lienOuvrage) {
        this.lienOuvrage = lienOuvrage;
        return this;
    }

    public void setLienOuvrage(String lienOuvrage) {
        this.lienOuvrage = lienOuvrage;
    }

    public String getDoiOuvrage() {
        return doiOuvrage;
    }

    public Ouvrage doiOuvrage(String doiOuvrage) {
        this.doiOuvrage = doiOuvrage;
        return this;
    }

    public void setDoiOuvrage(String doiOuvrage) {
        this.doiOuvrage = doiOuvrage;
    }

    public String getHalOuvrage() {
        return halOuvrage;
    }

    public Ouvrage halOuvrage(String halOuvrage) {
        this.halOuvrage = halOuvrage;
        return this;
    }

    public void setHalOuvrage(String halOuvrage) {
        this.halOuvrage = halOuvrage;
    }

    public String getDiversOuvrage() {
        return diversOuvrage;
    }

    public Ouvrage diversOuvrage(String diversOuvrage) {
        this.diversOuvrage = diversOuvrage;
        return this;
    }

    public void setDiversOuvrage(String diversOuvrage) {
        this.diversOuvrage = diversOuvrage;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public Ouvrage notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public Ouvrage addNotation(Note note) {
        this.notations.add(note);
        note.setOuvrage(this);
        return this;
    }

    public Ouvrage removeNotation(Note note) {
        this.notations.remove(note);
        note.setOuvrage(null);
        return this;
    }

    public void setNotations(Set<Note> notes) {
        this.notations = notes;
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
            ", titreOuvrage='" + getTitreOuvrage() + "'" +
            ", typeOuvrage='" + getTypeOuvrage() + "'" +
            ", participationOuvrage='" + getParticipationOuvrage() + "'" +
            ", anneeOuvrage=" + getAnneeOuvrage() +
            ", numeroEditionOuvrage=" + getNumeroEditionOuvrage() +
            ", volumeOuvrage=" + getVolumeOuvrage() +
            ", traductionOuvrage='" + getTraductionOuvrage() + "'" +
            ", lieuOuvrage='" + getLieuOuvrage() + "'" +
            ", maisonEditionOuvrage='" + getMaisonEditionOuvrage() + "'" +
            ", collectionOuvrage='" + getCollectionOuvrage() + "'" +
            ", langueOuvrage='" + getLangueOuvrage() + "'" +
            ", lienOuvrage='" + getLienOuvrage() + "'" +
            ", doiOuvrage='" + getDoiOuvrage() + "'" +
            ", halOuvrage='" + getHalOuvrage() + "'" +
            ", diversOuvrage='" + getDiversOuvrage() + "'" +
            "}";
    }
}
