package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustStatistics} entity.
 */
public class CustStatisticsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String value;

    @NotNull
    private String valuetype;

    @NotNull
    private Long customerId;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustStatisticsDTO)) {
            return false;
        }

        CustStatisticsDTO custStatisticsDTO = (CustStatisticsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custStatisticsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustStatisticsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valuetype='" + getValuetype() + "'" +
            ", customerId=" + getCustomerId() +
            ", customer=" + getCustomer() +
            "}";
    }
}
