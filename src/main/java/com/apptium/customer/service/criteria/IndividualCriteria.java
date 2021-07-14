package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.Individual} entity. This class is used
 * in {@link com.apptium.customer.web.rest.IndividualResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /individuals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndividualCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter middleName;

    private StringFilter formattedName;

    private LocalDateFilter dateOfBirth;

    private StringFilter gender;

    private StringFilter maritalStatus;

    private StringFilter nationality;

    private StringFilter status;

    private LongFilter indActivationId;

    private LongFilter indNewsLetterConfId;

    private LongFilter indContactId;

    private LongFilter indCharId;

    private LongFilter indAuditTrialId;

    private LongFilter custRelPartyId;

    private LongFilter shoppingSessionRefId;

    public IndividualCriteria() {}

    public IndividualCriteria(IndividualCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.formattedName = other.formattedName == null ? null : other.formattedName.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.indActivationId = other.indActivationId == null ? null : other.indActivationId.copy();
        this.indNewsLetterConfId = other.indNewsLetterConfId == null ? null : other.indNewsLetterConfId.copy();
        this.indContactId = other.indContactId == null ? null : other.indContactId.copy();
        this.indCharId = other.indCharId == null ? null : other.indCharId.copy();
        this.indAuditTrialId = other.indAuditTrialId == null ? null : other.indAuditTrialId.copy();
        this.custRelPartyId = other.custRelPartyId == null ? null : other.custRelPartyId.copy();
        this.shoppingSessionRefId = other.shoppingSessionRefId == null ? null : other.shoppingSessionRefId.copy();
    }

    @Override
    public IndividualCriteria copy() {
        return new IndividualCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getFormattedName() {
        return formattedName;
    }

    public StringFilter formattedName() {
        if (formattedName == null) {
            formattedName = new StringFilter();
        }
        return formattedName;
    }

    public void setFormattedName(StringFilter formattedName) {
        this.formattedName = formattedName;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new LocalDateFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public StringFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new StringFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
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

    public LongFilter getIndActivationId() {
        return indActivationId;
    }

    public LongFilter indActivationId() {
        if (indActivationId == null) {
            indActivationId = new LongFilter();
        }
        return indActivationId;
    }

    public void setIndActivationId(LongFilter indActivationId) {
        this.indActivationId = indActivationId;
    }

    public LongFilter getIndNewsLetterConfId() {
        return indNewsLetterConfId;
    }

    public LongFilter indNewsLetterConfId() {
        if (indNewsLetterConfId == null) {
            indNewsLetterConfId = new LongFilter();
        }
        return indNewsLetterConfId;
    }

    public void setIndNewsLetterConfId(LongFilter indNewsLetterConfId) {
        this.indNewsLetterConfId = indNewsLetterConfId;
    }

    public LongFilter getIndContactId() {
        return indContactId;
    }

    public LongFilter indContactId() {
        if (indContactId == null) {
            indContactId = new LongFilter();
        }
        return indContactId;
    }

    public void setIndContactId(LongFilter indContactId) {
        this.indContactId = indContactId;
    }

    public LongFilter getIndCharId() {
        return indCharId;
    }

    public LongFilter indCharId() {
        if (indCharId == null) {
            indCharId = new LongFilter();
        }
        return indCharId;
    }

    public void setIndCharId(LongFilter indCharId) {
        this.indCharId = indCharId;
    }

    public LongFilter getIndAuditTrialId() {
        return indAuditTrialId;
    }

    public LongFilter indAuditTrialId() {
        if (indAuditTrialId == null) {
            indAuditTrialId = new LongFilter();
        }
        return indAuditTrialId;
    }

    public void setIndAuditTrialId(LongFilter indAuditTrialId) {
        this.indAuditTrialId = indAuditTrialId;
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

    public LongFilter getShoppingSessionRefId() {
        return shoppingSessionRefId;
    }

    public LongFilter shoppingSessionRefId() {
        if (shoppingSessionRefId == null) {
            shoppingSessionRefId = new LongFilter();
        }
        return shoppingSessionRefId;
    }

    public void setShoppingSessionRefId(LongFilter shoppingSessionRefId) {
        this.shoppingSessionRefId = shoppingSessionRefId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IndividualCriteria that = (IndividualCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(formattedName, that.formattedName) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(status, that.status) &&
            Objects.equals(indActivationId, that.indActivationId) &&
            Objects.equals(indNewsLetterConfId, that.indNewsLetterConfId) &&
            Objects.equals(indContactId, that.indContactId) &&
            Objects.equals(indCharId, that.indCharId) &&
            Objects.equals(indAuditTrialId, that.indAuditTrialId) &&
            Objects.equals(custRelPartyId, that.custRelPartyId) &&
            Objects.equals(shoppingSessionRefId, that.shoppingSessionRefId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            firstName,
            lastName,
            middleName,
            formattedName,
            dateOfBirth,
            gender,
            maritalStatus,
            nationality,
            status,
            indActivationId,
            indNewsLetterConfId,
            indContactId,
            indCharId,
            indAuditTrialId,
            custRelPartyId,
            shoppingSessionRefId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndividualCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (formattedName != null ? "formattedName=" + formattedName + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (indActivationId != null ? "indActivationId=" + indActivationId + ", " : "") +
            (indNewsLetterConfId != null ? "indNewsLetterConfId=" + indNewsLetterConfId + ", " : "") +
            (indContactId != null ? "indContactId=" + indContactId + ", " : "") +
            (indCharId != null ? "indCharId=" + indCharId + ", " : "") +
            (indAuditTrialId != null ? "indAuditTrialId=" + indAuditTrialId + ", " : "") +
            (custRelPartyId != null ? "custRelPartyId=" + custRelPartyId + ", " : "") +
            (shoppingSessionRefId != null ? "shoppingSessionRefId=" + shoppingSessionRefId + ", " : "") +
            "}";
    }
}
