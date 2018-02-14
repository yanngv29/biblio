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
    @Column(name = "id_memoire", nullable = false)
    private String idMemoire;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeRapport type;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "maison_edition")
    private String maisonEdition;

    @OneToMany(mappedBy = "rapport")
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

    public String getIdMemoire() {
        return idMemoire;
    }

    public Rapport idMemoire(String idMemoire) {
        this.idMemoire = idMemoire;
        return this;
    }

    public void setIdMemoire(String idMemoire) {
        this.idMemoire = idMemoire;
    }

    public TypeRapport getType() {
        return type;
    }

    public Rapport type(TypeRapport type) {
        this.type = type;
        return this;
    }

    public void setType(TypeRapport type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public Rapport date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public Rapport lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public Rapport maisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
        return this;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public Rapport notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public Rapport addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setRapport(this);
        return this;
    }

    public Rapport removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setRapport(null);
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
            ", idMemoire='" + getIdMemoire() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", maisonEdition='" + getMaisonEdition() + "'" +
            "}";
    }
}
