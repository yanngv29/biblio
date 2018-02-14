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
    @Column(name = "id_communication", nullable = false)
    private String idCommunication;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeCommunication type;

    @Column(name = "hal")
    private String hal;

    @ManyToOne
    private Conference conference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCommunication() {
        return idCommunication;
    }

    public Communication idCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
        return this;
    }

    public void setIdCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
    }

    public TypeCommunication getType() {
        return type;
    }

    public Communication type(TypeCommunication type) {
        this.type = type;
        return this;
    }

    public void setType(TypeCommunication type) {
        this.type = type;
    }

    public String getHal() {
        return hal;
    }

    public Communication hal(String hal) {
        this.hal = hal;
        return this;
    }

    public void setHal(String hal) {
        this.hal = hal;
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
            ", idCommunication='" + getIdCommunication() + "'" +
            ", type='" + getType() + "'" +
            ", hal='" + getHal() + "'" +
            "}";
    }
}
