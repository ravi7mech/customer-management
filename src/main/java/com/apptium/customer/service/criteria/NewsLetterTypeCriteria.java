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
 * Criteria class for the {@link com.apptium.customer.domain.NewsLetterType} entity. This class is used
 * in {@link com.apptium.customer.web.rest.NewsLetterTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /news-letter-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NewsLetterTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter newLetterType;

    private StringFilter displayValue;

    private StringFilter description;

    private StringFilter status;

    private LongFilter custNewsLetterConfigId;

    private LongFilter indNewsLetterConfId;

    public NewsLetterTypeCriteria() {}

    public NewsLetterTypeCriteria(NewsLetterTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.newLetterType = other.newLetterType == null ? null : other.newLetterType.copy();
        this.displayValue = other.displayValue == null ? null : other.displayValue.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.custNewsLetterConfigId = other.custNewsLetterConfigId == null ? null : other.custNewsLetterConfigId.copy();
        this.indNewsLetterConfId = other.indNewsLetterConfId == null ? null : other.indNewsLetterConfId.copy();
    }

    @Override
    public NewsLetterTypeCriteria copy() {
        return new NewsLetterTypeCriteria(this);
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

    public StringFilter getNewLetterType() {
        return newLetterType;
    }

    public StringFilter newLetterType() {
        if (newLetterType == null) {
            newLetterType = new StringFilter();
        }
        return newLetterType;
    }

    public void setNewLetterType(StringFilter newLetterType) {
        this.newLetterType = newLetterType;
    }

    public StringFilter getDisplayValue() {
        return displayValue;
    }

    public StringFilter displayValue() {
        if (displayValue == null) {
            displayValue = new StringFilter();
        }
        return displayValue;
    }

    public void setDisplayValue(StringFilter displayValue) {
        this.displayValue = displayValue;
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

    public LongFilter getCustNewsLetterConfigId() {
        return custNewsLetterConfigId;
    }

    public LongFilter custNewsLetterConfigId() {
        if (custNewsLetterConfigId == null) {
            custNewsLetterConfigId = new LongFilter();
        }
        return custNewsLetterConfigId;
    }

    public void setCustNewsLetterConfigId(LongFilter custNewsLetterConfigId) {
        this.custNewsLetterConfigId = custNewsLetterConfigId;
    }

    public LongFilter getIndNewsLetterConfId() {
        return indNewsLetterConfId;
    }

    public LongFilter indNewsLetterConfId() {
        if (indNewsLetterConfId == null) {
            indNewsLetterConfId = new LongFilter();
        }
        return indNewsLetterConfId;
    }

    public void setIndNewsLetterConfId(LongFilter indNewsLetterConfId) {
        this.indNewsLetterConfId = indNewsLetterConfId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NewsLetterTypeCriteria that = (NewsLetterTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(newLetterType, that.newLetterType) &&
            Objects.equals(displayValue, that.displayValue) &&
            Objects.equals(description, that.description) &&
            Objects.equals(status, that.status) &&
            Objects.equals(custNewsLetterConfigId, that.custNewsLetterConfigId) &&
            Objects.equals(indNewsLetterConfId, that.indNewsLetterConfId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newLetterType, displayValue, description, status, custNewsLetterConfigId, indNewsLetterConfId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsLetterTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (newLetterType != null ? "newLetterType=" + newLetterType + ", " : "") +
            (displayValue != null ? "displayValue=" + displayValue + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (custNewsLetterConfigId != null ? "custNewsLetterConfigId=" + custNewsLetterConfigId + ", " : "") +
            (indNewsLetterConfId != null ? "indNewsLetterConfId=" + indNewsLetterConfId + ", " : "") +
            "}";
    }
}
