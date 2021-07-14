package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustBillingRef.
 */
@Entity
@Table(name = "cust_billing_ref")
public class CustBillingRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount_due", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountDue;

    @NotNull
    @Column(name = "bill_date", nullable = false)
    private Instant billDate;

    @NotNull
    @Column(name = "bill_no", nullable = false)
    private Long billNo;

    @NotNull
    @Column(name = "billing_period", nullable = false)
    private Instant billingPeriod;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "href", nullable = false)
    private String href;

    @NotNull
    @Column(name = "last_updated_date", nullable = false)
    private Instant lastUpdatedDate;

    @NotNull
    @Column(name = "next_updated_date", nullable = false)
    private Instant nextUpdatedDate;

    @NotNull
    @Column(name = "payment_due_date", nullable = false)
    private Instant paymentDueDate;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "tax_excluded_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxExcludedAmount;

    @NotNull
    @Column(name = "tax_included_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxIncludedAmount;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToMany(mappedBy = "custBillingRef")
    @JsonIgnoreProperties(value = { "bankCardTypes", "custBillingRef" }, allowSetters = true)
    private Set<CustPaymentMethod> custPaymentMethods = new HashSet<>();

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
    @OneToOne(mappedBy = "custBillingRef")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustBillingRef id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getAmountDue() {
        return this.amountDue;
    }

    public CustBillingRef amountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
        return this;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public Instant getBillDate() {
        return this.billDate;
    }

    public CustBillingRef billDate(Instant billDate) {
        this.billDate = billDate;
        return this;
    }

    public void setBillDate(Instant billDate) {
        this.billDate = billDate;
    }

    public Long getBillNo() {
        return this.billNo;
    }

    public CustBillingRef billNo(Long billNo) {
        this.billNo = billNo;
        return this;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public Instant getBillingPeriod() {
        return this.billingPeriod;
    }

    public CustBillingRef billingPeriod(Instant billingPeriod) {
        this.billingPeriod = billingPeriod;
        return this;
    }

    public void setBillingPeriod(Instant billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getCategory() {
        return this.category;
    }

    public CustBillingRef category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHref() {
        return this.href;
    }

    public CustBillingRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Instant getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public CustBillingRef lastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Instant getNextUpdatedDate() {
        return this.nextUpdatedDate;
    }

    public CustBillingRef nextUpdatedDate(Instant nextUpdatedDate) {
        this.nextUpdatedDate = nextUpdatedDate;
        return this;
    }

    public void setNextUpdatedDate(Instant nextUpdatedDate) {
        this.nextUpdatedDate = nextUpdatedDate;
    }

    public Instant getPaymentDueDate() {
        return this.paymentDueDate;
    }

    public CustBillingRef paymentDueDate(Instant paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
        return this;
    }

    public void setPaymentDueDate(Instant paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getState() {
        return this.state;
    }

    public CustBillingRef state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxExcludedAmount() {
        return this.taxExcludedAmount;
    }

    public CustBillingRef taxExcludedAmount(BigDecimal taxExcludedAmount) {
        this.taxExcludedAmount = taxExcludedAmount;
        return this;
    }

    public void setTaxExcludedAmount(BigDecimal taxExcludedAmount) {
        this.taxExcludedAmount = taxExcludedAmount;
    }

    public BigDecimal getTaxIncludedAmount() {
        return this.taxIncludedAmount;
    }

    public CustBillingRef taxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
        return this;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustBillingRef customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<CustPaymentMethod> getCustPaymentMethods() {
        return this.custPaymentMethods;
    }

    public CustBillingRef custPaymentMethods(Set<CustPaymentMethod> custPaymentMethods) {
        this.setCustPaymentMethods(custPaymentMethods);
        return this;
    }

    public CustBillingRef addCustPaymentMethod(CustPaymentMethod custPaymentMethod) {
        this.custPaymentMethods.add(custPaymentMethod);
        custPaymentMethod.setCustBillingRef(this);
        return this;
    }

    public CustBillingRef removeCustPaymentMethod(CustPaymentMethod custPaymentMethod) {
        this.custPaymentMethods.remove(custPaymentMethod);
        custPaymentMethod.setCustBillingRef(null);
        return this;
    }

    public void setCustPaymentMethods(Set<CustPaymentMethod> custPaymentMethods) {
        if (this.custPaymentMethods != null) {
            this.custPaymentMethods.forEach(i -> i.setCustBillingRef(null));
        }
        if (custPaymentMethods != null) {
            custPaymentMethods.forEach(i -> i.setCustBillingRef(this));
        }
        this.custPaymentMethods = custPaymentMethods;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustBillingRef customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCustBillingRef(null);
        }
        if (customer != null) {
            customer.setCustBillingRef(this);
        }
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillingRef)) {
            return false;
        }
        return id != null && id.equals(((CustBillingRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingRef{" +
            "id=" + getId() +
            ", amountDue=" + getAmountDue() +
            ", billDate='" + getBillDate() + "'" +
            ", billNo=" + getBillNo() +
            ", billingPeriod='" + getBillingPeriod() + "'" +
            ", category='" + getCategory() + "'" +
            ", href='" + getHref() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", nextUpdatedDate='" + getNextUpdatedDate() + "'" +
            ", paymentDueDate='" + getPaymentDueDate() + "'" +
            ", state='" + getState() + "'" +
            ", taxExcludedAmount=" + getTaxExcludedAmount() +
            ", taxIncludedAmount=" + getTaxIncludedAmount() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
