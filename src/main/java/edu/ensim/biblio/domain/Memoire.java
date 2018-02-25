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

import edu.ensim.biblio.domain.enumeration.TypeMemoire;

/**
 * Mémoire : tous les critères obligatoires
 */
@ApiModel(description = "Mémoire : tous les critères obligatoires")
@Entity
@Table(name = "memoire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Memoire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_memoire", nullable = false)
    private String titreMemoire;

    @Column(name = "sous_titre_memoire")
    private String sousTitreMemoire;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_memoire", nullable = false)
    private TypeMemoire typeMemoire;

    @Column(name = "date_memoire")
    private Instant dateMemoire;

    @Column(name = "lieu_memoire")
    private String lieuMemoire;

    @Column(name = "langue_memoire")
    private String langueMemoire;

    @Column(name = "lien_memoire")
    private String lienMemoire;

    @Column(name = "doi_memoire")
    private String doiMemoire;

    @Column(name = "hal_memoire")
    private String halMemoire;

    @Column(name = "divers_memoire")
    private String diversMemoire;

    @OneToMany(mappedBy = "memoire")
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

    public String getTitreMemoire() {
        return titreMemoire;
    }

    public Memoire titreMemoire(String titreMemoire) {
        this.titreMemoire = titreMemoire;
        return this;
    }

    public void setTitreMemoire(String titreMemoire) {
        this.titreMemoire = titreMemoire;
    }

    public String getSousTitreMemoire() {
        return sousTitreMemoire;
    }

    public Memoire sousTitreMemoire(String sousTitreMemoire) {
        this.sousTitreMemoire = sousTitreMemoire;
        return this;
    }

    public void setSousTitreMemoire(String sousTitreMemoire) {
        this.sousTitreMemoire = sousTitreMemoire;
    }

    public TypeMemoire getTypeMemoire() {
        return typeMemoire;
    }

    public Memoire typeMemoire(TypeMemoire typeMemoire) {
        this.typeMemoire = typeMemoire;
        return this;
    }

    public void setTypeMemoire(TypeMemoire typeMemoire) {
        this.typeMemoire = typeMemoire;
    }

    public Instant getDateMemoire() {
        return dateMemoire;
    }

    public Memoire dateMemoire(Instant dateMemoire) {
        this.dateMemoire = dateMemoire;
        return this;
    }

    public void setDateMemoire(Instant dateMemoire) {
        this.dateMemoire = dateMemoire;
    }

    public String getLieuMemoire() {
        return lieuMemoire;
    }

    public Memoire lieuMemoire(String lieuMemoire) {
        this.lieuMemoire = lieuMemoire;
        return this;
    }

    public void setLieuMemoire(String lieuMemoire) {
        this.lieuMemoire = lieuMemoire;
    }

    public String getLangueMemoire() {
        return langueMemoire;
    }

    public Memoire langueMemoire(String langueMemoire) {
        this.langueMemoire = langueMemoire;
        return this;
    }

    public void setLangueMemoire(String langueMemoire) {
        this.langueMemoire = langueMemoire;
    }

    public String getLienMemoire() {
        return lienMemoire;
    }

    public Memoire lienMemoire(String lienMemoire) {
        this.lienMemoire = lienMemoire;
        return this;
    }

    public void setLienMemoire(String lienMemoire) {
        this.lienMemoire = lienMemoire;
    }

    public String getDoiMemoire() {
        return doiMemoire;
    }

    public Memoire doiMemoire(String doiMemoire) {
        this.doiMemoire = doiMemoire;
        return this;
    }

    public void setDoiMemoire(String doiMemoire) {
        this.doiMemoire = doiMemoire;
    }

    public String getHalMemoire() {
        return halMemoire;
    }

    public Memoire halMemoire(String halMemoire) {
        this.halMemoire = halMemoire;
        return this;
    }

    public void setHalMemoire(String halMemoire) {
        this.halMemoire = halMemoire;
    }

    public String getDiversMemoire() {
        return diversMemoire;
    }

    public Memoire diversMemoire(String diversMemoire) {
        this.diversMemoire = diversMemoire;
        return this;
    }

    public void setDiversMemoire(String diversMemoire) {
        this.diversMemoire = diversMemoire;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public Memoire notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public Memoire addNotation(Note note) {
        this.notations.add(note);
        note.setMemoire(this);
        return this;
    }

    public Memoire removeNotation(Note note) {
        this.notations.remove(note);
        note.setMemoire(null);
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
        Memoire memoire = (Memoire) o;
        if (memoire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memoire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Memoire{" +
            "id=" + getId() +
            ", titreMemoire='" + getTitreMemoire() + "'" +
            ", sousTitreMemoire='" + getSousTitreMemoire() + "'" +
            ", typeMemoire='" + getTypeMemoire() + "'" +
            ", dateMemoire='" + getDateMemoire() + "'" +
            ", lieuMemoire='" + getLieuMemoire() + "'" +
            ", langueMemoire='" + getLangueMemoire() + "'" +
            ", lienMemoire='" + getLienMemoire() + "'" +
            ", doiMemoire='" + getDoiMemoire() + "'" +
            ", halMemoire='" + getHalMemoire() + "'" +
            ", diversMemoire='" + getDiversMemoire() + "'" +
            "}";
    }
}
