package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.Eligibility} entity.
 */
public class EligibilityDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer noOfLines;

    @NotNull
    private BigDecimal creditAmount;

    private String description;

    @NotNull
    private Boolean isEligiblePaylater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfLines() {
        return noOfLines;
    }

    public void setNoOfLines(Integer noOfLines) {
        this.noOfLines = noOfLines;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEligiblePaylater() {
        return isEligiblePaylater;
    }

    public void setIsEligiblePaylater(Boolean isEligiblePaylater) {
        this.isEligiblePaylater = isEligiblePaylater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EligibilityDTO)) {
            return false;
        }

        EligibilityDTO eligibilityDTO = (EligibilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eligibilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityDTO{" +
            "id=" + getId() +
            ", noOfLines=" + getNoOfLines() +
            ", creditAmount=" + getCreditAmount() +
            ", description='" + getDescription() + "'" +
            ", isEligiblePaylater='" + getIsEligiblePaylater() + "'" +
            "}";
    }
}
