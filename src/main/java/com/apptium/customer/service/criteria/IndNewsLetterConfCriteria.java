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
 * Criteria class for the {@link com.apptium.customer.domain.IndNewsLetterConf} entity. This class is used
 * in {@link com.apptium.customer.web.rest.IndNewsLetterConfResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ind-news-letter-confs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndNewsLetterConfCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter newLetterTypeId;

    private StringFilter value;

    private LongFilter individualId;

    private LongFilter individualId;

    private LongFilter newsLetterTypeId;

    public IndNewsLetterConfCriteria() {}

    public IndNewsLetterConfCriteria(IndNewsLetterConfCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.newLetterTypeId = other.newLetterTypeId == null ? null : other.newLetterTypeId.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.newsLetterTypeId = other.newsLetterTypeId == null ? null : other.newsLetterTypeId.copy();
    }

    @Override
    public IndNewsLetterConfCriteria copy() {
        return new IndNewsLetterConfCriteria(this);
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
        final IndNewsLetterConfCriteria that = (IndNewsLetterConfCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(newLetterTypeId, that.newLetterTypeId) &&
            Objects.equals(value, that.value) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(newsLetterTypeId, that.newsLetterTypeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newLetterTypeId, value, individualId, individualId, newsLetterTypeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndNewsLetterConfCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (newLetterTypeId != null ? "newLetterTypeId=" + newLetterTypeId + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (newsLetterTypeId != null ? "newsLetterTypeId=" + newsLetterTypeId + ", " : "") +
            "}";
    }
}
