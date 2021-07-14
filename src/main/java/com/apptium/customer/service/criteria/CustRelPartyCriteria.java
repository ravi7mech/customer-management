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
 * Criteria class for the {@link com.apptium.customer.domain.CustRelParty} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustRelPartyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-rel-parties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustRelPartyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter roleId;

    private LongFilter individualId;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter customerId;

    private LongFilter customerId;

    private LongFilter departmentId;

    private LongFilter roleTypeRefId;

    private LongFilter individualId;

    public CustRelPartyCriteria() {}

    public CustRelPartyCriteria(CustRelPartyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.roleTypeRefId = other.roleTypeRefId == null ? null : other.roleTypeRefId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
    }

    @Override
    public CustRelPartyCriteria copy() {
        return new CustRelPartyCriteria(this);
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

    public LongFilter getRoleId() {
        return roleId;
    }

    public LongFilter roleId() {
        if (roleId == null) {
            roleId = new LongFilter();
        }
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
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

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getRoleTypeRefId() {
        return roleTypeRefId;
    }

    public LongFilter roleTypeRefId() {
        if (roleTypeRefId == null) {
            roleTypeRefId = new LongFilter();
        }
        return roleTypeRefId;
    }

    public void setRoleTypeRefId(LongFilter roleTypeRefId) {
        this.roleTypeRefId = roleTypeRefId;
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
        final CustRelPartyCriteria that = (CustRelPartyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(roleTypeRefId, that.roleTypeRefId) &&
            Objects.equals(individualId, that.individualId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            roleId,
            individualId,
            validFrom,
            validTo,
            customerId,
            customerId,
            departmentId,
            roleTypeRefId,
            individualId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustRelPartyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (roleId != null ? "roleId=" + roleId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
            (validTo != null ? "validTo=" + validTo + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (roleTypeRefId != null ? "roleTypeRefId=" + roleTypeRefId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            "}";
    }
}
