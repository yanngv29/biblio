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
    @Column(name = "id_chapitre", nullable = false)
    private String idChapitre;

    @Column(name = "page_debut")
    private String pageDebut;

    @Column(name = "page_fin")
    private String pageFin;

    @Column(name = "hal")
    private String hal;

    @ManyToOne
    private Ouvrage ouvrage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdChapitre() {
        return idChapitre;
    }

    public Chapitre idChapitre(String idChapitre) {
        this.idChapitre = idChapitre;
        return this;
    }

    public void setIdChapitre(String idChapitre) {
        this.idChapitre = idChapitre;
    }

    public String getPageDebut() {
        return pageDebut;
    }

    public Chapitre pageDebut(String pageDebut) {
        this.pageDebut = pageDebut;
        return this;
    }

    public void setPageDebut(String pageDebut) {
        this.pageDebut = pageDebut;
    }

    public String getPageFin() {
        return pageFin;
    }

    public Chapitre pageFin(String pageFin) {
        this.pageFin = pageFin;
        return this;
    }

    public void setPageFin(String pageFin) {
        this.pageFin = pageFin;
    }

    public String getHal() {
        return hal;
    }

    public Chapitre hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
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
            ", idChapitre='" + getIdChapitre() + "'" +
            ", pageDebut='" + getPageDebut() + "'" +
            ", pageFin='" + getPageFin() + "'" +
            ", hal='" + getHal() + "'" +
            "}";
    }
}
