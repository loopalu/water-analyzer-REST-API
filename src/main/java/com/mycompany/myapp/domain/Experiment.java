package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Experiment.
 */
@Entity
@Table(name = "experiment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Experiment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "experiment_time", nullable = false)
    private Instant experimentTime;

    @NotNull
    @Column(name = "experiment_type", nullable = false)
    private String experimentType;

    @OneToMany(mappedBy = "experiment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperimentAnalysis> experimentAnalyses = new HashSet<>();
    @OneToMany(mappedBy = "experiment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperimentResults> experimentResults = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("experiments")
    private Method method;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExperimentTime() {
        return experimentTime;
    }

    public Experiment experimentTime(Instant experimentTime) {
        this.experimentTime = experimentTime;
        return this;
    }

    public void setExperimentTime(Instant experimentTime) {
        this.experimentTime = experimentTime;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public Experiment experimentType(String experimentType) {
        this.experimentType = experimentType;
        return this;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    public Set<ExperimentAnalysis> getExperimentAnalyses() {
        return experimentAnalyses;
    }

    public Experiment experimentAnalyses(Set<ExperimentAnalysis> experimentAnalyses) {
        this.experimentAnalyses = experimentAnalyses;
        return this;
    }

    public Experiment addExperimentAnalysis(ExperimentAnalysis experimentAnalysis) {
        this.experimentAnalyses.add(experimentAnalysis);
        experimentAnalysis.setExperiment(this);
        return this;
    }

    public Experiment removeExperimentAnalysis(ExperimentAnalysis experimentAnalysis) {
        this.experimentAnalyses.remove(experimentAnalysis);
        experimentAnalysis.setExperiment(null);
        return this;
    }

    public void setExperimentAnalyses(Set<ExperimentAnalysis> experimentAnalyses) {
        this.experimentAnalyses = experimentAnalyses;
    }

    public Set<ExperimentResults> getExperimentResults() {
        return experimentResults;
    }

    public Experiment experimentResults(Set<ExperimentResults> experimentResults) {
        this.experimentResults = experimentResults;
        return this;
    }

    public Experiment addExperimentResults(ExperimentResults experimentResults) {
        this.experimentResults.add(experimentResults);
        experimentResults.setExperiment(this);
        return this;
    }

    public Experiment removeExperimentResults(ExperimentResults experimentResults) {
        this.experimentResults.remove(experimentResults);
        experimentResults.setExperiment(null);
        return this;
    }

    public void setExperimentResults(Set<ExperimentResults> experimentResults) {
        this.experimentResults = experimentResults;
    }

    public Method getMethod() {
        return method;
    }

    public Experiment method(Method method) {
        this.method = method;
        return this;
    }

    public void setMethod(Method method) {
        this.method = method;
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
        Experiment experiment = (Experiment) o;
        if (experiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Experiment{" +
            "id=" + getId() +
            ", experimentTime='" + getExperimentTime() + "'" +
            ", experimentType='" + getExperimentType() + "'" +
            "}";
    }
}
