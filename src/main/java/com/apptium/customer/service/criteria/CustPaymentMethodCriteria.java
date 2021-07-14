package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustPaymentMethod} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustPaymentMethodResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-payment-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustPaymentMethodCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter preferred;

    private StringFilter type;

    private IntegerFilter authorizationCode;

    private StringFilter status;

    private InstantFilter statusDate;

    private StringFilter details;

    private LongFilter customerId;

    private LongFilter bankCardTypeId;

    private LongFilter custBillingRefId;

    public CustPaymentMethodCriteria() {}

    public CustPaymentMethodCriteria(CustPaymentMethodCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.preferred = other.preferred == null ? null : other.preferred.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.authorizationCode = other.authorizationCode == null ? null : other.authorizationCode.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.bankCardTypeId = other.bankCardTypeId == null ? null : other.bankCardTypeId.copy();
        this.custBillingRefId = other.custBillingRefId == null ? null : other.custBillingRefId.copy();
    }

    @Override
    public CustPaymentMethodCriteria copy() {
        return new CustPaymentMethodCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getPreferred() {
        return preferred;
    }

    public BooleanFilter preferred() {
        if (preferred == null) {
            preferred = new BooleanFilter();
        }
        return preferred;
    }

    public void setPreferred(BooleanFilter preferred) {
        this.preferred = preferred;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getAuthorizationCode() {
        return authorizationCode;
    }

    public IntegerFilter authorizationCode() {
        if (authorizationCode == null) {
            authorizationCode = new IntegerFilter();
        }
        return authorizationCode;
    }

    public void setAuthorizationCode(IntegerFilter authorizationCode) {
        this.authorizationCode = authorizationCode;
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

    public InstantFilter getStatusDate() {
        return statusDate;
    }

    public InstantFilter statusDate() {
        if (statusDate == null) {
            statusDate = new InstantFilter();
        }
        return statusDate;
    }

    public void setStatusDate(InstantFilter statusDate) {
        this.statusDate = statusDate;
    }

    public StringFilter getDetails() {
        return details;
    }

    public StringFilter details() {
        if (details == null) {
            details = new StringFilter();
        }
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
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

    public LongFilter getBankCardTypeId() {
        return bankCardTypeId;
    }

    public LongFilter bankCardTypeId() {
        if (bankCardTypeId == null) {
            bankCardTypeId = new LongFilter();
        }
        return bankCardTypeId;
    }

    public void setBankCardTypeId(LongFilter bankCardTypeId) {
        this.bankCardTypeId = bankCardTypeId;
    }

    public LongFilter getCustBillingRefId() {
        return custBillingRefId;
    }

    public LongFilter custBillingRefId() {
        if (custBillingRefId == null) {
            custBillingRefId = new LongFilter();
        }
        return custBillingRefId;
    }

    public void setCustBillingRefId(LongFilter custBillingRefId) {
        this.custBillingRefId = custBillingRefId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustPaymentMethodCriteria that = (CustPaymentMethodCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(preferred, that.preferred) &&
            Objects.equals(type, that.type) &&
            Objects.equals(authorizationCode, that.authorizationCode) &&
            Objects.equals(status, that.status) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(details, that.details) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(bankCardTypeId, that.bankCardTypeId) &&
            Objects.equals(custBillingRefId, that.custBillingRefId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            preferred,
            type,
            authorizationCode,
            status,
            statusDate,
            details,
            customerId,
            bankCardTypeId,
            custBillingRefId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustPaymentMethodCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (preferred != null ? "preferred=" + preferred + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (authorizationCode != null ? "authorizationCode=" + authorizationCode + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (bankCardTypeId != null ? "bankCardTypeId=" + bankCardTypeId + ", " : "") +
            (custBillingRefId != null ? "custBillingRefId=" + custBillingRefId + ", " : "") +
            "}";
    }
}
