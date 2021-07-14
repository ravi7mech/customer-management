package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A IndChar.
 */
@Entity
@Table(name = "ind_char")
public class IndChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private Boolean value;

    @NotNull
    @Column(name = "value_type", nullable = false)
    private String valueType;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Long individualId;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indContacts", "indChars", "indAuditTrials", "custRelParties", "shoppingSessionRefs",
        },
        allowSetters = true
    )
    private Individual individual;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public IndChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return this.value;
    }

    public IndChar value(Boolean value) {
        this.value = value;
        return this;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public IndChar valueType(String valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Long getIndividualId() {
        return this.individualId;
    }

    public IndChar individualId(Long individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public IndChar individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndChar)) {
            return false;
        }
        return id != null && id.equals(((IndChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
