package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustPaymentMethod} entity.
 */
public class CustPaymentMethodDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Boolean preferred;

    @NotNull
    private String type;

    @NotNull
    private Integer authorizationCode;

    @NotNull
    private String status;

    @NotNull
    private Instant statusDate;

    @NotNull
    private String details;

    @NotNull
    private Long customerId;

    private CustBillingRefDTO custBillingRef;

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

    public Integer getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(Integer authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustBillingRefDTO getCustBillingRef() {
        return custBillingRef;
    }

    public void setCustBillingRef(CustBillingRefDTO custBillingRef) {
        this.custBillingRef = custBillingRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustPaymentMethodDTO)) {
            return false;
        }

        CustPaymentMethodDTO custPaymentMethodDTO = (CustPaymentMethodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custPaymentMethodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustPaymentMethodDTO{" +
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
            ", custBillingRef=" + getCustBillingRef() +
            "}";
    }
}
