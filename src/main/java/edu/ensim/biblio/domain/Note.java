package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.RangNote;

/**
 * Notation
 */
@ApiModel(description = "Notation")
@Entity
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cnu_note")
    private String cnuNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "rang_note")
    private RangNote rangNote;

    @Column(name = "debut_note")
    private String debutNote;

    @Column(name = "fin_note")
    private String finNote;

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

    public String getCnuNote() {
        return cnuNote;
    }

    public Note cnuNote(String cnuNote) {
        this.cnuNote = cnuNote;
        return this;
    }

    public void setCnuNote(String cnuNote) {
        this.cnuNote = cnuNote;
    }

    public RangNote getRangNote() {
        return rangNote;
    }

    public Note rangNote(RangNote rangNote) {
        this.rangNote = rangNote;
        return this;
    }

    public void setRangNote(RangNote rangNote) {
        this.rangNote = rangNote;
    }

    public String getDebutNote() {
        return debutNote;
    }

    public Note debutNote(String debutNote) {
        this.debutNote = debutNote;
        return this;
    }

    public void setDebutNote(String debutNote) {
        this.debutNote = debutNote;
    }

    public String getFinNote() {
        return finNote;
    }

    public Note finNote(String finNote) {
        this.finNote = finNote;
        return this;
    }

    public void setFinNote(String finNote) {
        this.finNote = finNote;
    }

    public Conference getConference() {
        return conference;
    }

    public Note conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Memoire getMemoire() {
        return memoire;
    }

    public Note memoire(Memoire memoire) {
        this.memoire = memoire;
        return this;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public Note ouvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        return this;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public PublicationGouvernementale getPublicationGouvernementale() {
        return publicationGouvernementale;
    }

    public Note publicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
        return this;
    }

    public void setPublicationGouvernementale(PublicationGouvernementale publicationGouvernementale) {
        this.publicationGouvernementale = publicationGouvernementale;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public Note rapport(Rapport rapport) {
        this.rapport = rapport;
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
    }

    public Revue getRevue() {
        return revue;
    }

    public Note revue(Revue revue) {
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
        Note note = (Note) o;
        if (note.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), note.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", cnuNote='" + getCnuNote() + "'" +
            ", rangNote='" + getRangNote() + "'" +
            ", debutNote='" + getDebutNote() + "'" +
            ", finNote='" + getFinNote() + "'" +
            "}";
    }
}
