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
 * Criteria class for the {@link com.apptium.customer.domain.IndContact} entity. This class is used
 * in {@link com.apptium.customer.web.rest.IndContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ind-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter preferred;

    private StringFilter type;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter individualId;

    private LongFilter indContactCharId;

    private LongFilter individualId;

    public IndContactCriteria() {}

    public IndContactCriteria(IndContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.preferred = other.preferred == null ? null : other.preferred.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.indContactCharId = other.indContactCharId == null ? null : other.indContactCharId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
    }

    @Override
    public IndContactCriteria copy() {
        return new IndContactCriteria(this);
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

    public StringFilter getPreferred() {
        return preferred;
    }

    public StringFilter preferred() {
        if (preferred == null) {
            preferred = new StringFilter();
        }
        return preferred;
    }

    public void setPreferred(StringFilter preferred) {
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

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public InstantFilter validFrom() {
        if (validFrom == null) {
            validFrom = new InstantFilter();
        }
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public InstantFilter validTo() {
        if (validTo == null) {
            validTo = new InstantFilter();
        }
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
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

    public LongFilter getIndContactCharId() {
        return indContactCharId;
    }

    public LongFilter indContactCharId() {
        if (indContactCharId == null) {
            indContactCharId = new LongFilter();
        }
        return indContactCharId;
    }

    public void setIndContactCharId(LongFilter indContactCharId) {
        this.indContactCharId = indContactCharId;
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
        final IndContactCriteria that = (IndContactCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(preferred, that.preferred) &&
            Objects.equals(type, that.type) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(indContactCharId, that.indContactCharId) &&
            Objects.equals(individualId, that.individualId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, preferred, type, validFrom, validTo, individualId, indContactCharId, individualId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContactCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (preferred != null ? "preferred=" + preferred + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
            (validTo != null ? "validTo=" + validTo + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (indContactCharId != null ? "indContactCharId=" + indContactCharId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            "}";
    }
}
