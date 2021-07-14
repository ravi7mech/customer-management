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
 * Criteria class for the {@link com.apptium.customer.domain.CustISVRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustISVRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-isv-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustISVRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter isvId;

    private StringFilter isvName;

    private LongFilter isvCustId;

    private LongFilter customerId;

    private LongFilter custISVCharId;

    private LongFilter customerId;

    public CustISVRefCriteria() {}

    public CustISVRefCriteria(CustISVRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isvId = other.isvId == null ? null : other.isvId.copy();
        this.isvName = other.isvName == null ? null : other.isvName.copy();
        this.isvCustId = other.isvCustId == null ? null : other.isvCustId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.custISVCharId = other.custISVCharId == null ? null : other.custISVCharId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustISVRefCriteria copy() {
        return new CustISVRefCriteria(this);
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

    public IntegerFilter getIsvId() {
        return isvId;
    }

    public IntegerFilter isvId() {
        if (isvId == null) {
            isvId = new IntegerFilter();
        }
        return isvId;
    }

    public void setIsvId(IntegerFilter isvId) {
        this.isvId = isvId;
    }

    public StringFilter getIsvName() {
        return isvName;
    }

    public StringFilter isvName() {
        if (isvName == null) {
            isvName = new StringFilter();
        }
        return isvName;
    }

    public void setIsvName(StringFilter isvName) {
        this.isvName = isvName;
    }

    public LongFilter getIsvCustId() {
        return isvCustId;
    }

    public LongFilter isvCustId() {
        if (isvCustId == null) {
            isvCustId = new LongFilter();
        }
        return isvCustId;
    }

    public void setIsvCustId(LongFilter isvCustId) {
        this.isvCustId = isvCustId;
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

    public LongFilter getCustISVCharId() {
        return custISVCharId;
    }

    public LongFilter custISVCharId() {
        if (custISVCharId == null) {
            custISVCharId = new LongFilter();
        }
        return custISVCharId;
    }

    public void setCustISVCharId(LongFilter custISVCharId) {
        this.custISVCharId = custISVCharId;
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
        final CustISVRefCriteria that = (CustISVRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isvId, that.isvId) &&
            Objects.equals(isvName, that.isvName) &&
            Objects.equals(isvCustId, that.isvCustId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(custISVCharId, that.custISVCharId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isvId, isvName, isvCustId, customerId, custISVCharId, customerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isvId != null ? "isvId=" + isvId + ", " : "") +
            (isvName != null ? "isvName=" + isvName + ", " : "") +
            (isvCustId != null ? "isvCustId=" + isvCustId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (custISVCharId != null ? "custISVCharId=" + custISVCharId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
