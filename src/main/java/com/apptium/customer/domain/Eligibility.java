package com.apptium.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Eligibility.
 */
@Entity
@Table(name = "eligibility")
public class Eligibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "no_of_lines", nullable = false)
    private Integer noOfLines;

    @NotNull
    @Column(name = "credit_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal creditAmount;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "is_eligible_paylater", nullable = false)
    private Boolean isEligiblePaylater;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Eligibility id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNoOfLines() {
        return this.noOfLines;
    }

    public Eligibility noOfLines(Integer noOfLines) {
        this.noOfLines = noOfLines;
        return this;
    }

    public void setNoOfLines(Integer noOfLines) {
        this.noOfLines = noOfLines;
    }

    public BigDecimal getCreditAmount() {
        return this.creditAmount;
    }

    public Eligibility creditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
        return this;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDescription() {
        return this.description;
    }

    public Eligibility description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEligiblePaylater() {
        return this.isEligiblePaylater;
    }

    public Eligibility isEligiblePaylater(Boolean isEligiblePaylater) {
        this.isEligiblePaylater = isEligiblePaylater;
        return this;
    }

    public void setIsEligiblePaylater(Boolean isEligiblePaylater) {
        this.isEligiblePaylater = isEligiblePaylater;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eligibility)) {
            return false;
        }
        return id != null && id.equals(((Eligibility) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eligibility{" +
            "id=" + getId() +
            ", noOfLines=" + getNoOfLines() +
            ", creditAmount=" + getCreditAmount() +
            ", description='" + getDescription() + "'" +
            ", isEligiblePaylater='" + getIsEligiblePaylater() + "'" +
            "}";
    }
}
