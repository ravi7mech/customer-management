package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustCreditProfile.
 */
@Entity
@Table(name = "cust_credit_profile")
public class CustCreditProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cust_id_type_one", nullable = false)
    private String custIdTypeOne;

    @NotNull
    @Column(name = "cust_id_ref_one", nullable = false)
    private String custIdRefOne;

    @NotNull
    @Column(name = "cust_id_type_two", nullable = false)
    private String custIdTypeTwo;

    @NotNull
    @Column(name = "cust_id_ref_two", nullable = false)
    private String custIdRefTwo;

    @NotNull
    @Column(name = "credit_card_number", nullable = false)
    private Long creditCardNumber;

    @NotNull
    @Column(name = "credit_profile_data", nullable = false)
    private Instant creditProfileData;

    @NotNull
    @Column(name = "credit_risk_rating", nullable = false)
    private String creditRiskRating;

    @Column(name = "credit_risk_rating_desc")
    private String creditRiskRatingDesc;

    @NotNull
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private Instant validUntil;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @JsonIgnoreProperties(
        value = {
            "custBillingAcc",
            "custCreditProfile",
            "custBillingRef",
            "custContacts",
            "custStatistics",
            "custChars",
            "custCommunicationRefs",
            "custPasswordChars",
            "custNewsLetterConfigs",
            "custSecurityChars",
            "custRelParties",
            "custISVRefs",
            "shoppingSessionRefs",
            "industry",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "custCreditProfile")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustCreditProfile id(Long id) {
        this.id = id;
        return this;
    }

    public String getCustIdTypeOne() {
        return this.custIdTypeOne;
    }

    public CustCreditProfile custIdTypeOne(String custIdTypeOne) {
        this.custIdTypeOne = custIdTypeOne;
        return this;
    }

    public void setCustIdTypeOne(String custIdTypeOne) {
        this.custIdTypeOne = custIdTypeOne;
    }

    public String getCustIdRefOne() {
        return this.custIdRefOne;
    }

    public CustCreditProfile custIdRefOne(String custIdRefOne) {
        this.custIdRefOne = custIdRefOne;
        return this;
    }

    public void setCustIdRefOne(String custIdRefOne) {
        this.custIdRefOne = custIdRefOne;
    }

    public String getCustIdTypeTwo() {
        return this.custIdTypeTwo;
    }

    public CustCreditProfile custIdTypeTwo(String custIdTypeTwo) {
        this.custIdTypeTwo = custIdTypeTwo;
        return this;
    }

    public void setCustIdTypeTwo(String custIdTypeTwo) {
        this.custIdTypeTwo = custIdTypeTwo;
    }

    public String getCustIdRefTwo() {
        return this.custIdRefTwo;
    }

    public CustCreditProfile custIdRefTwo(String custIdRefTwo) {
        this.custIdRefTwo = custIdRefTwo;
        return this;
    }

    public void setCustIdRefTwo(String custIdRefTwo) {
        this.custIdRefTwo = custIdRefTwo;
    }

    public Long getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public CustCreditProfile creditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Instant getCreditProfileData() {
        return this.creditProfileData;
    }

    public CustCreditProfile creditProfileData(Instant creditProfileData) {
        this.creditProfileData = creditProfileData;
        return this;
    }

    public void setCreditProfileData(Instant creditProfileData) {
        this.creditProfileData = creditProfileData;
    }

    public String getCreditRiskRating() {
        return this.creditRiskRating;
    }

    public CustCreditProfile creditRiskRating(String creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
        return this;
    }

    public void setCreditRiskRating(String creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
    }

    public String getCreditRiskRatingDesc() {
        return this.creditRiskRatingDesc;
    }

    public CustCreditProfile creditRiskRatingDesc(String creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
        return this;
    }

    public void setCreditRiskRatingDesc(String creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
    }

    public Integer getCreditScore() {
        return this.creditScore;
    }

    public CustCreditProfile creditScore(Integer creditScore) {
        this.creditScore = creditScore;
        return this;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Instant getValidUntil() {
        return this.validUntil;
    }

    public CustCreditProfile validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustCreditProfile customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustCreditProfile customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCustCreditProfile(null);
        }
        if (customer != null) {
            customer.setCustCreditProfile(this);
        }
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustCreditProfile)) {
            return false;
        }
        return id != null && id.equals(((CustCreditProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCreditProfile{" +
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
