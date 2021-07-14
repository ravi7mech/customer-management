package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustISVRef.
 */
@Entity
@Table(name = "cust_isv_ref")
public class CustISVRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "isv_id", nullable = false)
    private Integer isvId;

    @NotNull
    @Column(name = "isv_name", nullable = false)
    private String isvName;

    @NotNull
    @Column(name = "isv_cust_id", nullable = false)
    private Long isvCustId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToMany(mappedBy = "custISVRef")
    @JsonIgnoreProperties(value = { "custISVRef" }, allowSetters = true)
    private Set<CustISVChar> custISVChars = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustISVRef id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getIsvId() {
        return this.isvId;
    }

    public CustISVRef isvId(Integer isvId) {
        this.isvId = isvId;
        return this;
    }

    public void setIsvId(Integer isvId) {
        this.isvId = isvId;
    }

    public String getIsvName() {
        return this.isvName;
    }

    public CustISVRef isvName(String isvName) {
        this.isvName = isvName;
        return this;
    }

    public void setIsvName(String isvName) {
        this.isvName = isvName;
    }

    public Long getIsvCustId() {
        return this.isvCustId;
    }

    public CustISVRef isvCustId(Long isvCustId) {
        this.isvCustId = isvCustId;
        return this;
    }

    public void setIsvCustId(Long isvCustId) {
        this.isvCustId = isvCustId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustISVRef customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<CustISVChar> getCustISVChars() {
        return this.custISVChars;
    }

    public CustISVRef custISVChars(Set<CustISVChar> custISVChars) {
        this.setCustISVChars(custISVChars);
        return this;
    }

    public CustISVRef addCustISVChar(CustISVChar custISVChar) {
        this.custISVChars.add(custISVChar);
        custISVChar.setCustISVRef(this);
        return this;
    }

    public CustISVRef removeCustISVChar(CustISVChar custISVChar) {
        this.custISVChars.remove(custISVChar);
        custISVChar.setCustISVRef(null);
        return this;
    }

    public void setCustISVChars(Set<CustISVChar> custISVChars) {
        if (this.custISVChars != null) {
            this.custISVChars.forEach(i -> i.setCustISVRef(null));
        }
        if (custISVChars != null) {
            custISVChars.forEach(i -> i.setCustISVRef(this));
        }
        this.custISVChars = custISVChars;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustISVRef customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustISVRef)) {
            return false;
        }
        return id != null && id.equals(((CustISVRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVRef{" +
            "id=" + getId() +
            ", isvId=" + getIsvId() +
            ", isvName='" + getIsvName() + "'" +
            ", isvCustId=" + getIsvCustId() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
