package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.Rang;

/**
 * Notation
 */
@ApiModel(description = "Notation")
@Entity
@Table(name = "notation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_notation", nullable = false)
    private String idNotation;

    @Column(name = "cnu")
    private String cnu;

    @Enumerated(EnumType.STRING)
    @Column(name = "rang")
    private Rang rang;

    @Column(name = "debut")
    private String debut;

    @Column(name = "fin")
    private String fin;

    @ManyToOne
    private Conference conference;

    @ManyToOne
    private Memoire memoire;

    @ManyToOne
    private Ouvrage ouvrage;

    @ManyToOne
    private PublicationGouvernementale publicationGouvernementale;

    @ManyToOne
    private Rapport rapport;

    @ManyToOne
    private Revue revue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNotation() {
        return idNotation;
    }

    public Notation idNotation(String idNotation) {
        this.idNotation = idNotation;
        return this;
    }

    public void setIdNotation(String idNotation) {
        this.idNotation = idNotation;
    }

    public String getCnu() {
        return cnu;
    }

    public Notation cnu(String cnu) {
        this.cnu = cnu;
        return this;
    }

    public void setCnu(String cnu) {
        this.cnu = cnu;
    }

    public Rang getRang() {
        return rang;
    }

    public Notation rang(Rang rang) {
        this.rang = rang;
        return this;
    }

    public void setRang(Rang rang) {
        this.rang = rang;
    }

    public String getDebut() {
        return debut;
    }

    public Notation debut(String debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public Notation fin(String fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public Conference getConference() {
        return conference;
    }

    public Notation conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Memoire getMemoire() {
        return memoire;
    }

    public Notation memoire(Memoire memoire) {
        this.memoire = memoire;
        return this;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public Notation ouvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        return this;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public PublicationGouvernementale getPublicationGouvernementale() {
        return publicationGouvernementale;
    }

    public Notation publicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
        return this;
    }

    public void setPublicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public Notation rapport(Rapport rapport) {
        this.rapport = rapport;
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
    }

    public Revue getRevue() {
        return revue;
    }

    public Notation revue(Revue revue) {
        this.revue = revue;
        return this;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
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
        Notation notation = (Notation) o;
        if (notation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Notation{" +
            "id=" + getId() +
            ", idNotation='" + getIdNotation() + "'" +
            ", cnu='" + getCnu() + "'" +
            ", rang='" + getRang() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
}
