package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AnalytesOfInterest.
 */
@Entity
@Table(name = "analytes_of_interest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalytesOfInterest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("analytesOfInterests")
    private Method method;

    @ManyToOne
    @JsonIgnoreProperties("analytesOfInterests")
    private Analyte analyte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Method getMethod() {
        return method;
    }

    public AnalytesOfInterest method(Method method) {
        this.method = method;
        return this;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Analyte getAnalyte() {
        return analyte;
    }

    public AnalytesOfInterest analyte(Analyte analyte) {
        this.analyte = analyte;
        return this;
    }

    public void setAnalyte(Analyte analyte) {
        this.analyte = analyte;
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
        AnalytesOfInterest analytesOfInterest = (AnalytesOfInterest) o;
        if (analytesOfInterest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), analytesOfInterest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnalytesOfInterest{" +
            "id=" + getId() +
            "}";
    }
}
