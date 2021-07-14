package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.Eligibility} entity. This class is used
 * in {@link com.apptium.customer.web.rest.EligibilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /eligibilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EligibilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter noOfLines;

    private BigDecimalFilter creditAmount;

    private StringFilter description;

    private BooleanFilter isEligiblePaylater;

    public EligibilityCriteria() {}

    public EligibilityCriteria(EligibilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noOfLines = other.noOfLines == null ? null : other.noOfLines.copy();
        this.creditAmount = other.creditAmount == null ? null : other.creditAmount.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isEligiblePaylater = other.isEligiblePaylater == null ? null : other.isEligiblePaylater.copy();
    }

    @Override
    public EligibilityCriteria copy() {
        return new EligibilityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNoOfLines() {
        return noOfLines;
    }

    public IntegerFilter noOfLines() {
        if (noOfLines == null) {
            noOfLines = new IntegerFilter();
        }
        return noOfLines;
    }

    public void setNoOfLines(IntegerFilter noOfLines) {
        this.noOfLines = noOfLines;
    }

    public BigDecimalFilter getCreditAmount() {
        return creditAmount;
    }

    public BigDecimalFilter creditAmount() {
        if (creditAmount == null) {
            creditAmount = new BigDecimalFilter();
        }
        return creditAmount;
    }

    public void setCreditAmount(BigDecimalFilter creditAmount) {
        this.creditAmount = creditAmount;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getIsEligiblePaylater() {
        return isEligiblePaylater;
    }

    public BooleanFilter isEligiblePaylater() {
        if (isEligiblePaylater == null) {
            isEligiblePaylater = new BooleanFilter();
        }
        return isEligiblePaylater;
    }

    public void setIsEligiblePaylater(BooleanFilter isEligiblePaylater) {
        this.isEligiblePaylater = isEligiblePaylater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EligibilityCriteria that = (EligibilityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(noOfLines, that.noOfLines) &&
            Objects.equals(creditAmount, that.creditAmount) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isEligiblePaylater, that.isEligiblePaylater)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noOfLines, creditAmount, description, isEligiblePaylater);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (noOfLines != null ? "noOfLines=" + noOfLines + ", " : "") +
            (creditAmount != null ? "creditAmount=" + creditAmount + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (isEligiblePaylater != null ? "isEligiblePaylater=" + isEligiblePaylater + ", " : "") +
            "}";
    }
}
