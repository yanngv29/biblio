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

import edu.ensim.biblio.domain.enumeration.TypeConference;

import edu.ensim.biblio.domain.enumeration.Audience;

/**
 * Conférence : critères non définitifs
 */
@ApiModel(description = "Conférence : critères non définitifs")
@Entity
@Table(name = "conference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_conference", nullable = false)
    private String nomConference;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_conference")
    private TypeConference typeConference;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience_conference")
    private Audience audienceConference;

    @Column(name = "comite_selection_conference")
    private Boolean comiteSelectionConference;

    @Column(name = "date_debut_conference")
    private Instant dateDebutConference;

    @Column(name = "date_fin_conference")
    private Instant dateFinConference;

    @Column(name = "ville_conference")
    private String villeConference;

    @Column(name = "pays_conference")
    private String paysConference;

    @Column(name = "langue_conference")
    private String langueConference;

    @Column(name = "lien_site_conference")
    private String lienSiteConference;

    @Column(name = "divers_conference")
    private String diversConference;

    @OneToMany(mappedBy = "conference")
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

    public String getNomConference() {
        return nomConference;
    }

    public Conference nomConference(String nomConference) {
        this.nomConference = nomConference;
        return this;
    }

    public void setNomConference(String nomConference) {
        this.nomConference = nomConference;
    }

    public TypeConference getTypeConference() {
        return typeConference;
    }

    public Conference typeConference(TypeConference typeConference) {
        this.typeConference = typeConference;
        return this;
    }

    public void setTypeConference(TypeConference typeConference) {
        this.typeConference = typeConference;
    }

    public Audience getAudienceConference() {
        return audienceConference;
    }

    public Conference audienceConference(Audience audienceConference) {
        this.audienceConference = audienceConference;
        return this;
    }

    public void setAudienceConference(Audience audienceConference) {
        this.audienceConference = audienceConference;
    }

    public Boolean isComiteSelectionConference() {
        return comiteSelectionConference;
    }

    public Conference comiteSelectionConference(Boolean comiteSelectionConference) {
        this.comiteSelectionConference = comiteSelectionConference;
        return this;
    }

    public void setComiteSelectionConference(Boolean comiteSelectionConference) {
        this.comiteSelectionConference = comiteSelectionConference;
    }

    public Instant getDateDebutConference() {
        return dateDebutConference;
    }

    public Conference dateDebutConference(Instant dateDebutConference) {
        this.dateDebutConference = dateDebutConference;
        return this;
    }

    public void setDateDebutConference(Instant dateDebutConference) {
        this.dateDebutConference = dateDebutConference;
    }

    public Instant getDateFinConference() {
        return dateFinConference;
    }

    public Conference dateFinConference(Instant dateFinConference) {
        this.dateFinConference = dateFinConference;
        return this;
    }

    public void setDateFinConference(Instant dateFinConference) {
        this.dateFinConference = dateFinConference;
    }

    public String getVilleConference() {
        return villeConference;
    }

    public Conference villeConference(String villeConference) {
        this.villeConference = villeConference;
        return this;
    }

    public void setVilleConference(String villeConference) {
        this.villeConference = villeConference;
    }

    public String getPaysConference() {
        return paysConference;
    }

    public Conference paysConference(String paysConference) {
        this.paysConference = paysConference;
        return this;
    }

    public void setPaysConference(String paysConference) {
        this.paysConference = paysConference;
    }

    public String getLangueConference() {
        return langueConference;
    }

    public Conference langueConference(String langueConference) {
        this.langueConference = langueConference;
        return this;
    }

    public void setLangueConference(String langueConference) {
        this.langueConference = langueConference;
    }

    public String getLienSiteConference() {
        return lienSiteConference;
    }

    public Conference lienSiteConference(String lienSiteConference) {
        this.lienSiteConference = lienSiteConference;
        return this;
    }

    public void setLienSiteConference(String lienSiteConference) {
        this.lienSiteConference = lienSiteConference;
    }

    public String getDiversConference() {
        return diversConference;
    }

    public Conference diversConference(String diversConference) {
        this.diversConference = diversConference;
        return this;
    }

    public void setDiversConference(String diversConference) {
        this.diversConference = diversConference;
    }

    public Set<Note> getNotations() {
        return notations;
    }

    public Conference notations(Set<Note> notes) {
        this.notations = notes;
        return this;
    }

    public Conference addNotation(Note note) {
        this.notations.add(note);
        note.setConference(this);
        return this;
    }

    public Conference removeNotation(Note note) {
        this.notations.remove(note);
        note.setConference(null);
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
        Conference conference = (Conference) o;
        if (conference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conference{" +
            "id=" + getId() +
            ", nomConference='" + getNomConference() + "'" +
            ", typeConference='" + getTypeConference() + "'" +
            ", audienceConference='" + getAudienceConference() + "'" +
            ", comiteSelectionConference='" + isComiteSelectionConference() + "'" +
            ", dateDebutConference='" + getDateDebutConference() + "'" +
            ", dateFinConference='" + getDateFinConference() + "'" +
            ", villeConference='" + getVilleConference() + "'" +
            ", paysConference='" + getPaysConference() + "'" +
            ", langueConference='" + getLangueConference() + "'" +
            ", lienSiteConference='" + getLienSiteConference() + "'" +
            ", diversConference='" + getDiversConference() + "'" +
            "}";
    }
}
