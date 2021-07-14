package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustContact.
 */
@Entity
@Table(name = "cust_contact")
public class CustContact implements Serializable {

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
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @JsonIgnoreProperties(value = { "custContact" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private GeographicSiteRef geographicSiteRef;

    @OneToMany(mappedBy = "custContact")
    @JsonIgnoreProperties(value = { "custContact" }, allowSetters = true)
    private Set<CustContactChar> custContactChars = new HashSet<>();

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

    public CustContact id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CustContact description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPreferred() {
        return this.preferred;
    }

    public CustContact preferred(Boolean preferred) {
        this.preferred = preferred;
        return this;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public String getType() {
        return this.type;
    }

    public CustContact type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public CustContact validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return this.validTo;
    }

    public CustContact validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustContact customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public GeographicSiteRef getGeographicSiteRef() {
        return this.geographicSiteRef;
    }

    public CustContact geographicSiteRef(GeographicSiteRef geographicSiteRef) {
        this.setGeographicSiteRef(geographicSiteRef);
        return this;
    }

    public void setGeographicSiteRef(GeographicSiteRef geographicSiteRef) {
        this.geographicSiteRef = geographicSiteRef;
    }

    public Set<CustContactChar> getCustContactChars() {
        return this.custContactChars;
    }

    public CustContact custContactChars(Set<CustContactChar> custContactChars) {
        this.setCustContactChars(custContactChars);
        return this;
    }

    public CustContact addCustContactChar(CustContactChar custContactChar) {
        this.custContactChars.add(custContactChar);
        custContactChar.setCustContact(this);
        return this;
    }

    public CustContact removeCustContactChar(CustContactChar custContactChar) {
        this.custContactChars.remove(custContactChar);
        custContactChar.setCustContact(null);
        return this;
    }

    public void setCustContactChars(Set<CustContactChar> custContactChars) {
        if (this.custContactChars != null) {
            this.custContactChars.forEach(i -> i.setCustContact(null));
        }
        if (custContactChars != null) {
            custContactChars.forEach(i -> i.setCustContact(this));
        }
        this.custContactChars = custContactChars;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustContact customer(Customer customer) {
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
        if (!(o instanceof CustContact)) {
            return false;
        }
        return id != null && id.equals(((CustContact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustContact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", preferred='" + getPreferred() + "'" +
            ", type='" + getType() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
