package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustContact} entity.
 */
public class CustContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Boolean preferred;

    @NotNull
    private String type;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;

    @NotNull
    private Long customerId;

    private GeographicSiteRefDTO geographicSiteRef;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public GeographicSiteRefDTO getGeographicSiteRef() {
        return geographicSiteRef;
    }

    public void setGeographicSiteRef(GeographicSiteRefDTO geographicSiteRef) {
        this.geographicSiteRef = geographicSiteRef;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustContactDTO)) {
            return false;
        }

        CustContactDTO custContactDTO = (CustContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", preferred='" + getPreferred() + "'" +
            ", type='" + getType() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", customerId=" + getCustomerId() +
            ", geographicSiteRef=" + getGeographicSiteRef() +
            ", customer=" + getCustomer() +
            "}";
    }
}
