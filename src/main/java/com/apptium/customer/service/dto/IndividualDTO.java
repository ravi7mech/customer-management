package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.Individual} entity.
 */
public class IndividualDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;

    @NotNull
    private String formattedName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String gender;

    private String maritalStatus;

    @NotNull
    private String nationality;

    private String status;

    private IndActivationDTO indActivation;

    private IndNewsLetterConfDTO indNewsLetterConf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IndActivationDTO getIndActivation() {
        return indActivation;
    }

    public void setIndActivation(IndActivationDTO indActivation) {
        this.indActivation = indActivation;
    }

    public IndNewsLetterConfDTO getIndNewsLetterConf() {
        return indNewsLetterConf;
    }

    public void setIndNewsLetterConf(IndNewsLetterConfDTO indNewsLetterConf) {
        this.indNewsLetterConf = indNewsLetterConf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndividualDTO)) {
            return false;
        }

        IndividualDTO individualDTO = (IndividualDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, individualDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndividualDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", formattedName='" + getFormattedName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", status='" + getStatus() + "'" +
            ", indActivation=" + getIndActivation() +
            ", indNewsLetterConf=" + getIndNewsLetterConf() +
            "}";
    }
}
