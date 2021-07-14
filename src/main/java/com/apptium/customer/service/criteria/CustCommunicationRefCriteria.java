package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustCommunicationRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustCommunicationRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-communication-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustCommunicationRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter customerNotificationId;

    private StringFilter role;

    private StringFilter status;

    private LongFilter customerId;

    private LongFilter customerId;

    public CustCommunicationRefCriteria() {}

    public CustCommunicationRefCriteria(CustCommunicationRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.customerNotificationId = other.customerNotificationId == null ? null : other.customerNotificationId.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustCommunicationRefCriteria copy() {
        return new CustCommunicationRefCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCustomerNotificationId() {
        return customerNotificationId;
    }

    public StringFilter customerNotificationId() {
        if (customerNotificationId == null) {
            customerNotificationId = new StringFilter();
        }
        return customerNotificationId;
    }

    public void setCustomerNotificationId(StringFilter customerNotificationId) {
        this.customerNotificationId = customerNotificationId;
    }

    public StringFilter getRole() {
        return role;
    }

    public StringFilter role() {
        if (role == null) {
            role = new StringFilter();
        }
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustCommunicationRefCriteria that = (CustCommunicationRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(customerNotificationId, that.customerNotificationId) &&
            Objects.equals(role, that.role) &&
            Objects.equals(status, that.status) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerNotificationId, role, status, customerId, customerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCommunicationRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (customerNotificationId != null ? "customerNotificationId=" + customerNotificationId + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
