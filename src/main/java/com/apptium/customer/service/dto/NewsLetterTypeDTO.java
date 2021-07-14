package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.NewsLetterType} entity.
 */
public class NewsLetterTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String newLetterType;

    @NotNull
    private String displayValue;

    private String description;

    @NotNull
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewLetterType() {
        return newLetterType;
    }

    public void setNewLetterType(String newLetterType) {
        this.newLetterType = newLetterType;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsLetterTypeDTO)) {
            return false;
        }

        NewsLetterTypeDTO newsLetterTypeDTO = (NewsLetterTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, newsLetterTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsLetterTypeDTO{" +
            "id=" + getId() +
            ", newLetterType='" + getNewLetterType() + "'" +
            ", displayValue='" + getDisplayValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
