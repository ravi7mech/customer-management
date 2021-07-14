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
 * Criteria class for the {@link com.apptium.customer.domain.CustISVChar} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustISVCharResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-isv-chars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustISVCharCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter value;

    private StringFilter valueType;

    private IntegerFilter custIsvId;

    private LongFilter custISVRefId;

    public CustISVCharCriteria() {}

    public CustISVCharCriteria(CustISVCharCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.valueType = other.valueType == null ? null : other.valueType.copy();
        this.custIsvId = other.custIsvId == null ? null : other.custIsvId.copy();
        this.custISVRefId = other.custISVRefId == null ? null : other.custISVRefId.copy();
    }

    @Override
    public CustISVCharCriteria copy() {
        return new CustISVCharCriteria(this);
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

    public IntegerFilter getValue() {
        return value;
    }

    public IntegerFilter value() {
        if (value == null) {
            value = new IntegerFilter();
        }
        return value;
    }

    public void setValue(IntegerFilter value) {
        this.value = value;
    }

    public StringFilter getValueType() {
        return valueType;
    }

    public StringFilter valueType() {
        if (valueType == null) {
            valueType = new StringFilter();
        }
        return valueType;
    }

    public void setValueType(StringFilter valueType) {
        this.valueType = valueType;
    }

    public IntegerFilter getCustIsvId() {
        return custIsvId;
    }

    public IntegerFilter custIsvId() {
        if (custIsvId == null) {
            custIsvId = new IntegerFilter();
        }
        return custIsvId;
    }

    public void setCustIsvId(IntegerFilter custIsvId) {
        this.custIsvId = custIsvId;
    }

    public LongFilter getCustISVRefId() {
        return custISVRefId;
    }

    public LongFilter custISVRefId() {
        if (custISVRefId == null) {
            custISVRefId = new LongFilter();
        }
        return custISVRefId;
    }

    public void setCustISVRefId(LongFilter custISVRefId) {
        this.custISVRefId = custISVRefId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustISVCharCriteria that = (CustISVCharCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(valueType, that.valueType) &&
            Objects.equals(custIsvId, that.custIsvId) &&
            Objects.equals(custISVRefId, that.custISVRefId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, valueType, custIsvId, custISVRefId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVCharCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (valueType != null ? "valueType=" + valueType + ", " : "") +
            (custIsvId != null ? "custIsvId=" + custIsvId + ", " : "") +
            (custISVRefId != null ? "custISVRefId=" + custISVRefId + ", " : "") +
            "}";
    }
}
