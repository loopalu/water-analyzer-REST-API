package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MatrixList.
 */
@Entity
@Table(name = "matrix_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatrixList implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "matrix_name")
    private String matrixName;

    @OneToMany(mappedBy = "matrixList")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Method> methods = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public MatrixList matrixName(String matrixName) {
        this.matrixName = matrixName;
        return this;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName = matrixName;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public MatrixList methods(Set<Method> methods) {
        this.methods = methods;
        return this;
    }

    public MatrixList addMethod(Method method) {
        this.methods.add(method);
        method.setMatrixList(this);
        return this;
    }

    public MatrixList removeMethod(Method method) {
        this.methods.remove(method);
        method.setMatrixList(null);
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
        MatrixList matrixList = (MatrixList) o;
        if (matrixList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matrixList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatrixList{" +
            "id=" + getId() +
            ", matrixName='" + getMatrixName() + "'" +
            "}";
    }
}
