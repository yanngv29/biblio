package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.Audience;

/**
 * Revue : identifiant, nom et année obligatoires
 */
@ApiModel(description = "Revue : identifiant, nom et année obligatoires")
@Entity
@Table(name = "revue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Revue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_revue", nullable = false)
    private String nomRevue;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience")
    private Audience audience;

    @Column(name = "comite_selection")
    private Boolean comiteSelection;

    @Column(name = "langue_revue")
    private String langueRevue;

    @Column(name = "lieu_revue")
    private String lieuRevue;

    @Column(name = "lien_revue")
    private String lienRevue;

    @Column(name = "divers_revue")
    private String diversRevue;

    @OneToMany(mappedBy = "revue")
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

    public String getNomRevue() {
        return nomRevue;
    }

    public Revue nomRevue(String nomRevue) {
        this.nomRevue = nomRevue;
        return this;
    }

    public void setNomRevue(String nomRevue) {
        this.nomRevue = nomRevue;
    }

    public Audience getAudience() {
        return audience;
    }

    public Revue audience(Audience audience) {
        this.audience = audience;
        return this;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Boolean isComiteSelection() {
        return comiteSelection;
    }

    public Revue comiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
        return this;
    }

    public void setComiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
    }

    public String getLangueRevue() {
        return langueRevue;
    }

    public Revue langueRevue(String langueRevue) {
        this.langueRevue = langueRevue;
        return this;
    }

    public void setLangueRevue(String langueRevue) {
        this.langueRevue = langueRevue;
    }

    public String getLieuRevue() {
        return lieuRevue;
    }

    public Revue lieuRevue(String lieuRevue) {
        this.lieuRevue = lieuRevue;
        return this;
    }

    public void setLieuRevue(String lieuRevue) {
        this.lieuRevue = lieuRevue;
    }

    public String getLienRevue() {
        return lienRevue;
    }

    public Revue lienRevue(String lienRevue) {
        this.lienRevue = lienRevue;
        return this;
    }

    public void setLienRevue(String lienRevue) {
        this.lienRevue = lienRevue;
    }

    public String getDiversRevue() {
        return diversRevue;
    }

    public Revue diversRevue(String diversRevue) {
        this.diversRevue = diversRevue;
        return this;
    }

    public void setDiversRevue(String diversRevue) {
        this.diversRevue = diversRevue;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public Revue notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public Revue addNotation(Note note) {
        this.notations.add(note);
        note.setRevue(this);
        return this;
    }

    public Revue removeNotation(Note note) {
        this.notations.remove(note);
        note.setRevue(null);
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
        Revue revue = (Revue) o;
        if (revue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), revue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Revue{" +
            "id=" + getId() +
            ", nomRevue='" + getNomRevue() + "'" +
            ", audience='" + getAudience() + "'" +
            ", comiteSelection='" + isComiteSelection() + "'" +
            ", langueRevue='" + getLangueRevue() + "'" +
            ", lieuRevue='" + getLieuRevue() + "'" +
            ", lienRevue='" + getLienRevue() + "'" +
            ", diversRevue='" + getDiversRevue() + "'" +
            "}";
    }
}
