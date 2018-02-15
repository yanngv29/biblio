package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Chapitre : identifiant nécessaire
 */
@ApiModel(description = "Chapitre : identifiant nécessaire")
@Entity
@Table(name = "chapitre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapitre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_chapitre", nullable = false)
    private String titreChapitre;

    @Column(name = "page_debut_chapitre")
    private String pageDebutChapitre;

    @Column(name = "page_fin_chapitre")
    private String pageFinChapitre;

    @Column(name = "langue_chapitre")
    private String langueChapitre;

    @Column(name = "lien_chapitre")
    private String lienChapitre;

    @Column(name = "hal_chapitre")
    private String halChapitre;

    @Column(name = "divers_chapitre")
    private String diversChapitre;

    @ManyToOne
    private Ouvrage ouvrage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreChapitre() {
        return titreChapitre;
    }

    public Chapitre titreChapitre(String titreChapitre) {
        this.titreChapitre = titreChapitre;
        return this;
    }

    public void setTitreChapitre(String titreChapitre) {
        this.titreChapitre = titreChapitre;
    }

    public String getPageDebutChapitre() {
        return pageDebutChapitre;
    }

    public Chapitre pageDebutChapitre(String pageDebutChapitre) {
        this.pageDebutChapitre = pageDebutChapitre;
        return this;
    }

    public void setPageDebutChapitre(String pageDebutChapitre) {
        this.pageDebutChapitre = pageDebutChapitre;
    }

    public String getPageFinChapitre() {
        return pageFinChapitre;
    }

    public Chapitre pageFinChapitre(String pageFinChapitre) {
        this.pageFinChapitre = pageFinChapitre;
        return this;
    }

    public void setPageFinChapitre(String pageFinChapitre) {
        this.pageFinChapitre = pageFinChapitre;
    }

    public String getLangueChapitre() {
        return langueChapitre;
    }

    public Chapitre langueChapitre(String langueChapitre) {
        this.langueChapitre = langueChapitre;
        return this;
    }

    public void setLangueChapitre(String langueChapitre) {
        this.langueChapitre = langueChapitre;
    }

    public String getLienChapitre() {
        return lienChapitre;
    }

    public Chapitre lienChapitre(String lienChapitre) {
        this.lienChapitre = lienChapitre;
        return this;
    }

    public void setLienChapitre(String lienChapitre) {
        this.lienChapitre = lienChapitre;
    }

    public String getHalChapitre() {
        return halChapitre;
    }

    public Chapitre halChapitre(String halChapitre) {
        this.halChapitre = halChapitre;
        return this;
    }

    public void setHalChapitre(String halChapitre) {
        this.halChapitre = halChapitre;
    }

    public String getDiversChapitre() {
        return diversChapitre;
    }

    public Chapitre diversChapitre(String diversChapitre) {
        this.diversChapitre = diversChapitre;
        return this;
    }

    public void setDiversChapitre(String diversChapitre) {
        this.diversChapitre = diversChapitre;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public Chapitre ouvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        return this;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
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
        Chapitre chapitre = (Chapitre) o;
        if (chapitre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapitre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapitre{" +
            "id=" + getId() +
            ", titreChapitre='" + getTitreChapitre() + "'" +
            ", pageDebutChapitre='" + getPageDebutChapitre() + "'" +
            ", pageFinChapitre='" + getPageFinChapitre() + "'" +
            ", langueChapitre='" + getLangueChapitre() + "'" +
            ", lienChapitre='" + getLienChapitre() + "'" +
            ", halChapitre='" + getHalChapitre() + "'" +
            ", diversChapitre='" + getDiversChapitre() + "'" +
            "}";
    }
}
