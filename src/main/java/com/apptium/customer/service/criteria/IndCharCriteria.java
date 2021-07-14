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
 * Criteria class for the {@link com.apptium.customer.domain.IndChar} entity. This class is used
 * in {@link com.apptium.customer.web.rest.IndCharResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ind-chars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndCharCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter value;

    private StringFilter valueType;

    private LongFilter individualId;

    private LongFilter individualId;

    public IndCharCriteria() {}

    public IndCharCriteria(IndCharCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.valueType = other.valueType == null ? null : other.valueType.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
    }

    @Override
    public IndCharCriteria copy() {
        return new IndCharCriteria(this);
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

    public BooleanFilter getValue() {
        return value;
    }

    public BooleanFilter value() {
        if (value == null) {
            value = new BooleanFilter();
        }
        return value;
    }

    public void setValue(BooleanFilter value) {
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

    public LongFilter getIndividualId() {
        return individualId;
    }

    public LongFilter individualId() {
        if (individualId == null) {
            individualId = new LongFilter();
        }
        return individualId;
    }

    public void setIndividualId(LongFilter individualId) {
        this.individualId = individualId;
    }

    public LongFilter getIndividualId() {
        return individualId;
    }

    public LongFilter individualId() {
        if (individualId == null) {
            individualId = new LongFilter();
        }
        return individualId;
    }

    public void setIndividualId(LongFilter individualId) {
        this.individualId = individualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IndCharCriteria that = (IndCharCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(valueType, that.valueType) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(individualId, that.individualId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, valueType, individualId, individualId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndCharCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (valueType != null ? "valueType=" + valueType + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            "}";
    }
}
