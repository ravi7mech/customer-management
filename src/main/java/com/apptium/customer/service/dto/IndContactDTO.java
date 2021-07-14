package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.IndContact} entity.
 */
public class IndContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String preferred;

    @NotNull
    private String type;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;

    @NotNull
    private Long individualId;

    private IndividualDTO individual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
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
        if (!(o instanceof IndContactDTO)) {
            return false;
        }

        IndContactDTO indContactDTO = (IndContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContactDTO{" +
            "id=" + getId() +
            ", preferred='" + getPreferred() + "'" +
            ", type='" + getType() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", individualId=" + getIndividualId() +
            ", individual=" + getIndividual() +
            "}";
    }
}
