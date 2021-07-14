package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GeographicSiteRef.
 */
@Entity
@Table(name = "geographic_site_ref")
public class GeographicSiteRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "site_ref", nullable = false)
    private String siteRef;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "cust_con_id", nullable = false)
    private Long custConId;

    @JsonIgnoreProperties(value = { "geographicSiteRef", "custContactChars", "customer" }, allowSetters = true)
    @OneToOne(mappedBy = "geographicSiteRef")
    private CustContact custContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeographicSiteRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getSiteRef() {
        return this.siteRef;
    }

    public GeographicSiteRef siteRef(String siteRef) {
        this.siteRef = siteRef;
        return this;
    }

    public void setSiteRef(String siteRef) {
        this.siteRef = siteRef;
    }

    public String getLocation() {
        return this.location;
    }

    public GeographicSiteRef location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCustConId() {
        return this.custConId;
    }

    public GeographicSiteRef custConId(Long custConId) {
        this.custConId = custConId;
        return this;
    }

    public void setCustConId(Long custConId) {
        this.custConId = custConId;
    }

    public CustContact getCustContact() {
        return this.custContact;
    }

    public GeographicSiteRef custContact(CustContact custContact) {
        this.setCustContact(custContact);
        return this;
    }

    public void setCustContact(CustContact custContact) {
        if (this.custContact != null) {
            this.custContact.setGeographicSiteRef(null);
        }
        if (custContact != null) {
            custContact.setGeographicSiteRef(this);
        }
        this.custContact = custContact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeographicSiteRef)) {
            return false;
        }
        return id != null && id.equals(((GeographicSiteRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicSiteRef{" +
            "id=" + getId() +
            ", siteRef='" + getSiteRef() + "'" +
            ", location='" + getLocation() + "'" +
            ", custConId=" + getCustConId() +
            "}";
    }
}
