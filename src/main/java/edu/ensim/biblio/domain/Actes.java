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
@Table(name = "actes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Actes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_acte", nullable = false)
    private String titreActe;

    @Column(name = "type_acte")
    private String typeActe;

    @Column(name = "annee_acte")
    private Integer anneeActe;

    @Column(name = "numero_edition_acte")
    private Integer numeroEditionActe;

    @Column(name = "volume_acte")
    private Integer volumeActe;

    @Column(name = "traduction_acte")
    private String traductionActe;

    @Column(name = "lieu_acte")
    private String lieuActe;

    @Column(name = "maison_edition_acte")
    private String maisonEditionActe;

    @Column(name = "collection_acte")
    private String collectionActe;

    @Column(name = "langue_acte")
    private String langueActe;

    @Column(name = "lien_acte")
    private String lienActe;

    @Column(name = "hal_acte")
    private String halActe;

    @Column(name = "divers_acte")
    private String diversActe;

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

    public String getTitreActe() {
        return titreActe;
    }

    public Actes titreActe(String titreActe) {
        this.titreActe = titreActe;
        return this;
    }

    public void setTitreActe(String titreActe) {
        this.titreActe = titreActe;
    }

    public String getTypeActe() {
        return typeActe;
    }

    public Actes typeActe(String typeActe) {
        this.typeActe = typeActe;
        return this;
    }

    public void setTypeActe(String typeActe) {
        this.typeActe = typeActe;
    }

    public Integer getAnneeActe() {
        return anneeActe;
    }

    public Actes anneeActe(Integer anneeActe) {
        this.anneeActe = anneeActe;
        return this;
    }

    public void setAnneeActe(Integer anneeActe) {
        this.anneeActe = anneeActe;
    }

    public Integer getNumeroEditionActe() {
        return numeroEditionActe;
    }

    public Actes numeroEditionActe(Integer numeroEditionActe) {
        this.numeroEditionActe = numeroEditionActe;
        return this;
    }

    public void setNumeroEditionActe(Integer numeroEditionActe) {
        this.numeroEditionActe = numeroEditionActe;
    }

    public Integer getVolumeActe() {
        return volumeActe;
    }

    public Actes volumeActe(Integer volumeActe) {
        this.volumeActe = volumeActe;
        return this;
    }

    public void setVolumeActe(Integer volumeActe) {
        this.volumeActe = volumeActe;
    }

    public String getTraductionActe() {
        return traductionActe;
    }

    public Actes traductionActe(String traductionActe) {
        this.traductionActe = traductionActe;
        return this;
    }

    public void setTraductionActe(String traductionActe) {
        this.traductionActe = traductionActe;
    }

    public String getLieuActe() {
        return lieuActe;
    }

    public Actes lieuActe(String lieuActe) {
        this.lieuActe = lieuActe;
        return this;
    }

    public void setLieuActe(String lieuActe) {
        this.lieuActe = lieuActe;
    }

    public String getMaisonEditionActe() {
        return maisonEditionActe;
    }

    public Actes maisonEditionActe(String maisonEditionActe) {
        this.maisonEditionActe = maisonEditionActe;
        return this;
    }

    public void setMaisonEditionActe(String maisonEditionActe) {
        this.maisonEditionActe = maisonEditionActe;
    }

    public String getCollectionActe() {
        return collectionActe;
    }

    public Actes collectionActe(String collectionActe) {
        this.collectionActe = collectionActe;
        return this;
    }

    public void setCollectionActe(String collectionActe) {
        this.collectionActe = collectionActe;
    }

    public String getLangueActe() {
        return langueActe;
    }

    public Actes langueActe(String langueActe) {
        this.langueActe = langueActe;
        return this;
    }

    public void setLangueActe(String langueActe) {
        this.langueActe = langueActe;
    }

    public String getLienActe() {
        return lienActe;
    }

    public Actes lienActe(String lienActe) {
        this.lienActe = lienActe;
        return this;
    }

    public void setLienActe(String lienActe) {
        this.lienActe = lienActe;
    }

    public String getHalActe() {
        return halActe;
    }

    public Actes halActe(String halActe) {
        this.halActe = halActe;
        return this;
    }

    public void setHalActe(String halActe) {
        this.halActe = halActe;
    }

    public String getDiversActe() {
        return diversActe;
    }

    public Actes diversActe(String diversActe) {
        this.diversActe = diversActe;
        return this;
    }

    public void setDiversActe(String diversActe) {
        this.diversActe = diversActe;
    }

    public Conference getConference() {
        return conference;
    }

    public Actes conference(Conference conference) {
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
        Actes actes = (Actes) o;
        if (actes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Actes{" +
            "id=" + getId() +
            ", titreActe='" + getTitreActe() + "'" +
            ", typeActe='" + getTypeActe() + "'" +
            ", anneeActe=" + getAnneeActe() +
            ", numeroEditionActe=" + getNumeroEditionActe() +
            ", volumeActe=" + getVolumeActe() +
            ", traductionActe='" + getTraductionActe() + "'" +
            ", lieuActe='" + getLieuActe() + "'" +
            ", maisonEditionActe='" + getMaisonEditionActe() + "'" +
            ", collectionActe='" + getCollectionActe() + "'" +
            ", langueActe='" + getLangueActe() + "'" +
            ", lienActe='" + getLienActe() + "'" +
            ", halActe='" + getHalActe() + "'" +
            ", diversActe='" + getDiversActe() + "'" +
            "}";
    }
}
