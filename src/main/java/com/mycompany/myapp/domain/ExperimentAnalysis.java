package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExperimentAnalysis.
 */
@Entity
@Table(name = "experiment_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExperimentAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "measuring_point", nullable = false)
    private Long measuringPoint;

    @NotNull
    @Column(name = "voltage_experiment", nullable = false)
    private Long voltageExperiment;

    @NotNull
    @Column(name = "voltage_smoothed", nullable = false)
    private Long voltageSmoothed;

    @NotNull
    @Column(name = "value_moving_average_subtracted", nullable = false)
    private Long valueMovingAverageSubtracted;

    @NotNull
    @Column(name = "value_over_threshold", nullable = false)
    private Long valueOverThreshold;

    @OneToMany(mappedBy = "experimentAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperimentPeaks> experimentPeaks = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("experimentAnalyses")
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

    public ExperimentAnalysis measuringPoint(Long measuringPoint) {
        this.measuringPoint = measuringPoint;
        return this;
    }

    public void setMeasuringPoint(Long measuringPoint) {
        this.measuringPoint = measuringPoint;
    }

    public Long getVoltageExperiment() {
        return voltageExperiment;
    }

    public ExperimentAnalysis voltageExperiment(Long voltageExperiment) {
        this.voltageExperiment = voltageExperiment;
        return this;
    }

    public void setVoltageExperiment(Long voltageExperiment) {
        this.voltageExperiment = voltageExperiment;
    }

    public Long getVoltageSmoothed() {
        return voltageSmoothed;
    }

    public ExperimentAnalysis voltageSmoothed(Long voltageSmoothed) {
        this.voltageSmoothed = voltageSmoothed;
        return this;
    }

    public void setVoltageSmoothed(Long voltageSmoothed) {
        this.voltageSmoothed = voltageSmoothed;
    }

    public Long getValueMovingAverageSubtracted() {
        return valueMovingAverageSubtracted;
    }

    public ExperimentAnalysis valueMovingAverageSubtracted(Long valueMovingAverageSubtracted) {
        this.valueMovingAverageSubtracted = valueMovingAverageSubtracted;
        return this;
    }

    public void setValueMovingAverageSubtracted(Long valueMovingAverageSubtracted) {
        this.valueMovingAverageSubtracted = valueMovingAverageSubtracted;
    }

    public Long getValueOverThreshold() {
        return valueOverThreshold;
    }

    public ExperimentAnalysis valueOverThreshold(Long valueOverThreshold) {
        this.valueOverThreshold = valueOverThreshold;
        return this;
    }

    public void setValueOverThreshold(Long valueOverThreshold) {
        this.valueOverThreshold = valueOverThreshold;
    }

    public Set<ExperimentPeaks> getExperimentPeaks() {
        return experimentPeaks;
    }

    public ExperimentAnalysis experimentPeaks(Set<ExperimentPeaks> experimentPeaks) {
        this.experimentPeaks = experimentPeaks;
        return this;
    }

    public ExperimentAnalysis addExperimentPeaks(ExperimentPeaks experimentPeaks) {
        this.experimentPeaks.add(experimentPeaks);
        experimentPeaks.setExperimentAnalysis(this);
        return this;
    }

    public ExperimentAnalysis removeExperimentPeaks(ExperimentPeaks experimentPeaks) {
        this.experimentPeaks.remove(experimentPeaks);
        experimentPeaks.setExperimentAnalysis(null);
        return this;
    }

    public void setExperimentPeaks(Set<ExperimentPeaks> experimentPeaks) {
        this.experimentPeaks = experimentPeaks;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public ExperimentAnalysis experiment(Experiment experiment) {
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
        ExperimentAnalysis experimentAnalysis = (ExperimentAnalysis) o;
        if (experimentAnalysis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experimentAnalysis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperimentAnalysis{" +
            "id=" + getId() +
            ", measuringPoint=" + getMeasuringPoint() +
            ", voltageExperiment=" + getVoltageExperiment() +
            ", voltageSmoothed=" + getVoltageSmoothed() +
            ", valueMovingAverageSubtracted=" + getValueMovingAverageSubtracted() +
            ", valueOverThreshold=" + getValueOverThreshold() +
            "}";
    }
}
