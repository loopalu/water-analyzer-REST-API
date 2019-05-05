package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExperimentResults.
 */
@Entity
@Table(name = "experiment_results")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExperimentResults implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "measuring_point")
    private Long measuringPoint;

    @Column(name = "voltage_value")
    private Long voltageValue;

    @ManyToOne
    @JsonIgnoreProperties("experimentResults")
    private Experiment experiment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeasuringPoint() {
        return measuringPoint;
    }

    public ExperimentResults measuringPoint(Long measuringPoint) {
        this.measuringPoint = measuringPoint;
        return this;
    }

    public void setMeasuringPoint(Long measuringPoint) {
        this.measuringPoint = measuringPoint;
    }

    public Long getVoltageValue() {
        return voltageValue;
    }

    public ExperimentResults voltageValue(Long voltageValue) {
        this.voltageValue = voltageValue;
        return this;
    }

    public void setVoltageValue(Long voltageValue) {
        this.voltageValue = voltageValue;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public ExperimentResults experiment(Experiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
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
        ExperimentResults experimentResults = (ExperimentResults) o;
        if (experimentResults.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experimentResults.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperimentResults{" +
            "id=" + getId() +
            ", measuringPoint=" + getMeasuringPoint() +
            ", voltageValue=" + getVoltageValue() +
            "}";
    }
}
