package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustNewsLetterConfig.
 */
@Entity
@Table(name = "cust_news_letter_config")
public class CustNewsLetterConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "new_letter_type_id", nullable = false)
    private Long newLetterTypeId;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @ManyToOne
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
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "custNewsLetterConfigs", "indNewsLetterConfs" }, allowSetters = true)
    private NewsLetterType newsLetterType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustNewsLetterConfig id(Long id) {
        this.id = id;
        return this;
    }

    public Long getNewLetterTypeId() {
        return this.newLetterTypeId;
    }

    public CustNewsLetterConfig newLetterTypeId(Long newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
        return this;
    }

    public void setNewLetterTypeId(Long newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
    }

    public String getValue() {
        return this.value;
    }

    public CustNewsLetterConfig value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustNewsLetterConfig customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustNewsLetterConfig customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public NewsLetterType getNewsLetterType() {
        return this.newsLetterType;
    }

    public CustNewsLetterConfig newsLetterType(NewsLetterType newsLetterType) {
        this.setNewsLetterType(newsLetterType);
        return this;
    }

    public void setNewsLetterType(NewsLetterType newsLetterType) {
        this.newsLetterType = newsLetterType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustNewsLetterConfig)) {
            return false;
        }
        return id != null && id.equals(((CustNewsLetterConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustNewsLetterConfig{" +
            "id=" + getId() +
            ", newLetterTypeId=" + getNewLetterTypeId() +
            ", value='" + getValue() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
