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
 * Criteria class for the {@link com.apptium.customer.domain.CustBillingAcc} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustBillingAccResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-billing-accs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustBillingAccCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter href;

    private StringFilter status;

    private StringFilter description;

    private LongFilter billingAccountNumber;

    private LongFilter customerId;

    private StringFilter currencyCode;

    private LongFilter customerId;

    public CustBillingAccCriteria() {}

    public CustBillingAccCriteria(CustBillingAccCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.billingAccountNumber = other.billingAccountNumber == null ? null : other.billingAccountNumber.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustBillingAccCriteria copy() {
        return new CustBillingAccCriteria(this);
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

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
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

    public LongFilter getBillingAccountNumber() {
        return billingAccountNumber;
    }

    public LongFilter billingAccountNumber() {
        if (billingAccountNumber == null) {
            billingAccountNumber = new LongFilter();
        }
        return billingAccountNumber;
    }

    public void setBillingAccountNumber(LongFilter billingAccountNumber) {
        this.billingAccountNumber = billingAccountNumber;
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

    public StringFilter getCurrencyCode() {
        return currencyCode;
    }

    public StringFilter currencyCode() {
        if (currencyCode == null) {
            currencyCode = new StringFilter();
        }
        return currencyCode;
    }

    public void setCurrencyCode(StringFilter currencyCode) {
        this.currencyCode = currencyCode;
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
        final CustBillingAccCriteria that = (CustBillingAccCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(href, that.href) &&
            Objects.equals(status, that.status) &&
            Objects.equals(description, that.description) &&
            Objects.equals(billingAccountNumber, that.billingAccountNumber) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, href, status, description, billingAccountNumber, customerId, currencyCode, customerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingAccCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (billingAccountNumber != null ? "billingAccountNumber=" + billingAccountNumber + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
