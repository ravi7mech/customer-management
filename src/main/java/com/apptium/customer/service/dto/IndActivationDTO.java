package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.IndActivation} entity.
 */
public class IndActivationDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String activity;

    @NotNull
    private Long customerId;

    @NotNull
    private Long individualId;

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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndActivationDTO)) {
            return false;
        }

        IndActivationDTO indActivationDTO = (IndActivationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indActivationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndActivationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", customerId=" + getCustomerId() +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
