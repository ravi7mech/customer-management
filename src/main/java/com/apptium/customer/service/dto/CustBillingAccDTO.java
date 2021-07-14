package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustBillingAcc} entity.
 */
public class CustBillingAccDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String href;

    @NotNull
    private String status;

    private String description;

    @NotNull
    private Long billingAccountNumber;

    @NotNull
    private Long customerId;

    @NotNull
    private String currencyCode;

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBillingAccountNumber() {
        return billingAccountNumber;
    }

    public void setBillingAccountNumber(Long billingAccountNumber) {
        this.billingAccountNumber = billingAccountNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillingAccDTO)) {
            return false;
        }

        CustBillingAccDTO custBillingAccDTO = (CustBillingAccDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custBillingAccDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingAccDTO{" +
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
