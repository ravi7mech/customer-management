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
 * Criteria class for the {@link com.apptium.customer.domain.RoleTypeRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.RoleTypeRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /role-type-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleTypeRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roleName;

    private StringFilter roleType;

    private LongFilter custRelPartyId;

    public RoleTypeRefCriteria() {}

    public RoleTypeRefCriteria(RoleTypeRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roleName = other.roleName == null ? null : other.roleName.copy();
        this.roleType = other.roleType == null ? null : other.roleType.copy();
        this.custRelPartyId = other.custRelPartyId == null ? null : other.custRelPartyId.copy();
    }

    @Override
    public RoleTypeRefCriteria copy() {
        return new RoleTypeRefCriteria(this);
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

    public StringFilter getRoleName() {
        return roleName;
    }

    public StringFilter roleName() {
        if (roleName == null) {
            roleName = new StringFilter();
        }
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }

    public StringFilter getRoleType() {
        return roleType;
    }

    public StringFilter roleType() {
        if (roleType == null) {
            roleType = new StringFilter();
        }
        return roleType;
    }

    public void setRoleType(StringFilter roleType) {
        this.roleType = roleType;
    }

    public LongFilter getCustRelPartyId() {
        return custRelPartyId;
    }

    public LongFilter custRelPartyId() {
        if (custRelPartyId == null) {
            custRelPartyId = new LongFilter();
        }
        return custRelPartyId;
    }

    public void setCustRelPartyId(LongFilter custRelPartyId) {
        this.custRelPartyId = custRelPartyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoleTypeRefCriteria that = (RoleTypeRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roleName, that.roleName) &&
            Objects.equals(roleType, that.roleType) &&
            Objects.equals(custRelPartyId, that.custRelPartyId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, roleType, custRelPartyId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleTypeRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roleName != null ? "roleName=" + roleName + ", " : "") +
            (roleType != null ? "roleType=" + roleType + ", " : "") +
            (custRelPartyId != null ? "custRelPartyId=" + custRelPartyId + ", " : "") +
            "}";
    }
}
