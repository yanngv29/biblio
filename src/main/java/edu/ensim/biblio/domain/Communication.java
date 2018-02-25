package edu.ensim.biblio.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import edu.ensim.biblio.domain.enumeration.TypeCommunication;

/**
 * Communication : identifiant et type obligatoires
 */
@ApiModel(description = "Communication : identifiant et type obligatoires")
@Entity
@Table(name = "communication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Communication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre_communication", nullable = false)
    private String titreCommunication;

    @Column(name = "sous_titre_communication")
    private String sousTitreCommunication;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_communication")
    private TypeCommunication typeCommunication;

    @Column(name = "langue_communication")
    private String langueCommunication;

    @Column(name = "lien_communication")
    private String lienCommunication;

    @Column(name = "doi_communication")
    private String doiCommunication;

    @Column(name = "hal_communication")
    private String halCommunication;

    @Column(name = "divers_communication")
    private String diversCommunication;

    @ManyToOne
    private Conference conference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreCommunication() {
        return titreCommunication;
    }

    public Communication titreCommunication(String titreCommunication) {
        this.titreCommunication = titreCommunication;
        return this;
    }

    public void setTitreCommunication(String titreCommunication) {
        this.titreCommunication = titreCommunication;
    }

    public String getSousTitreCommunication() {
        return sousTitreCommunication;
    }

    public Communication sousTitreCommunication(String sousTitreCommunication) {
        this.sousTitreCommunication = sousTitreCommunication;
        return this;
    }

    public void setSousTitreCommunication(String sousTitreCommunication) {
        this.sousTitreCommunication = sousTitreCommunication;
    }

    public TypeCommunication getTypeCommunication() {
        return typeCommunication;
    }

    public Communication typeCommunication(TypeCommunication typeCommunication) {
        this.typeCommunication = typeCommunication;
        return this;
    }

    public void setTypeCommunication(TypeCommunication typeCommunication) {
        this.typeCommunication = typeCommunication;
    }

    public String getLangueCommunication() {
        return langueCommunication;
    }

    public Communication langueCommunication(String langueCommunication) {
        this.langueCommunication = langueCommunication;
        return this;
    }

    public void setLangueCommunication(String langueCommunication) {
        this.langueCommunication = langueCommunication;
    }

    public String getLienCommunication() {
        return lienCommunication;
    }

    public Communication lienCommunication(String lienCommunication) {
        this.lienCommunication = lienCommunication;
        return this;
    }

    public void setLienCommunication(String lienCommunication) {
        this.lienCommunication = lienCommunication;
    }

    public String getDoiCommunication() {
        return doiCommunication;
    }

    public Communication doiCommunication(String doiCommunication) {
        this.doiCommunication = doiCommunication;
        return this;
    }

    public void setDoiCommunication(String doiCommunication) {
        this.doiCommunication = doiCommunication;
    }

    public String getHalCommunication() {
        return halCommunication;
    }

    public Communication halCommunication(String halCommunication) {
        this.halCommunication = halCommunication;
        return this;
    }

    public void setHalCommunication(String halCommunication) {
        this.halCommunication = halCommunication;
    }

    public String getDiversCommunication() {
        return diversCommunication;
    }

    public Communication diversCommunication(String diversCommunication) {
        this.diversCommunication = diversCommunication;
        return this;
    }

    public void setDiversCommunication(String diversCommunication) {
        this.diversCommunication = diversCommunication;
    }

    public Conference getConference() {
        return conference;
    }

    public Communication conference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
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
        Communication communication = (Communication) o;
        if (communication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), communication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Communication{" +
            "id=" + getId() +
            ", titreCommunication='" + getTitreCommunication() + "'" +
            ", sousTitreCommunication='" + getSousTitreCommunication() + "'" +
            ", typeCommunication='" + getTypeCommunication() + "'" +
            ", langueCommunication='" + getLangueCommunication() + "'" +
            ", lienCommunication='" + getLienCommunication() + "'" +
            ", doiCommunication='" + getDoiCommunication() + "'" +
            ", halCommunication='" + getHalCommunication() + "'" +
            ", diversCommunication='" + getDiversCommunication() + "'" +
            "}";
    }
}
