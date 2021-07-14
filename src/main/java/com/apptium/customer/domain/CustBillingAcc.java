package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustBillingAcc.
 */
@Entity
@Table(name = "cust_billing_acc")
public class CustBillingAcc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "href", nullable = false)
    private String href;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "billing_account_number", nullable = false)
    private Long billingAccountNumber;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

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
    @OneToOne(mappedBy = "custBillingAcc")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustBillingAcc id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustBillingAcc name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return this.href;
    }

    public CustBillingAcc href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStatus() {
        return this.status;
    }

    public CustBillingAcc status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public CustBillingAcc description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBillingAccountNumber() {
        return this.billingAccountNumber;
    }

    public CustBillingAcc billingAccountNumber(Long billingAccountNumber) {
        this.billingAccountNumber = billingAccountNumber;
        return this;
    }

    public void setBillingAccountNumber(Long billingAccountNumber) {
        this.billingAccountNumber = billingAccountNumber;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustBillingAcc customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public CustBillingAcc currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustBillingAcc customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCustBillingAcc(null);
        }
        if (customer != null) {
            customer.setCustBillingAcc(this);
        }
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillingAcc)) {
            return false;
        }
        return id != null && id.equals(((CustBillingAcc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingAcc{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", href='" + getHref() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", billingAccountNumber=" + getBillingAccountNumber() +
            ", customerId=" + getCustomerId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            "}";
    }
}
