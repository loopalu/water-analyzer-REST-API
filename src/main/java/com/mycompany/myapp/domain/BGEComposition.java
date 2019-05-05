package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BGEComposition.
 */
@Entity
@Table(name = "bge_composition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BGEComposition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "background_electrolyte")
    private String backgroundElectrolyte;

    @Column(name = "bge_concentration")
    private Long bgeConcentration;

    @ManyToOne
    @JsonIgnoreProperties("bGECompositions")
    private Method method;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackgroundElectrolyte() {
        return backgroundElectrolyte;
    }

    public BGEComposition backgroundElectrolyte(String backgroundElectrolyte) {
        this.backgroundElectrolyte = backgroundElectrolyte;
        return this;
    }

    public void setBackgroundElectrolyte(String backgroundElectrolyte) {
        this.backgroundElectrolyte = backgroundElectrolyte;
    }

    public Long getBgeConcentration() {
        return bgeConcentration;
    }

    public BGEComposition bgeConcentration(Long bgeConcentration) {
        this.bgeConcentration = bgeConcentration;
        return this;
    }

    public void setBgeConcentration(Long bgeConcentration) {
        this.bgeConcentration = bgeConcentration;
    }

    public Method getMethod() {
        return method;
    }

    public BGEComposition method(Method method) {
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
        BGEComposition bGEComposition = (BGEComposition) o;
        if (bGEComposition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bGEComposition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BGEComposition{" +
            "id=" + getId() +
            ", backgroundElectrolyte='" + getBackgroundElectrolyte() + "'" +
            ", bgeConcentration=" + getBgeConcentration() +
            "}";
    }
}
