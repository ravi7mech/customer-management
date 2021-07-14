package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RoleTypeRef.
 */
@Entity
@Table(name = "role_type_ref")
public class RoleTypeRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @NotNull
    @Column(name = "role_type", nullable = false)
    private String roleType;

    @OneToMany(mappedBy = "roleTypeRef")
    @JsonIgnoreProperties(value = { "customer", "department", "roleTypeRef", "individual" }, allowSetters = true)
    private Set<CustRelParty> custRelParties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleTypeRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public RoleTypeRef roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public RoleTypeRef roleType(String roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Set<CustRelParty> getCustRelParties() {
        return this.custRelParties;
    }

    public RoleTypeRef custRelParties(Set<CustRelParty> custRelParties) {
        this.setCustRelParties(custRelParties);
        return this;
    }

    public RoleTypeRef addCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.add(custRelParty);
        custRelParty.setRoleTypeRef(this);
        return this;
    }

    public RoleTypeRef removeCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.remove(custRelParty);
        custRelParty.setRoleTypeRef(null);
        return this;
    }

    public void setCustRelParties(Set<CustRelParty> custRelParties) {
        if (this.custRelParties != null) {
            this.custRelParties.forEach(i -> i.setRoleTypeRef(null));
        }
        if (custRelParties != null) {
            custRelParties.forEach(i -> i.setRoleTypeRef(this));
        }
        this.custRelParties = custRelParties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleTypeRef)) {
            return false;
        }
        return id != null && id.equals(((RoleTypeRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleTypeRef{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleType='" + getRoleType() + "'" +
            "}";
    }
}
