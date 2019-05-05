package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExperimentPeaks.
 */
@Entity
@Table(name = "experiment_peaks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExperimentPeaks implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "peak_number")
    private Long peakNumber;

    @Column(name = "peak_start")
    private Long peakStart;

    @Column(name = "peak_end")
    private Long peakEnd;

    @Column(name = "peak_highest")
    private Long peakHighest;

    @Column(name = "peak_area")
    private Float peakArea;

    @Column(name = "analyte_concentration")
    private Float analyteConcentration;

    @ManyToOne
    @JsonIgnoreProperties("experimentPeaks")
    private Analyte analyte;

    @ManyToOne
    @JsonIgnoreProperties("experimentPeaks")
    private ExperimentAnalysis experimentAnalysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeakNumber() {
        return peakNumber;
    }

    public ExperimentPeaks peakNumber(Long peakNumber) {
        this.peakNumber = peakNumber;
        return this;
    }

    public void setPeakNumber(Long peakNumber) {
        this.peakNumber = peakNumber;
    }

    public Long getPeakStart() {
        return peakStart;
    }

    public ExperimentPeaks peakStart(Long peakStart) {
        this.peakStart = peakStart;
        return this;
    }

    public void setPeakStart(Long peakStart) {
        this.peakStart = peakStart;
    }

    public Long getPeakEnd() {
        return peakEnd;
    }

    public ExperimentPeaks peakEnd(Long peakEnd) {
        this.peakEnd = peakEnd;
        return this;
    }

    public void setPeakEnd(Long peakEnd) {
        this.peakEnd = peakEnd;
    }

    public Long getPeakHighest() {
        return peakHighest;
    }

    public ExperimentPeaks peakHighest(Long peakHighest) {
        this.peakHighest = peakHighest;
        return this;
    }

    public void setPeakHighest(Long peakHighest) {
        this.peakHighest = peakHighest;
    }

    public Float getPeakArea() {
        return peakArea;
    }

    public ExperimentPeaks peakArea(Float peakArea) {
        this.peakArea = peakArea;
        return this;
    }

    public void setPeakArea(Float peakArea) {
        this.peakArea = peakArea;
    }

    public Float getAnalyteConcentration() {
        return analyteConcentration;
    }

    public ExperimentPeaks analyteConcentration(Float analyteConcentration) {
        this.analyteConcentration = analyteConcentration;
        return this;
    }

    public void setAnalyteConcentration(Float analyteConcentration) {
        this.analyteConcentration = analyteConcentration;
    }

    public Analyte getAnalyte() {
        return analyte;
    }

    public ExperimentPeaks analyte(Analyte analyte) {
        this.analyte = analyte;
        return this;
    }

    public void setAnalyte(Analyte analyte) {
        this.analyte = analyte;
    }

    public ExperimentAnalysis getExperimentAnalysis() {
        return experimentAnalysis;
    }

    public ExperimentPeaks experimentAnalysis(ExperimentAnalysis experimentAnalysis) {
        this.experimentAnalysis = experimentAnalysis;
        return this;
    }

    public void setExperimentAnalysis(ExperimentAnalysis experimentAnalysis) {
        this.experimentAnalysis = experimentAnalysis;
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
        ExperimentPeaks experimentPeaks = (ExperimentPeaks) o;
        if (experimentPeaks.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experimentPeaks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperimentPeaks{" +
            "id=" + getId() +
            ", peakNumber=" + getPeakNumber() +
            ", peakStart=" + getPeakStart() +
            ", peakEnd=" + getPeakEnd() +
            ", peakHighest=" + getPeakHighest() +
            ", peakArea=" + getPeakArea() +
            ", analyteConcentration=" + getAnalyteConcentration() +
            "}";
    }
}
