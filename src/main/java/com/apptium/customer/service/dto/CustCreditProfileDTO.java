package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustCreditProfile} entity.
 */
public class CustCreditProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String custIdTypeOne;

    @NotNull
    private String custIdRefOne;

    @NotNull
    private String custIdTypeTwo;

    @NotNull
    private String custIdRefTwo;

    @NotNull
    private Long creditCardNumber;

    @NotNull
    private Instant creditProfileData;

    @NotNull
    private String creditRiskRating;

    private String creditRiskRatingDesc;

    @NotNull
    private Integer creditScore;

    @NotNull
    private Instant validUntil;

    @NotNull
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustIdTypeOne() {
        return custIdTypeOne;
    }

    public void setCustIdTypeOne(String custIdTypeOne) {
        this.custIdTypeOne = custIdTypeOne;
    }

    public String getCustIdRefOne() {
        return custIdRefOne;
    }

    public void setCustIdRefOne(String custIdRefOne) {
        this.custIdRefOne = custIdRefOne;
    }

    public String getCustIdTypeTwo() {
        return custIdTypeTwo;
    }

    public void setCustIdTypeTwo(String custIdTypeTwo) {
        this.custIdTypeTwo = custIdTypeTwo;
    }

    public String getCustIdRefTwo() {
        return custIdRefTwo;
    }

    public void setCustIdRefTwo(String custIdRefTwo) {
        this.custIdRefTwo = custIdRefTwo;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Instant getCreditProfileData() {
        return creditProfileData;
    }

    public void setCreditProfileData(Instant creditProfileData) {
        this.creditProfileData = creditProfileData;
    }

    public String getCreditRiskRating() {
        return creditRiskRating;
    }

    public void setCreditRiskRating(String creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
    }

    public String getCreditRiskRatingDesc() {
        return creditRiskRatingDesc;
    }

    public void setCreditRiskRatingDesc(String creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustCreditProfileDTO)) {
            return false;
        }

        CustCreditProfileDTO custCreditProfileDTO = (CustCreditProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custCreditProfileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCreditProfileDTO{" +
            "id=" + getId() +
            ", custIdTypeOne='" + getCustIdTypeOne() + "'" +
            ", custIdRefOne='" + getCustIdRefOne() + "'" +
            ", custIdTypeTwo='" + getCustIdTypeTwo() + "'" +
            ", custIdRefTwo='" + getCustIdRefTwo() + "'" +
            ", creditCardNumber=" + getCreditCardNumber() +
            ", creditProfileData='" + getCreditProfileData() + "'" +
            ", creditRiskRating='" + getCreditRiskRating() + "'" +
            ", creditRiskRatingDesc='" + getCreditRiskRatingDesc() + "'" +
            ", creditScore=" + getCreditScore() +
            ", validUntil='" + getValidUntil() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
