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
    @Column(name = "id_conference", nullable = false)
    private String idConference;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeConference type;

    @Column(name = "nom")
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience")
    private Audience audience;

    @Column(name = "comite_selection")
    private Boolean comiteSelection;

    @Column(name = "editeur")
    private String editeur;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @Column(name = "lien_site")
    private String lienSite;

    @Column(name = "lien_actes")
    private String lienActes;

    @Column(name = "divers")
    private String divers;

    @OneToMany(mappedBy = "conference")
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

    public String getIdConference() {
        return idConference;
    }

    public Conference idConference(String idConference) {
        this.idConference = idConference;
        return this;
    }

    public void setIdConference(String idConference) {
        this.idConference = idConference;
    }

    public TypeConference getType() {
        return type;
    }

    public Conference type(TypeConference type) {
        this.type = type;
        return this;
    }

    public void setType(TypeConference type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Conference nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Audience getAudience() {
        return audience;
    }

    public Conference audience(Audience audience) {
        this.audience = audience;
        return this;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Boolean isComiteSelection() {
        return comiteSelection;
    }

    public Conference comiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
        return this;
    }

    public void setComiteSelection(Boolean comiteSelection) {
        this.comiteSelection = comiteSelection;
    }

    public String getEditeur() {
        return editeur;
    }

    public Conference editeur(String editeur) {
        this.editeur = editeur;
        return this;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Conference dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public Conference dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getVille() {
        return ville;
    }

    public Conference ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public Conference pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getLienSite() {
        return lienSite;
    }

    public Conference lienSite(String lienSite) {
        this.lienSite = lienSite;
        return this;
    }

    public void setLienSite(String lienSite) {
        this.lienSite = lienSite;
    }

    public String getLienActes() {
        return lienActes;
    }

    public Conference lienActes(String lienActes) {
        this.lienActes = lienActes;
        return this;
    }

    public void setLienActes(String lienActes) {
        this.lienActes = lienActes;
    }

    public String getDivers() {
        return divers;
    }

    public Conference divers(String divers) {
        this.divers = divers;
        return this;
    }

    public void setDivers(String divers) {
        this.divers = divers;
    }

    public Set<Notation> getNotations() {
        return notations;
    }

    public Conference notations(Set<Notation> notations) {
        this.notations = notations;
        return this;
    }

    public Conference addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setConference(this);
        return this;
    }

    public Conference removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setConference(null);
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
            ", idConference='" + getIdConference() + "'" +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            ", audience='" + getAudience() + "'" +
            ", comiteSelection='" + isComiteSelection() + "'" +
            ", editeur='" + getEditeur() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            ", lienSite='" + getLienSite() + "'" +
            ", lienActes='" + getLienActes() + "'" +
            ", divers='" + getDivers() + "'" +
            "}";
    }
}
