package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.Participation;

/**
 * Contribution
 */
@ApiModel(description = "Contribution")
@Entity
@Table(name = "contribution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_chercheur", nullable = false)
    private String idChercheur;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Participation type;

    @Column(name = "rang")
    private String rang;

    @OneToOne
    @JoinColumn(unique = true)
    private Publication publication;

    @OneToOne
    @JoinColumn(unique = true)
    private Acte acte;

    @OneToOne
    @JoinColumn(unique = true)
    private Ouvrage ouvrage;

    @OneToOne
    @JoinColumn(unique = true)
    private Revue revue;

    @OneToOne
    @JoinColumn(unique = true)
    private Memoire memoire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdChercheur() {
        return idChercheur;
    }

    public Contribution idChercheur(String idChercheur) {
        this.idChercheur = idChercheur;
        return this;
    }

    public void setIdChercheur(String idChercheur) {
        this.idChercheur = idChercheur;
    }

    public Participation getType() {
        return type;
    }

    public Contribution type(Participation type) {
        this.type = type;
        return this;
    }

    public void setType(Participation type) {
        this.type = type;
    }

    public String getRang() {
        return rang;
    }

    public Contribution rang(String rang) {
        this.rang = rang;
        return this;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public Publication getPublication() {
        return publication;
    }

    public Contribution publication(Publication publication) {
        this.publication = publication;
        return this;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Acte getActe() {
        return acte;
    }

    public Contribution acte(Acte acte) {
        this.acte = acte;
        return this;
    }

    public void setActe(Acte acte) {
        this.acte = acte;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public Contribution ouvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        return this;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public Revue getRevue() {
        return revue;
    }

    public Contribution revue(Revue revue) {
        this.revue = revue;
        return this;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
    }

    public Memoire getMemoire() {
        return memoire;
    }

    public Contribution memoire(Memoire memoire) {
        this.memoire = memoire;
        return this;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
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
        Contribution contribution = (Contribution) o;
        if (contribution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contribution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contribution{" +
            "id=" + getId() +
            ", idChercheur='" + getIdChercheur() + "'" +
            ", type='" + getType() + "'" +
            ", rang='" + getRang() + "'" +
            "}";
    }
}
