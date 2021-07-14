package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustPaymentMethod.
 */
@Entity
@Table(name = "cust_payment_method")
public class CustPaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "preferred", nullable = false)
    private Boolean preferred;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "authorization_code", nullable = false)
    private Integer authorizationCode;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @NotNull
    @Column(name = "details", nullable = false)
    private String details;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToMany(mappedBy = "custPaymentMethod")
    @JsonIgnoreProperties(value = { "custPaymentMethod" }, allowSetters = true)
    private Set<BankCardType> bankCardTypes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "custPaymentMethods", "customer" }, allowSetters = true)
    private CustBillingRef custBillingRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustPaymentMethod id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustPaymentMethod name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CustPaymentMethod description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPreferred() {
        return this.preferred;
    }

    public CustPaymentMethod preferred(Boolean preferred) {
        this.preferred = preferred;
        return this;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public String getType() {
        return this.type;
    }

    public CustPaymentMethod type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAuthorizationCode() {
        return this.authorizationCode;
    }

    public CustPaymentMethod authorizationCode(Integer authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public void setAuthorizationCode(Integer authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getStatus() {
        return this.status;
    }

    public CustPaymentMethod status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStatusDate() {
        return this.statusDate;
    }

    public CustPaymentMethod statusDate(Instant statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getDetails() {
        return this.details;
    }

    public CustPaymentMethod details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustPaymentMethod customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<BankCardType> getBankCardTypes() {
        return this.bankCardTypes;
    }

    public CustPaymentMethod bankCardTypes(Set<BankCardType> bankCardTypes) {
        this.setBankCardTypes(bankCardTypes);
        return this;
    }

    public CustPaymentMethod addBankCardType(BankCardType bankCardType) {
        this.bankCardTypes.add(bankCardType);
        bankCardType.setCustPaymentMethod(this);
        return this;
    }

    public CustPaymentMethod removeBankCardType(BankCardType bankCardType) {
        this.bankCardTypes.remove(bankCardType);
        bankCardType.setCustPaymentMethod(null);
        return this;
    }

    public void setBankCardTypes(Set<BankCardType> bankCardTypes) {
        if (this.bankCardTypes != null) {
            this.bankCardTypes.forEach(i -> i.setCustPaymentMethod(null));
        }
        if (bankCardTypes != null) {
            bankCardTypes.forEach(i -> i.setCustPaymentMethod(this));
        }
        this.bankCardTypes = bankCardTypes;
    }

    public CustBillingRef getCustBillingRef() {
        return this.custBillingRef;
    }

    public CustPaymentMethod custBillingRef(CustBillingRef custBillingRef) {
        this.setCustBillingRef(custBillingRef);
        return this;
    }

    public void setCustBillingRef(CustBillingRef custBillingRef) {
        this.custBillingRef = custBillingRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustPaymentMethod)) {
            return false;
        }
        return id != null && id.equals(((CustPaymentMethod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustPaymentMethod{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", preferred='" + getPreferred() + "'" +
            ", type='" + getType() + "'" +
            ", authorizationCode=" + getAuthorizationCode() +
            ", status='" + getStatus() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
