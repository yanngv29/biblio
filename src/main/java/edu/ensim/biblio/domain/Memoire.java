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
    @Column(name = "id_memoire", nullable = false)
    private String idMemoire;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeMemoire type;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "hal")
    private String hal;

    @OneToMany(mappedBy = "memoire")
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

    public Memoire idMemoire(String idMemoire) {
        this.idMemoire = idMemoire;
        return this;
    }

    public void setIdMemoire(String idMemoire) {
        this.idMemoire = idMemoire;
    }

    public TypeMemoire getType() {
        return type;
    }

    public Memoire type(TypeMemoire type) {
        this.type = type;
        return this;
    }

    public void setType(TypeMemoire type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public Memoire date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public Memoire lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getHal() {
        return hal;
    }

    public Memoire hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public Memoire notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public Memoire addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setMemoire(this);
        return this;
    }

    public Memoire removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setMemoire(null);
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
            ", idMemoire='" + getIdMemoire() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", hal='" + getHal() + "'" +
            "}";
    }
}
