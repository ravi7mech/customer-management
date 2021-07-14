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
 * Criteria class for the {@link com.apptium.customer.domain.Department} entity. This class is used
 * in {@link com.apptium.customer.web.rest.DepartmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter departmentHead;

    private StringFilter status;

    private LongFilter individualId;

    private LongFilter custRelPartyId;

    public DepartmentCriteria() {}

    public DepartmentCriteria(DepartmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.departmentHead = other.departmentHead == null ? null : other.departmentHead.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.custRelPartyId = other.custRelPartyId == null ? null : other.custRelPartyId.copy();
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
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

    public StringFilter getDepartmentHead() {
        return departmentHead;
    }

    public StringFilter departmentHead() {
        if (departmentHead == null) {
            departmentHead = new StringFilter();
        }
        return departmentHead;
    }

    public void setDepartmentHead(StringFilter departmentHead) {
        this.departmentHead = departmentHead;
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
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(departmentHead, that.departmentHead) &&
            Objects.equals(status, that.status) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(custRelPartyId, that.custRelPartyId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, departmentHead, status, individualId, custRelPartyId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (departmentHead != null ? "departmentHead=" + departmentHead + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (custRelPartyId != null ? "custRelPartyId=" + custRelPartyId + ", " : "") +
            "}";
    }
}
