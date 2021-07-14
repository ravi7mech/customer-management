package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.RoleTypeRef} entity.
 */
public class RoleTypeRefDTO implements Serializable {

    private Long id;

    @NotNull
    private String roleName;

    @NotNull
    private String roleType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleTypeRefDTO)) {
            return false;
        }

        RoleTypeRefDTO roleTypeRefDTO = (RoleTypeRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleTypeRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleTypeRefDTO{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleType='" + getRoleType() + "'" +
            "}";
    }
}
