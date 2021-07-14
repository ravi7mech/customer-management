package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CustCommunicationRef.
 */
@Entity
@Table(name = "cust_communication_ref")
public class CustCommunicationRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "customer_notification_id", nullable = false)
    private String customerNotificationId;

    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

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

    public CustCommunicationRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getCustomerNotificationId() {
        return this.customerNotificationId;
    }

    public CustCommunicationRef customerNotificationId(String customerNotificationId) {
        this.customerNotificationId = customerNotificationId;
        return this;
    }

    public void setCustomerNotificationId(String customerNotificationId) {
        this.customerNotificationId = customerNotificationId;
    }

    public String getRole() {
        return this.role;
    }

    public CustCommunicationRef role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return this.status;
    }

    public CustCommunicationRef status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public CustCommunicationRef customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustCommunicationRef customer(Customer customer) {
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
        if (!(o instanceof CustCommunicationRef)) {
            return false;
        }
        return id != null && id.equals(((CustCommunicationRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCommunicationRef{" +
            "id=" + getId() +
            ", customerNotificationId='" + getCustomerNotificationId() + "'" +
            ", role='" + getRole() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
