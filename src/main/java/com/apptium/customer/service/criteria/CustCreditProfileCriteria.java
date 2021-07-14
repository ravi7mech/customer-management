package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustCreditProfile} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustCreditProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-credit-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustCreditProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter custIdTypeOne;

    private StringFilter custIdRefOne;

    private StringFilter custIdTypeTwo;

    private StringFilter custIdRefTwo;

    private LongFilter creditCardNumber;

    private InstantFilter creditProfileData;

    private StringFilter creditRiskRating;

    private StringFilter creditRiskRatingDesc;

    private IntegerFilter creditScore;

    private InstantFilter validUntil;

    private LongFilter customerId;

    private LongFilter customerId;

    public CustCreditProfileCriteria() {}

    public CustCreditProfileCriteria(CustCreditProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.custIdTypeOne = other.custIdTypeOne == null ? null : other.custIdTypeOne.copy();
        this.custIdRefOne = other.custIdRefOne == null ? null : other.custIdRefOne.copy();
        this.custIdTypeTwo = other.custIdTypeTwo == null ? null : other.custIdTypeTwo.copy();
        this.custIdRefTwo = other.custIdRefTwo == null ? null : other.custIdRefTwo.copy();
        this.creditCardNumber = other.creditCardNumber == null ? null : other.creditCardNumber.copy();
        this.creditProfileData = other.creditProfileData == null ? null : other.creditProfileData.copy();
        this.creditRiskRating = other.creditRiskRating == null ? null : other.creditRiskRating.copy();
        this.creditRiskRatingDesc = other.creditRiskRatingDesc == null ? null : other.creditRiskRatingDesc.copy();
        this.creditScore = other.creditScore == null ? null : other.creditScore.copy();
        this.validUntil = other.validUntil == null ? null : other.validUntil.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustCreditProfileCriteria copy() {
        return new CustCreditProfileCriteria(this);
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

    public StringFilter getCustIdTypeOne() {
        return custIdTypeOne;
    }

    public StringFilter custIdTypeOne() {
        if (custIdTypeOne == null) {
            custIdTypeOne = new StringFilter();
        }
        return custIdTypeOne;
    }

    public void setCustIdTypeOne(StringFilter custIdTypeOne) {
        this.custIdTypeOne = custIdTypeOne;
    }

    public StringFilter getCustIdRefOne() {
        return custIdRefOne;
    }

    public StringFilter custIdRefOne() {
        if (custIdRefOne == null) {
            custIdRefOne = new StringFilter();
        }
        return custIdRefOne;
    }

    public void setCustIdRefOne(StringFilter custIdRefOne) {
        this.custIdRefOne = custIdRefOne;
    }

    public StringFilter getCustIdTypeTwo() {
        return custIdTypeTwo;
    }

    public StringFilter custIdTypeTwo() {
        if (custIdTypeTwo == null) {
            custIdTypeTwo = new StringFilter();
        }
        return custIdTypeTwo;
    }

    public void setCustIdTypeTwo(StringFilter custIdTypeTwo) {
        this.custIdTypeTwo = custIdTypeTwo;
    }

    public StringFilter getCustIdRefTwo() {
        return custIdRefTwo;
    }

    public StringFilter custIdRefTwo() {
        if (custIdRefTwo == null) {
            custIdRefTwo = new StringFilter();
        }
        return custIdRefTwo;
    }

    public void setCustIdRefTwo(StringFilter custIdRefTwo) {
        this.custIdRefTwo = custIdRefTwo;
    }

    public LongFilter getCreditCardNumber() {
        return creditCardNumber;
    }

    public LongFilter creditCardNumber() {
        if (creditCardNumber == null) {
            creditCardNumber = new LongFilter();
        }
        return creditCardNumber;
    }

    public void setCreditCardNumber(LongFilter creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public InstantFilter getCreditProfileData() {
        return creditProfileData;
    }

    public InstantFilter creditProfileData() {
        if (creditProfileData == null) {
            creditProfileData = new InstantFilter();
        }
        return creditProfileData;
    }

    public void setCreditProfileData(InstantFilter creditProfileData) {
        this.creditProfileData = creditProfileData;
    }

    public StringFilter getCreditRiskRating() {
        return creditRiskRating;
    }

    public StringFilter creditRiskRating() {
        if (creditRiskRating == null) {
            creditRiskRating = new StringFilter();
        }
        return creditRiskRating;
    }

    public void setCreditRiskRating(StringFilter creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
    }

    public StringFilter getCreditRiskRatingDesc() {
        return creditRiskRatingDesc;
    }

    public StringFilter creditRiskRatingDesc() {
        if (creditRiskRatingDesc == null) {
            creditRiskRatingDesc = new StringFilter();
        }
        return creditRiskRatingDesc;
    }

    public void setCreditRiskRatingDesc(StringFilter creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
    }

    public IntegerFilter getCreditScore() {
        return creditScore;
    }

    public IntegerFilter creditScore() {
        if (creditScore == null) {
            creditScore = new IntegerFilter();
        }
        return creditScore;
    }

    public void setCreditScore(IntegerFilter creditScore) {
        this.creditScore = creditScore;
    }

    public InstantFilter getValidUntil() {
        return validUntil;
    }

    public InstantFilter validUntil() {
        if (validUntil == null) {
            validUntil = new InstantFilter();
        }
        return validUntil;
    }

    public void setValidUntil(InstantFilter validUntil) {
        this.validUntil = validUntil;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustCreditProfileCriteria that = (CustCreditProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(custIdTypeOne, that.custIdTypeOne) &&
            Objects.equals(custIdRefOne, that.custIdRefOne) &&
            Objects.equals(custIdTypeTwo, that.custIdTypeTwo) &&
            Objects.equals(custIdRefTwo, that.custIdRefTwo) &&
            Objects.equals(creditCardNumber, that.creditCardNumber) &&
            Objects.equals(creditProfileData, that.creditProfileData) &&
            Objects.equals(creditRiskRating, that.creditRiskRating) &&
            Objects.equals(creditRiskRatingDesc, that.creditRiskRatingDesc) &&
            Objects.equals(creditScore, that.creditScore) &&
            Objects.equals(validUntil, that.validUntil) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            custIdTypeOne,
            custIdRefOne,
            custIdTypeTwo,
            custIdRefTwo,
            creditCardNumber,
            creditProfileData,
            creditRiskRating,
            creditRiskRatingDesc,
            creditScore,
            validUntil,
            customerId,
            customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCreditProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (custIdTypeOne != null ? "custIdTypeOne=" + custIdTypeOne + ", " : "") +
            (custIdRefOne != null ? "custIdRefOne=" + custIdRefOne + ", " : "") +
            (custIdTypeTwo != null ? "custIdTypeTwo=" + custIdTypeTwo + ", " : "") +
            (custIdRefTwo != null ? "custIdRefTwo=" + custIdRefTwo + ", " : "") +
            (creditCardNumber != null ? "creditCardNumber=" + creditCardNumber + ", " : "") +
            (creditProfileData != null ? "creditProfileData=" + creditProfileData + ", " : "") +
            (creditRiskRating != null ? "creditRiskRating=" + creditRiskRating + ", " : "") +
            (creditRiskRatingDesc != null ? "creditRiskRatingDesc=" + creditRiskRatingDesc + ", " : "") +
            (creditScore != null ? "creditScore=" + creditScore + ", " : "") +
            (validUntil != null ? "validUntil=" + validUntil + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
