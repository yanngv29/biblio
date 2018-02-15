package edu.ensim.biblio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Numéro d'une revue
 */
@ApiModel(description = "Numéro d'une revue")
@Entity
@Table(name = "numero_revue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NumeroRevue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "volume_numero_revue")
    private String volumeNumeroRevue;

    @Column(name = "numero_volume_numero_revue")
    private String numeroVolumeNumeroRevue;

    @Column(name = "mois_numero_revue")
    private String moisNumeroRevue;

    @Column(name = "annee_numero_revue")
    private String anneeNumeroRevue;

    @Column(name = "langue_numero_revue")
    private String langueNumeroRevue;

    @Column(name = "lien_numero_revue")
    private String lienNumeroRevue;

    @Column(name = "doi_numero_revue")
    private String doiNumeroRevue;

    @Column(name = "divers_numero_revue")
    private String diversNumeroRevue;

    @ManyToOne
    private Revue revue;

    @OneToMany(mappedBy = "numeroRevue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Article> writers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolumeNumeroRevue() {
        return volumeNumeroRevue;
    }

    public NumeroRevue volumeNumeroRevue(String volumeNumeroRevue) {
        this.volumeNumeroRevue = volumeNumeroRevue;
        return this;
    }

    public void setVolumeNumeroRevue(String volumeNumeroRevue) {
        this.volumeNumeroRevue = volumeNumeroRevue;
    }

    public String getNumeroVolumeNumeroRevue() {
        return numeroVolumeNumeroRevue;
    }

    public NumeroRevue numeroVolumeNumeroRevue(String numeroVolumeNumeroRevue) {
        this.numeroVolumeNumeroRevue = numeroVolumeNumeroRevue;
        return this;
    }

    public void setNumeroVolumeNumeroRevue(String numeroVolumeNumeroRevue) {
        this.numeroVolumeNumeroRevue = numeroVolumeNumeroRevue;
    }

    public String getMoisNumeroRevue() {
        return moisNumeroRevue;
    }

    public NumeroRevue moisNumeroRevue(String moisNumeroRevue) {
        this.moisNumeroRevue = moisNumeroRevue;
        return this;
    }

    public void setMoisNumeroRevue(String moisNumeroRevue) {
        this.moisNumeroRevue = moisNumeroRevue;
    }

    public String getAnneeNumeroRevue() {
        return anneeNumeroRevue;
    }

    public NumeroRevue anneeNumeroRevue(String anneeNumeroRevue) {
        this.anneeNumeroRevue = anneeNumeroRevue;
        return this;
    }

    public void setAnneeNumeroRevue(String anneeNumeroRevue) {
        this.anneeNumeroRevue = anneeNumeroRevue;
    }

    public String getLangueNumeroRevue() {
        return langueNumeroRevue;
    }

    public NumeroRevue langueNumeroRevue(String langueNumeroRevue) {
        this.langueNumeroRevue = langueNumeroRevue;
        return this;
    }

    public void setLangueNumeroRevue(String langueNumeroRevue) {
        this.langueNumeroRevue = langueNumeroRevue;
    }

    public String getLienNumeroRevue() {
        return lienNumeroRevue;
    }

    public NumeroRevue lienNumeroRevue(String lienNumeroRevue) {
        this.lienNumeroRevue = lienNumeroRevue;
        return this;
    }

    public void setLienNumeroRevue(String lienNumeroRevue) {
        this.lienNumeroRevue = lienNumeroRevue;
    }

    public String getDoiNumeroRevue() {
        return doiNumeroRevue;
    }

    public NumeroRevue doiNumeroRevue(String doiNumeroRevue) {
        this.doiNumeroRevue = doiNumeroRevue;
        return this;
    }

    public void setDoiNumeroRevue(String doiNumeroRevue) {
        this.doiNumeroRevue = doiNumeroRevue;
    }

    public String getDiversNumeroRevue() {
        return diversNumeroRevue;
    }

    public NumeroRevue diversNumeroRevue(String diversNumeroRevue) {
        this.diversNumeroRevue = diversNumeroRevue;
        return this;
    }

    public void setDiversNumeroRevue(String diversNumeroRevue) {
        this.diversNumeroRevue = diversNumeroRevue;
    }

    public Revue getRevue() {
        return revue;
    }

    public NumeroRevue revue(Revue revue) {
        this.revue = revue;
        return this;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
    }

    public Set<Article> getWriters() {
        return writers;
    }

    public NumeroRevue writers(Set<Article> articles) {
        this.writers = articles;
        return this;
    }

    public NumeroRevue addWriter(Article article) {
        this.writers.add(article);
        article.setNumeroRevue(this);
        return this;
    }

    public NumeroRevue removeWriter(Article article) {
        this.writers.remove(article);
        article.setNumeroRevue(null);
        return this;
    }

    public void setWriters(Set<Article> articles) {
        this.writers = articles;
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
        NumeroRevue numeroRevue = (NumeroRevue) o;
        if (numeroRevue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numeroRevue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumeroRevue{" +
            "id=" + getId() +
            ", volumeNumeroRevue='" + getVolumeNumeroRevue() + "'" +
            ", numeroVolumeNumeroRevue='" + getNumeroVolumeNumeroRevue() + "'" +
            ", moisNumeroRevue='" + getMoisNumeroRevue() + "'" +
            ", anneeNumeroRevue='" + getAnneeNumeroRevue() + "'" +
            ", langueNumeroRevue='" + getLangueNumeroRevue() + "'" +
            ", lienNumeroRevue='" + getLienNumeroRevue() + "'" +
            ", doiNumeroRevue='" + getDoiNumeroRevue() + "'" +
            ", diversNumeroRevue='" + getDiversNumeroRevue() + "'" +
            "}";
    }
}
