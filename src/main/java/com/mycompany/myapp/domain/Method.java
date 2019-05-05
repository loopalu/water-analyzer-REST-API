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
 * A Method.
 */
@Entity
@Table(name = "method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Method implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "method_name", nullable = false)
    private String methodName;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Long frequency;

    @Column(name = "injection_type")
    private String injectionType;

    @Column(name = "injection_time")
    private Long injectionTime;

    @NotNull
    @Column(name = "measure_value", nullable = false)
    private Long measureValue;

    @NotNull
    @Column(name = "unit_of_measure", nullable = false)
    private String unitOfMeasure;

    @NotNull
    @Column(name = "experiment_time", nullable = false)
    private Float experimentTime;

    @NotNull
    @Column(name = "jhi_current", nullable = false)
    private Float current;

    @OneToMany(mappedBy = "method")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Experiment> experiments = new HashSet<>();
    @OneToMany(mappedBy = "method")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BGEComposition> bGECompositions = new HashSet<>();
    @OneToMany(mappedBy = "method")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnalytesOfInterest> analytesOfInterests = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("methods")
    private CapillaryType capillaryType;

    @ManyToOne
    @JsonIgnoreProperties("methods")
    private MatrixList matrixList;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getFrequency() {
        return frequency;
    }

    public Method frequency(Long frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public String getInjectionType() {
        return injectionType;
    }

    public Method injectionType(String injectionType) {
        this.injectionType = injectionType;
        return this;
    }

    public void setInjectionType(String injectionType) {
        this.injectionType = injectionType;
    }

    public Long getInjectionTime() {
        return injectionTime;
    }

    public Method injectionTime(Long injectionTime) {
        this.injectionTime = injectionTime;
        return this;
    }

    public void setInjectionTime(Long injectionTime) {
        this.injectionTime = injectionTime;
    }

    public Long getMeasureValue() {
        return measureValue;
    }

    public Method measureValue(Long measureValue) {
        this.measureValue = measureValue;
        return this;
    }

    public void setMeasureValue(Long measureValue) {
        this.measureValue = measureValue;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Method unitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Float getExperimentTime() {
        return experimentTime;
    }

    public Method experimentTime(Float experimentTime) {
        this.experimentTime = experimentTime;
        return this;
    }

    public void setExperimentTime(Float experimentTime) {
        this.experimentTime = experimentTime;
    }

    public Float getCurrent() {
        return current;
    }

    public Method current(Float current) {
        this.current = current;
        return this;
    }

    public void setCurrent(Float current) {
        this.current = current;
    }

    public Set<Experiment> getExperiments() {
        return experiments;
    }

    public Method experiments(Set<Experiment> experiments) {
        this.experiments = experiments;
        return this;
    }

    public Method addExperiment(Experiment experiment) {
        this.experiments.add(experiment);
        experiment.setMethod(this);
        return this;
    }

    public Method removeExperiment(Experiment experiment) {
        this.experiments.remove(experiment);
        experiment.setMethod(null);
        return this;
    }

    public void setExperiments(Set<Experiment> experiments) {
        this.experiments = experiments;
    }

    public Set<BGEComposition> getBGECompositions() {
        return bGECompositions;
    }

    public Method bGECompositions(Set<BGEComposition> bGECompositions) {
        this.bGECompositions = bGECompositions;
        return this;
    }

    public Method addBGEComposition(BGEComposition bGEComposition) {
        this.bGECompositions.add(bGEComposition);
        bGEComposition.setMethod(this);
        return this;
    }

    public Method removeBGEComposition(BGEComposition bGEComposition) {
        this.bGECompositions.remove(bGEComposition);
        bGEComposition.setMethod(null);
        return this;
    }

    public void setBGECompositions(Set<BGEComposition> bGECompositions) {
        this.bGECompositions = bGECompositions;
    }

    public Set<AnalytesOfInterest> getAnalytesOfInterests() {
        return analytesOfInterests;
    }

    public Method analytesOfInterests(Set<AnalytesOfInterest> analytesOfInterests) {
        this.analytesOfInterests = analytesOfInterests;
        return this;
    }

    public Method addAnalytesOfInterest(AnalytesOfInterest analytesOfInterest) {
        this.analytesOfInterests.add(analytesOfInterest);
        analytesOfInterest.setMethod(this);
        return this;
    }

    public Method removeAnalytesOfInterest(AnalytesOfInterest analytesOfInterest) {
        this.analytesOfInterests.remove(analytesOfInterest);
        analytesOfInterest.setMethod(null);
        return this;
    }

    public void setAnalytesOfInterests(Set<AnalytesOfInterest> analytesOfInterests) {
        this.analytesOfInterests = analytesOfInterests;
    }

    public CapillaryType getCapillaryType() {
        return capillaryType;
    }

    public Method capillaryType(CapillaryType capillaryType) {
        this.capillaryType = capillaryType;
        return this;
    }

    public void setCapillaryType(CapillaryType capillaryType) {
        this.capillaryType = capillaryType;
    }

    public MatrixList getMatrixList() {
        return matrixList;
    }

    public Method matrixList(MatrixList matrixList) {
        this.matrixList = matrixList;
        return this;
    }

    public void setMatrixList(MatrixList matrixList) {
        this.matrixList = matrixList;
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
        Method method = (Method) o;
        if (method.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), method.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Method{" +
            "id=" + getId() +
            ", methodName='" + getMethodName() + "'" +
            ", frequency=" + getFrequency() +
            ", injectionType='" + getInjectionType() + "'" +
            ", injectionTime=" + getInjectionTime() +
            ", measureValue=" + getMeasureValue() +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", experimentTime=" + getExperimentTime() +
            ", current=" + getCurrent() +
            "}";
    }
}
