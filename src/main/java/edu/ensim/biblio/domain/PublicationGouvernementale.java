package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Publication gouvernementale : identifiant et CNU obligatoires
 */
@ApiModel(description = "Publication gouvernementale : identifiant et CNU obligatoires")
@Entity
@Table(name = "publication_gouvernementale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PublicationGouvernementale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_pg", nullable = false)
    private String titrePG;

    @Column(name = "date_pg")
    private Instant datePG;

    @Column(name = "numero_edition_pg")
    private String numeroEditionPG;

    @Column(name = "lieu_pg")
    private String lieuPG;

    @Column(name = "maison_edition_pg")
    private String maisonEditionPG;

    @Column(name = "langue_pg")
    private String languePG;

    @Column(name = "lien_pg")
    private String lienPG;

    @Column(name = "doi_pg")
    private String doiPG;

    @Column(name = "hal_pg")
    private String halPG;

    @Column(name = "divers_ouvrage_pg")
    private String diversOuvragePG;

    @OneToMany(mappedBy = "publicationGouvernementale")
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

    public String getTitrePG() {
        return titrePG;
    }

    public PublicationGouvernementale titrePG(String titrePG) {
        this.titrePG = titrePG;
        return this;
    }

    public void setTitrePG(String titrePG) {
        this.titrePG = titrePG;
    }

    public Instant getDatePG() {
        return datePG;
    }

    public PublicationGouvernementale datePG(Instant datePG) {
        this.datePG = datePG;
        return this;
    }

    public void setDatePG(Instant datePG) {
        this.datePG = datePG;
    }

    public String getNumeroEditionPG() {
        return numeroEditionPG;
    }

    public PublicationGouvernementale numeroEditionPG(String numeroEditionPG) {
        this.numeroEditionPG = numeroEditionPG;
        return this;
    }

    public void setNumeroEditionPG(String numeroEditionPG) {
        this.numeroEditionPG = numeroEditionPG;
    }

    public String getLieuPG() {
        return lieuPG;
    }

    public PublicationGouvernementale lieuPG(String lieuPG) {
        this.lieuPG = lieuPG;
        return this;
    }

    public void setLieuPG(String lieuPG) {
        this.lieuPG = lieuPG;
    }

    public String getMaisonEditionPG() {
        return maisonEditionPG;
    }

    public PublicationGouvernementale maisonEditionPG(String maisonEditionPG) {
        this.maisonEditionPG = maisonEditionPG;
        return this;
    }

    public void setMaisonEditionPG(String maisonEditionPG) {
        this.maisonEditionPG = maisonEditionPG;
    }

    public String getLanguePG() {
        return languePG;
    }

    public PublicationGouvernementale languePG(String languePG) {
        this.languePG = languePG;
        return this;
    }

    public void setLanguePG(String languePG) {
        this.languePG = languePG;
    }

    public String getLienPG() {
        return lienPG;
    }

    public PublicationGouvernementale lienPG(String lienPG) {
        this.lienPG = lienPG;
        return this;
    }

    public void setLienPG(String lienPG) {
        this.lienPG = lienPG;
    }

    public String getDoiPG() {
        return doiPG;
    }

    public PublicationGouvernementale doiPG(String doiPG) {
        this.doiPG = doiPG;
        return this;
    }

    public void setDoiPG(String doiPG) {
        this.doiPG = doiPG;
    }

    public String getHalPG() {
        return halPG;
    }

    public PublicationGouvernementale halPG(String halPG) {
        this.halPG = halPG;
        return this;
    }

    public void setHalPG(String halPG) {
        this.halPG = halPG;
    }

    public String getDiversOuvragePG() {
        return diversOuvragePG;
    }

    public PublicationGouvernementale diversOuvragePG(String diversOuvragePG) {
        this.diversOuvragePG = diversOuvragePG;
        return this;
    }

    public void setDiversOuvragePG(String diversOuvragePG) {
        this.diversOuvragePG = diversOuvragePG;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public PublicationGouvernementale notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public PublicationGouvernementale addNotation(Note note) {
        this.notations.add(note);
        note.setPublicationGouvernementale(this);
        return this;
    }

    public PublicationGouvernementale removeNotation(Note note) {
        this.notations.remove(note);
        note.setPublicationGouvernementale(null);
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
        PublicationGouvernementale publicationGouvernementale = (PublicationGouvernementale) o;
        if (publicationGouvernementale.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicationGouvernementale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicationGouvernementale{" +
            "id=" + getId() +
            ", titrePG='" + getTitrePG() + "'" +
            ", datePG='" + getDatePG() + "'" +
            ", numeroEditionPG='" + getNumeroEditionPG() + "'" +
            ", lieuPG='" + getLieuPG() + "'" +
            ", maisonEditionPG='" + getMaisonEditionPG() + "'" +
            ", languePG='" + getLanguePG() + "'" +
            ", lienPG='" + getLienPG() + "'" +
            ", doiPG='" + getDoiPG() + "'" +
            ", halPG='" + getHalPG() + "'" +
            ", diversOuvragePG='" + getDiversOuvragePG() + "'" +
            "}";
    }
}
