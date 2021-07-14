package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.IndContactChar} entity.
 */
public class IndContactCharDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;

    private String streetOne;

    private String streetTwo;

    @NotNull
    private String city;

    @NotNull
    private String stateOrProvince;

    @NotNull
    private String country;

    @NotNull
    private Long postCode;

    @NotNull
    private Long phoneNumber;

    private String emailAddress;

    @NotNull
    private Long faxNumber;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    private Long svContactId;

    private Boolean isEmailValid;

    private Boolean isAddressValid;

    @NotNull
    private Long indConId;

    private IndContactDTO indContact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreetOne() {
        return streetOne;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPostCode() {
        return postCode;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getSvContactId() {
        return svContactId;
    }

    public void setSvContactId(Long svContactId) {
        this.svContactId = svContactId;
    }

    public Boolean getIsEmailValid() {
        return isEmailValid;
    }

    public void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public Boolean getIsAddressValid() {
        return isAddressValid;
    }

    public void setIsAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
    }

    public Long getIndConId() {
        return indConId;
    }

    public void setIndConId(Long indConId) {
        this.indConId = indConId;
    }

    public IndContactDTO getIndContact() {
        return indContact;
    }

    public void setIndContact(IndContactDTO indContact) {
        this.indContact = indContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndContactCharDTO)) {
            return false;
        }

        IndContactCharDTO indContactCharDTO = (IndContactCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indContactCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContactCharDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", streetOne='" + getStreetOne() + "'" +
            ", streetTwo='" + getStreetTwo() + "'" +
            ", city='" + getCity() + "'" +
            ", stateOrProvince='" + getStateOrProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postCode=" + getPostCode() +
            ", phoneNumber=" + getPhoneNumber() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", faxNumber=" + getFaxNumber() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", svContactId=" + getSvContactId() +
            ", isEmailValid='" + getIsEmailValid() + "'" +
            ", isAddressValid='" + getIsAddressValid() + "'" +
            ", indConId=" + getIndConId() +
            ", indContact=" + getIndContact() +
            "}";
    }
}
