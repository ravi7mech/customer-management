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
 * Criteria class for the {@link com.apptium.customer.domain.CustContact} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter preferred;

    private StringFilter type;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter customerId;

    private LongFilter geographicSiteRefId;

    private LongFilter custContactCharId;

    private LongFilter customerId;

    public CustContactCriteria() {}

    public CustContactCriteria(CustContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.preferred = other.preferred == null ? null : other.preferred.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.geographicSiteRefId = other.geographicSiteRefId == null ? null : other.geographicSiteRefId.copy();
        this.custContactCharId = other.custContactCharId == null ? null : other.custContactCharId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustContactCriteria copy() {
        return new CustContactCriteria(this);
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

    public LongFilter getGeographicSiteRefId() {
        return geographicSiteRefId;
    }

    public LongFilter geographicSiteRefId() {
        if (geographicSiteRefId == null) {
            geographicSiteRefId = new LongFilter();
        }
        return geographicSiteRefId;
    }

    public void setGeographicSiteRefId(LongFilter geographicSiteRefId) {
        this.geographicSiteRefId = geographicSiteRefId;
    }

    public LongFilter getCustContactCharId() {
        return custContactCharId;
    }

    public LongFilter custContactCharId() {
        if (custContactCharId == null) {
            custContactCharId = new LongFilter();
        }
        return custContactCharId;
    }

    public void setCustContactCharId(LongFilter custContactCharId) {
        this.custContactCharId = custContactCharId;
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
        final CustContactCriteria that = (CustContactCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(preferred, that.preferred) &&
            Objects.equals(type, that.type) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(geographicSiteRefId, that.geographicSiteRefId) &&
            Objects.equals(custContactCharId, that.custContactCharId) &&
            Objects.equals(customerId, that.customerId)
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
            validFrom,
            validTo,
            customerId,
            geographicSiteRefId,
            custContactCharId,
            customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustContactCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (preferred != null ? "preferred=" + preferred + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
            (validTo != null ? "validTo=" + validTo + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (geographicSiteRefId != null ? "geographicSiteRefId=" + geographicSiteRefId + ", " : "") +
            (custContactCharId != null ? "custContactCharId=" + custContactCharId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
