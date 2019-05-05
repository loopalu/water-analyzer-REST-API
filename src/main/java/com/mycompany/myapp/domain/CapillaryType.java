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
 * A CapillaryType.
 */
@Entity
@Table(name = "capillary_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CapillaryType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "capillary_name", nullable = false)
    private String capillaryName;

    @NotNull
    @Column(name = "capillary_length", nullable = false)
    private Float capillaryLength;

    @NotNull
    @Column(name = "effective_length", nullable = false)
    private Float effectiveLength;

    @NotNull
    @Column(name = "inner_diameter", nullable = false)
    private Float innerDiameter;

    @NotNull
    @Column(name = "outer_diameter", nullable = false)
    private Float outerDiameter;

    @Column(name = "electric_force")
    private Float electricForce;

    @OneToMany(mappedBy = "capillaryType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Method> methods = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapillaryName() {
        return capillaryName;
    }

    public CapillaryType capillaryName(String capillaryName) {
        this.capillaryName = capillaryName;
        return this;
    }

    public void setCapillaryName(String capillaryName) {
        this.capillaryName = capillaryName;
    }

    public Float getCapillaryLength() {
        return capillaryLength;
    }

    public CapillaryType capillaryLength(Float capillaryLength) {
        this.capillaryLength = capillaryLength;
        return this;
    }

    public void setCapillaryLength(Float capillaryLength) {
        this.capillaryLength = capillaryLength;
    }

    public Float getEffectiveLength() {
        return effectiveLength;
    }

    public CapillaryType effectiveLength(Float effectiveLength) {
        this.effectiveLength = effectiveLength;
        return this;
    }

    public void setEffectiveLength(Float effectiveLength) {
        this.effectiveLength = effectiveLength;
    }

    public Float getInnerDiameter() {
        return innerDiameter;
    }

    public CapillaryType innerDiameter(Float innerDiameter) {
        this.innerDiameter = innerDiameter;
        return this;
    }

    public void setInnerDiameter(Float innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public Float getOuterDiameter() {
        return outerDiameter;
    }

    public CapillaryType outerDiameter(Float outerDiameter) {
        this.outerDiameter = outerDiameter;
        return this;
    }

    public void setOuterDiameter(Float outerDiameter) {
        this.outerDiameter = outerDiameter;
    }

    public Float getElectricForce() {
        return electricForce;
    }

    public CapillaryType electricForce(Float electricForce) {
        this.electricForce = electricForce;
        return this;
    }

    public void setElectricForce(Float electricForce) {
        this.electricForce = electricForce;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public CapillaryType methods(Set<Method> methods) {
        this.methods = methods;
        return this;
    }

    public CapillaryType addMethod(Method method) {
        this.methods.add(method);
        method.setCapillaryType(this);
        return this;
    }

    public CapillaryType removeMethod(Method method) {
        this.methods.remove(method);
        method.setCapillaryType(null);
        return this;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
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
        CapillaryType capillaryType = (CapillaryType) o;
        if (capillaryType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capillaryType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapillaryType{" +
            "id=" + getId() +
            ", capillaryName='" + getCapillaryName() + "'" +
            ", capillaryLength=" + getCapillaryLength() +
            ", effectiveLength=" + getEffectiveLength() +
            ", innerDiameter=" + getInnerDiameter() +
            ", outerDiameter=" + getOuterDiameter() +
            ", electricForce=" + getElectricForce() +
            "}";
    }
}
