package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustCommunicationRef} entity.
 */
public class CustCommunicationRefDTO implements Serializable {

    private Long id;

    @NotNull
    private String customerNotificationId;

    @NotNull
    private String role;

    private String status;

    @NotNull
    private Long customerId;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNotificationId() {
        return customerNotificationId;
    }

    public void setCustomerNotificationId(String customerNotificationId) {
        this.customerNotificationId = customerNotificationId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof CustCommunicationRefDTO)) {
            return false;
        }

        CustCommunicationRefDTO custCommunicationRefDTO = (CustCommunicationRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custCommunicationRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCommunicationRefDTO{" +
            "id=" + getId() +
            ", customerNotificationId='" + getCustomerNotificationId() + "'" +
            ", role='" + getRole() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerId=" + getCustomerId() +
            ", customer=" + getCustomer() +
            "}";
    }
}
