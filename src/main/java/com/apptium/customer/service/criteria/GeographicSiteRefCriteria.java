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
 * Criteria class for the {@link com.apptium.customer.domain.GeographicSiteRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.GeographicSiteRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /geographic-site-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GeographicSiteRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter siteRef;

    private StringFilter location;

    private LongFilter custConId;

    private LongFilter custContactId;

    public GeographicSiteRefCriteria() {}

    public GeographicSiteRefCriteria(GeographicSiteRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.siteRef = other.siteRef == null ? null : other.siteRef.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.custConId = other.custConId == null ? null : other.custConId.copy();
        this.custContactId = other.custContactId == null ? null : other.custContactId.copy();
    }

    @Override
    public GeographicSiteRefCriteria copy() {
        return new GeographicSiteRefCriteria(this);
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

    public StringFilter getSiteRef() {
        return siteRef;
    }

    public StringFilter siteRef() {
        if (siteRef == null) {
            siteRef = new StringFilter();
        }
        return siteRef;
    }

    public void setSiteRef(StringFilter siteRef) {
        this.siteRef = siteRef;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LongFilter getCustConId() {
        return custConId;
    }

    public LongFilter custConId() {
        if (custConId == null) {
            custConId = new LongFilter();
        }
        return custConId;
    }

    public void setCustConId(LongFilter custConId) {
        this.custConId = custConId;
    }

    public LongFilter getCustContactId() {
        return custContactId;
    }

    public LongFilter custContactId() {
        if (custContactId == null) {
            custContactId = new LongFilter();
        }
        return custContactId;
    }

    public void setCustContactId(LongFilter custContactId) {
        this.custContactId = custContactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GeographicSiteRefCriteria that = (GeographicSiteRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(siteRef, that.siteRef) &&
            Objects.equals(location, that.location) &&
            Objects.equals(custConId, that.custConId) &&
            Objects.equals(custContactId, that.custContactId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteRef, location, custConId, custContactId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicSiteRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (siteRef != null ? "siteRef=" + siteRef + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (custConId != null ? "custConId=" + custConId + ", " : "") +
            (custContactId != null ? "custContactId=" + custContactId + ", " : "") +
            "}";
    }
}
