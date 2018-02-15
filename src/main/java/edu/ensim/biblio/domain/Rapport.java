package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.TypeRapport;

/**
 * A Rapport.
 */
@Entity
@Table(name = "rapport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_rapport", nullable = false)
    private String titreRapport;

    @Column(name = "sous_titre_rapport")
    private String sousTitreRapport;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_rapport")
    private TypeRapport typeRapport;

    @Column(name = "date_rapport")
    private Instant dateRapport;

    @Column(name = "lieu_rapport")
    private String lieuRapport;

    @Column(name = "maison_edition_rapport")
    private String maisonEditionRapport;

    @Column(name = "langue_rapport")
    private String langueRapport;

    @Column(name = "lien_rapport")
    private String lienRapport;

    @Column(name = "doi_rapport")
    private String doiRapport;

    @Column(name = "hal_rapport")
    private String halRapport;

    @Column(name = "divers_ouvrage_rapport")
    private String diversOuvrageRapport;

    @OneToMany(mappedBy = "rapport")
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

    public String getTitreRapport() {
        return titreRapport;
    }

    public Rapport titreRapport(String titreRapport) {
        this.titreRapport = titreRapport;
        return this;
    }

    public void setTitreRapport(String titreRapport) {
        this.titreRapport = titreRapport;
    }

    public String getSousTitreRapport() {
        return sousTitreRapport;
    }

    public Rapport sousTitreRapport(String sousTitreRapport) {
        this.sousTitreRapport = sousTitreRapport;
        return this;
    }

    public void setSousTitreRapport(String sousTitreRapport) {
        this.sousTitreRapport = sousTitreRapport;
    }

    public TypeRapport getTypeRapport() {
        return typeRapport;
    }

    public Rapport typeRapport(TypeRapport typeRapport) {
        this.typeRapport = typeRapport;
        return this;
    }

    public void setTypeRapport(TypeRapport typeRapport) {
        this.typeRapport = typeRapport;
    }

    public Instant getDateRapport() {
        return dateRapport;
    }

    public Rapport dateRapport(Instant dateRapport) {
        this.dateRapport = dateRapport;
        return this;
    }

    public void setDateRapport(Instant dateRapport) {
        this.dateRapport = dateRapport;
    }

    public String getLieuRapport() {
        return lieuRapport;
    }

    public Rapport lieuRapport(String lieuRapport) {
        this.lieuRapport = lieuRapport;
        return this;
    }

    public void setLieuRapport(String lieuRapport) {
        this.lieuRapport = lieuRapport;
    }

    public String getMaisonEditionRapport() {
        return maisonEditionRapport;
    }

    public Rapport maisonEditionRapport(String maisonEditionRapport) {
        this.maisonEditionRapport = maisonEditionRapport;
        return this;
    }

    public void setMaisonEditionRapport(String maisonEditionRapport) {
        this.maisonEditionRapport = maisonEditionRapport;
    }

    public String getLangueRapport() {
        return langueRapport;
    }

    public Rapport langueRapport(String langueRapport) {
        this.langueRapport = langueRapport;
        return this;
    }

    public void setLangueRapport(String langueRapport) {
        this.langueRapport = langueRapport;
    }

    public String getLienRapport() {
        return lienRapport;
    }

    public Rapport lienRapport(String lienRapport) {
        this.lienRapport = lienRapport;
        return this;
    }

    public void setLienRapport(String lienRapport) {
        this.lienRapport = lienRapport;
    }

    public String getDoiRapport() {
        return doiRapport;
    }

    public Rapport doiRapport(String doiRapport) {
        this.doiRapport = doiRapport;
        return this;
    }

    public void setDoiRapport(String doiRapport) {
        this.doiRapport = doiRapport;
    }

    public String getHalRapport() {
        return halRapport;
    }

    public Rapport halRapport(String halRapport) {
        this.halRapport = halRapport;
        return this;
    }

    public void setHalRapport(String halRapport) {
        this.halRapport = halRapport;
    }

    public String getDiversOuvrageRapport() {
        return diversOuvrageRapport;
    }

    public Rapport diversOuvrageRapport(String diversOuvrageRapport) {
        this.diversOuvrageRapport = diversOuvrageRapport;
        return this;
    }

    public void setDiversOuvrageRapport(String diversOuvrageRapport) {
        this.diversOuvrageRapport = diversOuvrageRapport;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public Rapport notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public Rapport addNotation(Note note) {
        this.notations.add(note);
        note.setRapport(this);
        return this;
    }

    public Rapport removeNotation(Note note) {
        this.notations.remove(note);
        note.setRapport(null);
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
        Rapport rapport = (Rapport) o;
        if (rapport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rapport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rapport{" +
            "id=" + getId() +
            ", titreRapport='" + getTitreRapport() + "'" +
            ", sousTitreRapport='" + getSousTitreRapport() + "'" +
            ", typeRapport='" + getTypeRapport() + "'" +
            ", dateRapport='" + getDateRapport() + "'" +
            ", lieuRapport='" + getLieuRapport() + "'" +
            ", maisonEditionRapport='" + getMaisonEditionRapport() + "'" +
            ", langueRapport='" + getLangueRapport() + "'" +
            ", lienRapport='" + getLienRapport() + "'" +
            ", doiRapport='" + getDoiRapport() + "'" +
            ", halRapport='" + getHalRapport() + "'" +
            ", diversOuvrageRapport='" + getDiversOuvrageRapport() + "'" +
            "}";
    }
}
