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
 * Criteria class for the {@link com.apptium.customer.domain.CustNewsLetterConfig} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustNewsLetterConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-news-letter-configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustNewsLetterConfigCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter newLetterTypeId;

    private StringFilter value;

    private LongFilter customerId;

    private LongFilter customerId;

    private LongFilter newsLetterTypeId;

    public CustNewsLetterConfigCriteria() {}

    public CustNewsLetterConfigCriteria(CustNewsLetterConfigCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.newLetterTypeId = other.newLetterTypeId == null ? null : other.newLetterTypeId.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.newsLetterTypeId = other.newsLetterTypeId == null ? null : other.newsLetterTypeId.copy();
    }

    @Override
    public CustNewsLetterConfigCriteria copy() {
        return new CustNewsLetterConfigCriteria(this);
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

    public LongFilter getNewLetterTypeId() {
        return newLetterTypeId;
    }

    public LongFilter newLetterTypeId() {
        if (newLetterTypeId == null) {
            newLetterTypeId = new LongFilter();
        }
        return newLetterTypeId;
    }

    public void setNewLetterTypeId(LongFilter newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
    }

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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

    public LongFilter getNewsLetterTypeId() {
        return newsLetterTypeId;
    }

    public LongFilter newsLetterTypeId() {
        if (newsLetterTypeId == null) {
            newsLetterTypeId = new LongFilter();
        }
        return newsLetterTypeId;
    }

    public void setNewsLetterTypeId(LongFilter newsLetterTypeId) {
        this.newsLetterTypeId = newsLetterTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustNewsLetterConfigCriteria that = (CustNewsLetterConfigCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(newLetterTypeId, that.newLetterTypeId) &&
            Objects.equals(value, that.value) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(newsLetterTypeId, that.newsLetterTypeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newLetterTypeId, value, customerId, customerId, newsLetterTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustNewsLetterConfigCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (newLetterTypeId != null ? "newLetterTypeId=" + newLetterTypeId + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (newsLetterTypeId != null ? "newsLetterTypeId=" + newsLetterTypeId + ", " : "") +
            "}";
    }
}
