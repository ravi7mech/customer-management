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
 * Criteria class for the {@link com.apptium.customer.domain.Customer} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter formattedName;

    private StringFilter tradingName;

    private StringFilter custType;

    private StringFilter title;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter middleName;

    private LocalDateFilter dateOfBirth;

    private StringFilter gender;

    private StringFilter maritalStatus;

    private StringFilter nationality;

    private StringFilter status;

    private StringFilter customerEmail;

    private StringFilter companyIdType;

    private LongFilter companyId;

    private LongFilter primaryConAdminIndId;

    private LongFilter custBillingAccId;

    private LongFilter custCreditProfileId;

    private LongFilter custBillingRefId;

    private LongFilter custContactId;

    private LongFilter custStatisticsId;

    private LongFilter custCharId;

    private LongFilter custCommunicationRefId;

    private LongFilter custPasswordCharId;

    private LongFilter custNewsLetterConfigId;

    private LongFilter custSecurityCharId;

    private LongFilter custRelPartyId;

    private LongFilter custISVRefId;

    private LongFilter shoppingSessionRefId;

    private LongFilter industryId;

    public CustomerCriteria() {}

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.formattedName = other.formattedName == null ? null : other.formattedName.copy();
        this.tradingName = other.tradingName == null ? null : other.tradingName.copy();
        this.custType = other.custType == null ? null : other.custType.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.customerEmail = other.customerEmail == null ? null : other.customerEmail.copy();
        this.companyIdType = other.companyIdType == null ? null : other.companyIdType.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.primaryConAdminIndId = other.primaryConAdminIndId == null ? null : other.primaryConAdminIndId.copy();
        this.custBillingAccId = other.custBillingAccId == null ? null : other.custBillingAccId.copy();
        this.custCreditProfileId = other.custCreditProfileId == null ? null : other.custCreditProfileId.copy();
        this.custBillingRefId = other.custBillingRefId == null ? null : other.custBillingRefId.copy();
        this.custContactId = other.custContactId == null ? null : other.custContactId.copy();
        this.custStatisticsId = other.custStatisticsId == null ? null : other.custStatisticsId.copy();
        this.custCharId = other.custCharId == null ? null : other.custCharId.copy();
        this.custCommunicationRefId = other.custCommunicationRefId == null ? null : other.custCommunicationRefId.copy();
        this.custPasswordCharId = other.custPasswordCharId == null ? null : other.custPasswordCharId.copy();
        this.custNewsLetterConfigId = other.custNewsLetterConfigId == null ? null : other.custNewsLetterConfigId.copy();
        this.custSecurityCharId = other.custSecurityCharId == null ? null : other.custSecurityCharId.copy();
        this.custRelPartyId = other.custRelPartyId == null ? null : other.custRelPartyId.copy();
        this.custISVRefId = other.custISVRefId == null ? null : other.custISVRefId.copy();
        this.shoppingSessionRefId = other.shoppingSessionRefId == null ? null : other.shoppingSessionRefId.copy();
        this.industryId = other.industryId == null ? null : other.industryId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public StringFilter getTradingName() {
        return tradingName;
    }

    public StringFilter tradingName() {
        if (tradingName == null) {
            tradingName = new StringFilter();
        }
        return tradingName;
    }

    public void setTradingName(StringFilter tradingName) {
        this.tradingName = tradingName;
    }

    public StringFilter getCustType() {
        return custType;
    }

    public StringFilter custType() {
        if (custType == null) {
            custType = new StringFilter();
        }
        return custType;
    }

    public void setCustType(StringFilter custType) {
        this.custType = custType;
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

    public StringFilter getCustomerEmail() {
        return customerEmail;
    }

    public StringFilter customerEmail() {
        if (customerEmail == null) {
            customerEmail = new StringFilter();
        }
        return customerEmail;
    }

    public void setCustomerEmail(StringFilter customerEmail) {
        this.customerEmail = customerEmail;
    }

    public StringFilter getCompanyIdType() {
        return companyIdType;
    }

    public StringFilter companyIdType() {
        if (companyIdType == null) {
            companyIdType = new StringFilter();
        }
        return companyIdType;
    }

    public void setCompanyIdType(StringFilter companyIdType) {
        this.companyIdType = companyIdType;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getPrimaryConAdminIndId() {
        return primaryConAdminIndId;
    }

    public LongFilter primaryConAdminIndId() {
        if (primaryConAdminIndId == null) {
            primaryConAdminIndId = new LongFilter();
        }
        return primaryConAdminIndId;
    }

    public void setPrimaryConAdminIndId(LongFilter primaryConAdminIndId) {
        this.primaryConAdminIndId = primaryConAdminIndId;
    }

    public LongFilter getCustBillingAccId() {
        return custBillingAccId;
    }

    public LongFilter custBillingAccId() {
        if (custBillingAccId == null) {
            custBillingAccId = new LongFilter();
        }
        return custBillingAccId;
    }

    public void setCustBillingAccId(LongFilter custBillingAccId) {
        this.custBillingAccId = custBillingAccId;
    }

    public LongFilter getCustCreditProfileId() {
        return custCreditProfileId;
    }

    public LongFilter custCreditProfileId() {
        if (custCreditProfileId == null) {
            custCreditProfileId = new LongFilter();
        }
        return custCreditProfileId;
    }

    public void setCustCreditProfileId(LongFilter custCreditProfileId) {
        this.custCreditProfileId = custCreditProfileId;
    }

    public LongFilter getCustBillingRefId() {
        return custBillingRefId;
    }

    public LongFilter custBillingRefId() {
        if (custBillingRefId == null) {
            custBillingRefId = new LongFilter();
        }
        return custBillingRefId;
    }

    public void setCustBillingRefId(LongFilter custBillingRefId) {
        this.custBillingRefId = custBillingRefId;
    }

    public LongFilter getCustContactId() {
        return custContactId;
    }

    public LongFilter custContactId() {
        if (custContactId == null) {
            custContactId = new LongFilter();
        }
        return custContactId;
    }

    public void setCustContactId(LongFilter custContactId) {
        this.custContactId = custContactId;
    }

    public LongFilter getCustStatisticsId() {
        return custStatisticsId;
    }

    public LongFilter custStatisticsId() {
        if (custStatisticsId == null) {
            custStatisticsId = new LongFilter();
        }
        return custStatisticsId;
    }

    public void setCustStatisticsId(LongFilter custStatisticsId) {
        this.custStatisticsId = custStatisticsId;
    }

    public LongFilter getCustCharId() {
        return custCharId;
    }

    public LongFilter custCharId() {
        if (custCharId == null) {
            custCharId = new LongFilter();
        }
        return custCharId;
    }

    public void setCustCharId(LongFilter custCharId) {
        this.custCharId = custCharId;
    }

    public LongFilter getCustCommunicationRefId() {
        return custCommunicationRefId;
    }

    public LongFilter custCommunicationRefId() {
        if (custCommunicationRefId == null) {
            custCommunicationRefId = new LongFilter();
        }
        return custCommunicationRefId;
    }

    public void setCustCommunicationRefId(LongFilter custCommunicationRefId) {
        this.custCommunicationRefId = custCommunicationRefId;
    }

    public LongFilter getCustPasswordCharId() {
        return custPasswordCharId;
    }

    public LongFilter custPasswordCharId() {
        if (custPasswordCharId == null) {
            custPasswordCharId = new LongFilter();
        }
        return custPasswordCharId;
    }

    public void setCustPasswordCharId(LongFilter custPasswordCharId) {
        this.custPasswordCharId = custPasswordCharId;
    }

    public LongFilter getCustNewsLetterConfigId() {
        return custNewsLetterConfigId;
    }

    public LongFilter custNewsLetterConfigId() {
        if (custNewsLetterConfigId == null) {
            custNewsLetterConfigId = new LongFilter();
        }
        return custNewsLetterConfigId;
    }

    public void setCustNewsLetterConfigId(LongFilter custNewsLetterConfigId) {
        this.custNewsLetterConfigId = custNewsLetterConfigId;
    }

    public LongFilter getCustSecurityCharId() {
        return custSecurityCharId;
    }

    public LongFilter custSecurityCharId() {
        if (custSecurityCharId == null) {
            custSecurityCharId = new LongFilter();
        }
        return custSecurityCharId;
    }

    public void setCustSecurityCharId(LongFilter custSecurityCharId) {
        this.custSecurityCharId = custSecurityCharId;
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

    public LongFilter getCustISVRefId() {
        return custISVRefId;
    }

    public LongFilter custISVRefId() {
        if (custISVRefId == null) {
            custISVRefId = new LongFilter();
        }
        return custISVRefId;
    }

    public void setCustISVRefId(LongFilter custISVRefId) {
        this.custISVRefId = custISVRefId;
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

    public LongFilter getIndustryId() {
        return industryId;
    }

    public LongFilter industryId() {
        if (industryId == null) {
            industryId = new LongFilter();
        }
        return industryId;
    }

    public void setIndustryId(LongFilter industryId) {
        this.industryId = industryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(formattedName, that.formattedName) &&
            Objects.equals(tradingName, that.tradingName) &&
            Objects.equals(custType, that.custType) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(status, that.status) &&
            Objects.equals(customerEmail, that.customerEmail) &&
            Objects.equals(companyIdType, that.companyIdType) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(primaryConAdminIndId, that.primaryConAdminIndId) &&
            Objects.equals(custBillingAccId, that.custBillingAccId) &&
            Objects.equals(custCreditProfileId, that.custCreditProfileId) &&
            Objects.equals(custBillingRefId, that.custBillingRefId) &&
            Objects.equals(custContactId, that.custContactId) &&
            Objects.equals(custStatisticsId, that.custStatisticsId) &&
            Objects.equals(custCharId, that.custCharId) &&
            Objects.equals(custCommunicationRefId, that.custCommunicationRefId) &&
            Objects.equals(custPasswordCharId, that.custPasswordCharId) &&
            Objects.equals(custNewsLetterConfigId, that.custNewsLetterConfigId) &&
            Objects.equals(custSecurityCharId, that.custSecurityCharId) &&
            Objects.equals(custRelPartyId, that.custRelPartyId) &&
            Objects.equals(custISVRefId, that.custISVRefId) &&
            Objects.equals(shoppingSessionRefId, that.shoppingSessionRefId) &&
            Objects.equals(industryId, that.industryId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            formattedName,
            tradingName,
            custType,
            title,
            firstName,
            lastName,
            middleName,
            dateOfBirth,
            gender,
            maritalStatus,
            nationality,
            status,
            customerEmail,
            companyIdType,
            companyId,
            primaryConAdminIndId,
            custBillingAccId,
            custCreditProfileId,
            custBillingRefId,
            custContactId,
            custStatisticsId,
            custCharId,
            custCommunicationRefId,
            custPasswordCharId,
            custNewsLetterConfigId,
            custSecurityCharId,
            custRelPartyId,
            custISVRefId,
            shoppingSessionRefId,
            industryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (formattedName != null ? "formattedName=" + formattedName + ", " : "") +
            (tradingName != null ? "tradingName=" + tradingName + ", " : "") +
            (custType != null ? "custType=" + custType + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (customerEmail != null ? "customerEmail=" + customerEmail + ", " : "") +
            (companyIdType != null ? "companyIdType=" + companyIdType + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (primaryConAdminIndId != null ? "primaryConAdminIndId=" + primaryConAdminIndId + ", " : "") +
            (custBillingAccId != null ? "custBillingAccId=" + custBillingAccId + ", " : "") +
            (custCreditProfileId != null ? "custCreditProfileId=" + custCreditProfileId + ", " : "") +
            (custBillingRefId != null ? "custBillingRefId=" + custBillingRefId + ", " : "") +
            (custContactId != null ? "custContactId=" + custContactId + ", " : "") +
            (custStatisticsId != null ? "custStatisticsId=" + custStatisticsId + ", " : "") +
            (custCharId != null ? "custCharId=" + custCharId + ", " : "") +
            (custCommunicationRefId != null ? "custCommunicationRefId=" + custCommunicationRefId + ", " : "") +
            (custPasswordCharId != null ? "custPasswordCharId=" + custPasswordCharId + ", " : "") +
            (custNewsLetterConfigId != null ? "custNewsLetterConfigId=" + custNewsLetterConfigId + ", " : "") +
            (custSecurityCharId != null ? "custSecurityCharId=" + custSecurityCharId + ", " : "") +
            (custRelPartyId != null ? "custRelPartyId=" + custRelPartyId + ", " : "") +
            (custISVRefId != null ? "custISVRefId=" + custISVRefId + ", " : "") +
            (shoppingSessionRefId != null ? "shoppingSessionRefId=" + shoppingSessionRefId + ", " : "") +
            (industryId != null ? "industryId=" + industryId + ", " : "") +
            "}";
    }
}
