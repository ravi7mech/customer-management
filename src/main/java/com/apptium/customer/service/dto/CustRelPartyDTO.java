package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustRelParty} entity.
 */
public class CustRelPartyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long roleId;

    @NotNull
    private Long individualId;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;

    @NotNull
    private Long customerId;

    private CustomerDTO customer;

    private DepartmentDTO department;

    private RoleTypeRefDTO roleTypeRef;

    private IndividualDTO individual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public RoleTypeRefDTO getRoleTypeRef() {
        return roleTypeRef;
    }

    public void setRoleTypeRef(RoleTypeRefDTO roleTypeRef) {
        this.roleTypeRef = roleTypeRef;
    }

    public IndividualDTO getIndividual() {
        return individual;
    }

    public void setIndividual(IndividualDTO individual) {
        this.individual = individual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustRelPartyDTO)) {
            return false;
        }

        CustRelPartyDTO custRelPartyDTO = (CustRelPartyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custRelPartyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustRelPartyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", roleId=" + getRoleId() +
            ", individualId=" + getIndividualId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", customerId=" + getCustomerId() +
            ", customer=" + getCustomer() +
            ", department=" + getDepartment() +
            ", roleTypeRef=" + getRoleTypeRef() +
            ", individual=" + getIndividual() +
            "}";
    }
}
