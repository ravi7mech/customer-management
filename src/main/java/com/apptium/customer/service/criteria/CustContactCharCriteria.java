package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustContactChar} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustContactCharResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-contact-chars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustContactCharCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private StringFilter streetOne;

    private StringFilter streetTwo;

    private StringFilter city;

    private StringFilter stateOrProvince;

    private StringFilter country;

    private LongFilter postCode;

    private LongFilter phoneNumber;

    private StringFilter emailAddress;

    private LongFilter faxNumber;

    private BigDecimalFilter latitude;

    private BigDecimalFilter longitude;

    private IntegerFilter svContactId;

    private BooleanFilter isEmailValid;

    private BooleanFilter isAddressValid;

    private IntegerFilter custConMediumId;

    private LongFilter custContactId;

    public CustContactCharCriteria() {}

    public CustContactCharCriteria(CustContactCharCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.streetOne = other.streetOne == null ? null : other.streetOne.copy();
        this.streetTwo = other.streetTwo == null ? null : other.streetTwo.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.stateOrProvince = other.stateOrProvince == null ? null : other.stateOrProvince.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.faxNumber = other.faxNumber == null ? null : other.faxNumber.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.svContactId = other.svContactId == null ? null : other.svContactId.copy();
        this.isEmailValid = other.isEmailValid == null ? null : other.isEmailValid.copy();
        this.isAddressValid = other.isAddressValid == null ? null : other.isAddressValid.copy();
        this.custConMediumId = other.custConMediumId == null ? null : other.custConMediumId.copy();
        this.custContactId = other.custContactId == null ? null : other.custContactId.copy();
    }

    @Override
    public CustContactCharCriteria copy() {
        return new CustContactCharCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getStreetOne() {
        return streetOne;
    }

    public StringFilter streetOne() {
        if (streetOne == null) {
            streetOne = new StringFilter();
        }
        return streetOne;
    }

    public void setStreetOne(StringFilter streetOne) {
        this.streetOne = streetOne;
    }

    public StringFilter getStreetTwo() {
        return streetTwo;
    }

    public StringFilter streetTwo() {
        if (streetTwo == null) {
            streetTwo = new StringFilter();
        }
        return streetTwo;
    }

    public void setStreetTwo(StringFilter streetTwo) {
        this.streetTwo = streetTwo;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getStateOrProvince() {
        return stateOrProvince;
    }

    public StringFilter stateOrProvince() {
        if (stateOrProvince == null) {
            stateOrProvince = new StringFilter();
        }
        return stateOrProvince;
    }

    public void setStateOrProvince(StringFilter stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public LongFilter getPostCode() {
        return postCode;
    }

    public LongFilter postCode() {
        if (postCode == null) {
            postCode = new LongFilter();
        }
        return postCode;
    }

    public void setPostCode(LongFilter postCode) {
        this.postCode = postCode;
    }

    public LongFilter getPhoneNumber() {
        return phoneNumber;
    }

    public LongFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new LongFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(LongFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public StringFilter emailAddress() {
        if (emailAddress == null) {
            emailAddress = new StringFilter();
        }
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LongFilter getFaxNumber() {
        return faxNumber;
    }

    public LongFilter faxNumber() {
        if (faxNumber == null) {
            faxNumber = new LongFilter();
        }
        return faxNumber;
    }

    public void setFaxNumber(LongFilter faxNumber) {
        this.faxNumber = faxNumber;
    }

    public BigDecimalFilter getLatitude() {
        return latitude;
    }

    public BigDecimalFilter latitude() {
        if (latitude == null) {
            latitude = new BigDecimalFilter();
        }
        return latitude;
    }

    public void setLatitude(BigDecimalFilter latitude) {
        this.latitude = latitude;
    }

    public BigDecimalFilter getLongitude() {
        return longitude;
    }

    public BigDecimalFilter longitude() {
        if (longitude == null) {
            longitude = new BigDecimalFilter();
        }
        return longitude;
    }

    public void setLongitude(BigDecimalFilter longitude) {
        this.longitude = longitude;
    }

    public IntegerFilter getSvContactId() {
        return svContactId;
    }

    public IntegerFilter svContactId() {
        if (svContactId == null) {
            svContactId = new IntegerFilter();
        }
        return svContactId;
    }

    public void setSvContactId(IntegerFilter svContactId) {
        this.svContactId = svContactId;
    }

    public BooleanFilter getIsEmailValid() {
        return isEmailValid;
    }

    public BooleanFilter isEmailValid() {
        if (isEmailValid == null) {
            isEmailValid = new BooleanFilter();
        }
        return isEmailValid;
    }

    public void setIsEmailValid(BooleanFilter isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public BooleanFilter getIsAddressValid() {
        return isAddressValid;
    }

    public BooleanFilter isAddressValid() {
        if (isAddressValid == null) {
            isAddressValid = new BooleanFilter();
        }
        return isAddressValid;
    }

    public void setIsAddressValid(BooleanFilter isAddressValid) {
        this.isAddressValid = isAddressValid;
    }

    public IntegerFilter getCustConMediumId() {
        return custConMediumId;
    }

    public IntegerFilter custConMediumId() {
        if (custConMediumId == null) {
            custConMediumId = new IntegerFilter();
        }
        return custConMediumId;
    }

    public void setCustConMediumId(IntegerFilter custConMediumId) {
        this.custConMediumId = custConMediumId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustContactCharCriteria that = (CustContactCharCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(streetOne, that.streetOne) &&
            Objects.equals(streetTwo, that.streetTwo) &&
            Objects.equals(city, that.city) &&
            Objects.equals(stateOrProvince, that.stateOrProvince) &&
            Objects.equals(country, that.country) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(faxNumber, that.faxNumber) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(svContactId, that.svContactId) &&
            Objects.equals(isEmailValid, that.isEmailValid) &&
            Objects.equals(isAddressValid, that.isAddressValid) &&
            Objects.equals(custConMediumId, that.custConMediumId) &&
            Objects.equals(custContactId, that.custContactId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            streetOne,
            streetTwo,
            city,
            stateOrProvince,
            country,
            postCode,
            phoneNumber,
            emailAddress,
            faxNumber,
            latitude,
            longitude,
            svContactId,
            isEmailValid,
            isAddressValid,
            custConMediumId,
            custContactId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustContactCharCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (streetOne != null ? "streetOne=" + streetOne + ", " : "") +
            (streetTwo != null ? "streetTwo=" + streetTwo + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (stateOrProvince != null ? "stateOrProvince=" + stateOrProvince + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (postCode != null ? "postCode=" + postCode + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
            (faxNumber != null ? "faxNumber=" + faxNumber + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (svContactId != null ? "svContactId=" + svContactId + ", " : "") +
            (isEmailValid != null ? "isEmailValid=" + isEmailValid + ", " : "") +
            (isAddressValid != null ? "isAddressValid=" + isAddressValid + ", " : "") +
            (custConMediumId != null ? "custConMediumId=" + custConMediumId + ", " : "") +
            (custContactId != null ? "custContactId=" + custContactId + ", " : "") +
            "}";
    }
}
