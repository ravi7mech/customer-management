package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.IndNewsLetterConf} entity.
 */
public class IndNewsLetterConfDTO implements Serializable {

    private Long id;

    @NotNull
    private Long newLetterTypeId;

    @NotNull
    private String value;

    @NotNull
    private Long individualId;

    private NewsLetterTypeDTO newsLetterType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewLetterTypeId() {
        return newLetterTypeId;
    }

    public void setNewLetterTypeId(Long newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }

    public NewsLetterTypeDTO getNewsLetterType() {
        return newsLetterType;
    }

    public void setNewsLetterType(NewsLetterTypeDTO newsLetterType) {
        this.newsLetterType = newsLetterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndNewsLetterConfDTO)) {
            return false;
        }

        IndNewsLetterConfDTO indNewsLetterConfDTO = (IndNewsLetterConfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indNewsLetterConfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndNewsLetterConfDTO{" +
            "id=" + getId() +
            ", newLetterTypeId=" + getNewLetterTypeId() +
            ", value='" + getValue() + "'" +
            ", individualId=" + getIndividualId() +
            ", newsLetterType=" + getNewsLetterType() +
            "}";
    }
}
