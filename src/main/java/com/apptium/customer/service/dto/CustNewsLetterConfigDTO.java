package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustNewsLetterConfig} entity.
 */
public class CustNewsLetterConfigDTO implements Serializable {

    private Long id;

    @NotNull
    private Long newLetterTypeId;

    @NotNull
    private String value;

    @NotNull
    private Long customerId;

    private CustomerDTO customer;

    private NewsLetterTypeDTO newsLetterType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewLetterTypeId() {
        return newLetterTypeId;
    }

    public void setNewLetterTypeId(Long newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public NewsLetterTypeDTO getNewsLetterType() {
        return newsLetterType;
    }

    public void setNewsLetterType(NewsLetterTypeDTO newsLetterType) {
        this.newsLetterType = newsLetterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustNewsLetterConfigDTO)) {
            return false;
        }

        CustNewsLetterConfigDTO custNewsLetterConfigDTO = (CustNewsLetterConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custNewsLetterConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustNewsLetterConfigDTO{" +
            "id=" + getId() +
            ", newLetterTypeId=" + getNewLetterTypeId() +
            ", value='" + getValue() + "'" +
            ", customerId=" + getCustomerId() +
            ", customer=" + getCustomer() +
            ", newsLetterType=" + getNewsLetterType() +
            "}";
    }
}
