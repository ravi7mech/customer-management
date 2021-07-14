package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A IndContactChar.
 */
@Entity
@Table(name = "ind_contact_char")
public class IndContactChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "street_one")
    private String streetOne;

    @Column(name = "street_two")
    private String streetTwo;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state_or_province", nullable = false)
    private String stateOrProvince;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "post_code", nullable = false)
    private Long postCode;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @NotNull
    @Column(name = "fax_number", nullable = false)
    private Long faxNumber;

    @NotNull
    @Column(name = "latitude", precision = 21, scale = 2, nullable = false)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "longitude", precision = 21, scale = 2, nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "sv_contact_id", nullable = false)
    private Long svContactId;

    @Column(name = "is_email_valid")
    private Boolean isEmailValid;

    @Column(name = "is_address_valid")
    private Boolean isAddressValid;

    @NotNull
    @Column(name = "ind_con_id", nullable = false)
    private Long indConId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "indContactChars", "individual" }, allowSetters = true)
    private IndContact indContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndContactChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public IndContactChar type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreetOne() {
        return this.streetOne;
    }

    public IndContactChar streetOne(String streetOne) {
        this.streetOne = streetOne;
        return this;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return this.streetTwo;
    }

    public IndContactChar streetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
        return this;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCity() {
        return this.city;
    }

    public IndContactChar city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return this.stateOrProvince;
    }

    public IndContactChar stateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return this.country;
    }

    public IndContactChar country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPostCode() {
        return this.postCode;
    }

    public IndContactChar postCode(Long postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public IndContactChar phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public IndContactChar emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getFaxNumber() {
        return this.faxNumber;
    }

    public IndContactChar faxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public IndContactChar latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public IndContactChar longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getSvContactId() {
        return this.svContactId;
    }

    public IndContactChar svContactId(Long svContactId) {
        this.svContactId = svContactId;
        return this;
    }

    public void setSvContactId(Long svContactId) {
        this.svContactId = svContactId;
    }

    public Boolean getIsEmailValid() {
        return this.isEmailValid;
    }

    public IndContactChar isEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
        return this;
    }

    public void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public Boolean getIsAddressValid() {
        return this.isAddressValid;
    }

    public IndContactChar isAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
        return this;
    }

    public void setIsAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
    }

    public Long getIndConId() {
        return this.indConId;
    }

    public IndContactChar indConId(Long indConId) {
        this.indConId = indConId;
        return this;
    }

    public void setIndConId(Long indConId) {
        this.indConId = indConId;
    }

    public IndContact getIndContact() {
        return this.indContact;
    }

    public IndContactChar indContact(IndContact indContact) {
        this.setIndContact(indContact);
        return this;
    }

    public void setIndContact(IndContact indContact) {
        this.indContact = indContact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndContactChar)) {
            return false;
        }
        return id != null && id.equals(((IndContactChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContactChar{" +
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
            "}";
    }
}
