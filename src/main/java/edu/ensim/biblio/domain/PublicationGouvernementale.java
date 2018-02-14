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
    @Column(name = "id_pg", nullable = false)
    private String idPG;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "numero_edition")
    private String numeroEdition;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "maison_edition")
    private String maisonEdition;

    @OneToMany(mappedBy = "publicationGouvernementale")
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

    public String getIdPG() {
        return idPG;
    }

    public PublicationGouvernementale idPG(String idPG) {
        this.idPG = idPG;
        return this;
    }

    public void setIdPG(String idPG) {
        this.idPG = idPG;
    }

    public Instant getDate() {
        return date;
    }

    public PublicationGouvernementale date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getNumeroEdition() {
        return numeroEdition;
    }

    public PublicationGouvernementale numeroEdition(String numeroEdition) {
        this.numeroEdition = numeroEdition;
        return this;
    }

    public void setNumeroEdition(String numeroEdition) {
        this.numeroEdition = numeroEdition;
    }

    public String getLieu() {
        return lieu;
    }

    public PublicationGouvernementale lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public PublicationGouvernementale maisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
        return this;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public PublicationGouvernementale notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public PublicationGouvernementale addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setPublicationGouvernementale(this);
        return this;
    }

    public PublicationGouvernementale removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setPublicationGouvernementale(null);
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
            ", idPG='" + getIdPG() + "'" +
            ", date='" + getDate() + "'" +
            ", numeroEdition='" + getNumeroEdition() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", maisonEdition='" + getMaisonEdition() + "'" +
            "}";
    }
}
