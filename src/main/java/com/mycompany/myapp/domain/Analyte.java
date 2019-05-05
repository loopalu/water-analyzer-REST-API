package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Analyte.
 */
@Entity
@Table(name = "analyte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Analyte implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "analyte_name", nullable = false)
    private String analyteName;

    @NotNull
    @Column(name = "analyte_group", nullable = false)
    private String analyteGroup;

    @OneToMany(mappedBy = "analyte")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperimentPeaks> experimentPeaks = new HashSet<>();
    @OneToMany(mappedBy = "analyte")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnalytesOfInterest> analytesOfInterests = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnalyteName() {
        return analyteName;
    }

    public Analyte analyteName(String analyteName) {
        this.analyteName = analyteName;
        return this;
    }

    public void setAnalyteName(String analyteName) {
        this.analyteName = analyteName;
    }

    public String getAnalyteGroup() {
        return analyteGroup;
    }

    public Analyte analyteGroup(String analyteGroup) {
        this.analyteGroup = analyteGroup;
        return this;
    }

    public void setAnalyteGroup(String analyteGroup) {
        this.analyteGroup = analyteGroup;
    }

    public Set<ExperimentPeaks> getExperimentPeaks() {
        return experimentPeaks;
    }

    public Analyte experimentPeaks(Set<ExperimentPeaks> experimentPeaks) {
        this.experimentPeaks = experimentPeaks;
        return this;
    }

    public Analyte addExperimentPeaks(ExperimentPeaks experimentPeaks) {
        this.experimentPeaks.add(experimentPeaks);
        experimentPeaks.setAnalyte(this);
        return this;
    }

    public Analyte removeExperimentPeaks(ExperimentPeaks experimentPeaks) {
        this.experimentPeaks.remove(experimentPeaks);
        experimentPeaks.setAnalyte(null);
        return this;
    }

    public void setExperimentPeaks(Set<ExperimentPeaks> experimentPeaks) {
        this.experimentPeaks = experimentPeaks;
    }

    public Set<AnalytesOfInterest> getAnalytesOfInterests() {
        return analytesOfInterests;
    }

    public Analyte analytesOfInterests(Set<AnalytesOfInterest> analytesOfInterests) {
        this.analytesOfInterests = analytesOfInterests;
        return this;
    }

    public Analyte addAnalytesOfInterest(AnalytesOfInterest analytesOfInterest) {
        this.analytesOfInterests.add(analytesOfInterest);
        analytesOfInterest.setAnalyte(this);
        return this;
    }

    public Analyte removeAnalytesOfInterest(AnalytesOfInterest analytesOfInterest) {
        this.analytesOfInterests.remove(analytesOfInterest);
        analytesOfInterest.setAnalyte(null);
        return this;
    }

    public void setAnalytesOfInterests(Set<AnalytesOfInterest> analytesOfInterests) {
        this.analytesOfInterests = analytesOfInterests;
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
        Analyte analyte = (Analyte) o;
        if (analyte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), analyte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Analyte{" +
            "id=" + getId() +
            ", analyteName='" + getAnalyteName() + "'" +
            ", analyteGroup='" + getAnalyteGroup() + "'" +
            "}";
    }
}
