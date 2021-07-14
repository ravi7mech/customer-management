package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "formatted_name", nullable = false)
    private String formattedName;

    @NotNull
    @Column(name = "trading_name", nullable = false)
    private String tradingName;

    @NotNull
    @Column(name = "cust_type", nullable = false)
    private String custType;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @NotNull
    @Column(name = "company_id_type", nullable = false)
    private String companyIdType;

    @NotNull
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotNull
    @Column(name = "primary_con_admin_ind_id", nullable = false)
    private Long primaryConAdminIndId;

    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CustBillingAcc custBillingAcc;

    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CustCreditProfile custCreditProfile;

    @JsonIgnoreProperties(value = { "custPaymentMethods", "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CustBillingRef custBillingRef;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "geographicSiteRef", "custContactChars", "customer" }, allowSetters = true)
    private Set<CustContact> custContacts = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustStatistics> custStatistics = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustChar> custChars = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustCommunicationRef> custCommunicationRefs = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustPasswordChar> custPasswordChars = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "newsLetterType" }, allowSetters = true)
    private Set<CustNewsLetterConfig> custNewsLetterConfigs = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustSecurityChar> custSecurityChars = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "department", "roleTypeRef", "individual" }, allowSetters = true)
    private Set<CustRelParty> custRelParties = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "custISVChars", "customer" }, allowSetters = true)
    private Set<CustISVRef> custISVRefs = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "individual" }, allowSetters = true)
    private Set<ShoppingSessionRef> shoppingSessionRefs = new HashSet<>();

    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    @OneToOne(mappedBy = "customer")
    private Industry industry;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedName() {
        return this.formattedName;
    }

    public Customer formattedName(String formattedName) {
        this.formattedName = formattedName;
        return this;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getTradingName() {
        return this.tradingName;
    }

    public Customer tradingName(String tradingName) {
        this.tradingName = tradingName;
        return this;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getCustType() {
        return this.custType;
    }

    public Customer custType(String custType) {
        this.custType = custType;
        return this;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getTitle() {
        return this.title;
    }

    public Customer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Customer middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Customer dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public Customer gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public Customer maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Customer nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return this.status;
    }

    public Customer status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public Customer customerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCompanyIdType() {
        return this.companyIdType;
    }

    public Customer companyIdType(String companyIdType) {
        this.companyIdType = companyIdType;
        return this;
    }

    public void setCompanyIdType(String companyIdType) {
        this.companyIdType = companyIdType;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Customer companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPrimaryConAdminIndId() {
        return this.primaryConAdminIndId;
    }

    public Customer primaryConAdminIndId(Long primaryConAdminIndId) {
        this.primaryConAdminIndId = primaryConAdminIndId;
        return this;
    }

    public void setPrimaryConAdminIndId(Long primaryConAdminIndId) {
        this.primaryConAdminIndId = primaryConAdminIndId;
    }

    public CustBillingAcc getCustBillingAcc() {
        return this.custBillingAcc;
    }

    public Customer custBillingAcc(CustBillingAcc custBillingAcc) {
        this.setCustBillingAcc(custBillingAcc);
        return this;
    }

    public void setCustBillingAcc(CustBillingAcc custBillingAcc) {
        this.custBillingAcc = custBillingAcc;
    }

    public CustCreditProfile getCustCreditProfile() {
        return this.custCreditProfile;
    }

    public Customer custCreditProfile(CustCreditProfile custCreditProfile) {
        this.setCustCreditProfile(custCreditProfile);
        return this;
    }

    public void setCustCreditProfile(CustCreditProfile custCreditProfile) {
        this.custCreditProfile = custCreditProfile;
    }

    public CustBillingRef getCustBillingRef() {
        return this.custBillingRef;
    }

    public Customer custBillingRef(CustBillingRef custBillingRef) {
        this.setCustBillingRef(custBillingRef);
        return this;
    }

    public void setCustBillingRef(CustBillingRef custBillingRef) {
        this.custBillingRef = custBillingRef;
    }

    public Set<CustContact> getCustContacts() {
        return this.custContacts;
    }

    public Customer custContacts(Set<CustContact> custContacts) {
        this.setCustContacts(custContacts);
        return this;
    }

    public Customer addCustContact(CustContact custContact) {
        this.custContacts.add(custContact);
        custContact.setCustomer(this);
        return this;
    }

    public Customer removeCustContact(CustContact custContact) {
        this.custContacts.remove(custContact);
        custContact.setCustomer(null);
        return this;
    }

    public void setCustContacts(Set<CustContact> custContacts) {
        if (this.custContacts != null) {
            this.custContacts.forEach(i -> i.setCustomer(null));
        }
        if (custContacts != null) {
            custContacts.forEach(i -> i.setCustomer(this));
        }
        this.custContacts = custContacts;
    }

    public Set<CustStatistics> getCustStatistics() {
        return this.custStatistics;
    }

    public Customer custStatistics(Set<CustStatistics> custStatistics) {
        this.setCustStatistics(custStatistics);
        return this;
    }

    public Customer addCustStatistics(CustStatistics custStatistics) {
        this.custStatistics.add(custStatistics);
        custStatistics.setCustomer(this);
        return this;
    }

    public Customer removeCustStatistics(CustStatistics custStatistics) {
        this.custStatistics.remove(custStatistics);
        custStatistics.setCustomer(null);
        return this;
    }

    public void setCustStatistics(Set<CustStatistics> custStatistics) {
        if (this.custStatistics != null) {
            this.custStatistics.forEach(i -> i.setCustomer(null));
        }
        if (custStatistics != null) {
            custStatistics.forEach(i -> i.setCustomer(this));
        }
        this.custStatistics = custStatistics;
    }

    public Set<CustChar> getCustChars() {
        return this.custChars;
    }

    public Customer custChars(Set<CustChar> custChars) {
        this.setCustChars(custChars);
        return this;
    }

    public Customer addCustChar(CustChar custChar) {
        this.custChars.add(custChar);
        custChar.setCustomer(this);
        return this;
    }

    public Customer removeCustChar(CustChar custChar) {
        this.custChars.remove(custChar);
        custChar.setCustomer(null);
        return this;
    }

    public void setCustChars(Set<CustChar> custChars) {
        if (this.custChars != null) {
            this.custChars.forEach(i -> i.setCustomer(null));
        }
        if (custChars != null) {
            custChars.forEach(i -> i.setCustomer(this));
        }
        this.custChars = custChars;
    }

    public Set<CustCommunicationRef> getCustCommunicationRefs() {
        return this.custCommunicationRefs;
    }

    public Customer custCommunicationRefs(Set<CustCommunicationRef> custCommunicationRefs) {
        this.setCustCommunicationRefs(custCommunicationRefs);
        return this;
    }

    public Customer addCustCommunicationRef(CustCommunicationRef custCommunicationRef) {
        this.custCommunicationRefs.add(custCommunicationRef);
        custCommunicationRef.setCustomer(this);
        return this;
    }

    public Customer removeCustCommunicationRef(CustCommunicationRef custCommunicationRef) {
        this.custCommunicationRefs.remove(custCommunicationRef);
        custCommunicationRef.setCustomer(null);
        return this;
    }

    public void setCustCommunicationRefs(Set<CustCommunicationRef> custCommunicationRefs) {
        if (this.custCommunicationRefs != null) {
            this.custCommunicationRefs.forEach(i -> i.setCustomer(null));
        }
        if (custCommunicationRefs != null) {
            custCommunicationRefs.forEach(i -> i.setCustomer(this));
        }
        this.custCommunicationRefs = custCommunicationRefs;
    }

    public Set<CustPasswordChar> getCustPasswordChars() {
        return this.custPasswordChars;
    }

    public Customer custPasswordChars(Set<CustPasswordChar> custPasswordChars) {
        this.setCustPasswordChars(custPasswordChars);
        return this;
    }

    public Customer addCustPasswordChar(CustPasswordChar custPasswordChar) {
        this.custPasswordChars.add(custPasswordChar);
        custPasswordChar.setCustomer(this);
        return this;
    }

    public Customer removeCustPasswordChar(CustPasswordChar custPasswordChar) {
        this.custPasswordChars.remove(custPasswordChar);
        custPasswordChar.setCustomer(null);
        return this;
    }

    public void setCustPasswordChars(Set<CustPasswordChar> custPasswordChars) {
        if (this.custPasswordChars != null) {
            this.custPasswordChars.forEach(i -> i.setCustomer(null));
        }
        if (custPasswordChars != null) {
            custPasswordChars.forEach(i -> i.setCustomer(this));
        }
        this.custPasswordChars = custPasswordChars;
    }

    public Set<CustNewsLetterConfig> getCustNewsLetterConfigs() {
        return this.custNewsLetterConfigs;
    }

    public Customer custNewsLetterConfigs(Set<CustNewsLetterConfig> custNewsLetterConfigs) {
        this.setCustNewsLetterConfigs(custNewsLetterConfigs);
        return this;
    }

    public Customer addCustNewsLetterConfig(CustNewsLetterConfig custNewsLetterConfig) {
        this.custNewsLetterConfigs.add(custNewsLetterConfig);
        custNewsLetterConfig.setCustomer(this);
        return this;
    }

    public Customer removeCustNewsLetterConfig(CustNewsLetterConfig custNewsLetterConfig) {
        this.custNewsLetterConfigs.remove(custNewsLetterConfig);
        custNewsLetterConfig.setCustomer(null);
        return this;
    }

    public void setCustNewsLetterConfigs(Set<CustNewsLetterConfig> custNewsLetterConfigs) {
        if (this.custNewsLetterConfigs != null) {
            this.custNewsLetterConfigs.forEach(i -> i.setCustomer(null));
        }
        if (custNewsLetterConfigs != null) {
            custNewsLetterConfigs.forEach(i -> i.setCustomer(this));
        }
        this.custNewsLetterConfigs = custNewsLetterConfigs;
    }

    public Set<CustSecurityChar> getCustSecurityChars() {
        return this.custSecurityChars;
    }

    public Customer custSecurityChars(Set<CustSecurityChar> custSecurityChars) {
        this.setCustSecurityChars(custSecurityChars);
        return this;
    }

    public Customer addCustSecurityChar(CustSecurityChar custSecurityChar) {
        this.custSecurityChars.add(custSecurityChar);
        custSecurityChar.setCustomer(this);
        return this;
    }

    public Customer removeCustSecurityChar(CustSecurityChar custSecurityChar) {
        this.custSecurityChars.remove(custSecurityChar);
        custSecurityChar.setCustomer(null);
        return this;
    }

    public void setCustSecurityChars(Set<CustSecurityChar> custSecurityChars) {
        if (this.custSecurityChars != null) {
            this.custSecurityChars.forEach(i -> i.setCustomer(null));
        }
        if (custSecurityChars != null) {
            custSecurityChars.forEach(i -> i.setCustomer(this));
        }
        this.custSecurityChars = custSecurityChars;
    }

    public Set<CustRelParty> getCustRelParties() {
        return this.custRelParties;
    }

    public Customer custRelParties(Set<CustRelParty> custRelParties) {
        this.setCustRelParties(custRelParties);
        return this;
    }

    public Customer addCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.add(custRelParty);
        custRelParty.setCustomer(this);
        return this;
    }

    public Customer removeCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.remove(custRelParty);
        custRelParty.setCustomer(null);
        return this;
    }

    public void setCustRelParties(Set<CustRelParty> custRelParties) {
        if (this.custRelParties != null) {
            this.custRelParties.forEach(i -> i.setCustomer(null));
        }
        if (custRelParties != null) {
            custRelParties.forEach(i -> i.setCustomer(this));
        }
        this.custRelParties = custRelParties;
    }

    public Set<CustISVRef> getCustISVRefs() {
        return this.custISVRefs;
    }

    public Customer custISVRefs(Set<CustISVRef> custISVRefs) {
        this.setCustISVRefs(custISVRefs);
        return this;
    }

    public Customer addCustISVRef(CustISVRef custISVRef) {
        this.custISVRefs.add(custISVRef);
        custISVRef.setCustomer(this);
        return this;
    }

    public Customer removeCustISVRef(CustISVRef custISVRef) {
        this.custISVRefs.remove(custISVRef);
        custISVRef.setCustomer(null);
        return this;
    }

    public void setCustISVRefs(Set<CustISVRef> custISVRefs) {
        if (this.custISVRefs != null) {
            this.custISVRefs.forEach(i -> i.setCustomer(null));
        }
        if (custISVRefs != null) {
            custISVRefs.forEach(i -> i.setCustomer(this));
        }
        this.custISVRefs = custISVRefs;
    }

    public Set<ShoppingSessionRef> getShoppingSessionRefs() {
        return this.shoppingSessionRefs;
    }

    public Customer shoppingSessionRefs(Set<ShoppingSessionRef> shoppingSessionRefs) {
        this.setShoppingSessionRefs(shoppingSessionRefs);
        return this;
    }

    public Customer addShoppingSessionRef(ShoppingSessionRef shoppingSessionRef) {
        this.shoppingSessionRefs.add(shoppingSessionRef);
        shoppingSessionRef.setCustomer(this);
        return this;
    }

    public Customer removeShoppingSessionRef(ShoppingSessionRef shoppingSessionRef) {
        this.shoppingSessionRefs.remove(shoppingSessionRef);
        shoppingSessionRef.setCustomer(null);
        return this;
    }

    public void setShoppingSessionRefs(Set<ShoppingSessionRef> shoppingSessionRefs) {
        if (this.shoppingSessionRefs != null) {
            this.shoppingSessionRefs.forEach(i -> i.setCustomer(null));
        }
        if (shoppingSessionRefs != null) {
            shoppingSessionRefs.forEach(i -> i.setCustomer(this));
        }
        this.shoppingSessionRefs = shoppingSessionRefs;
    }

    public Industry getIndustry() {
        return this.industry;
    }

    public Customer industry(Industry industry) {
        this.setIndustry(industry);
        return this;
    }

    public void setIndustry(Industry industry) {
        if (this.industry != null) {
            this.industry.setCustomer(null);
        }
        if (industry != null) {
            industry.setCustomer(this);
        }
        this.industry = industry;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formattedName='" + getFormattedName() + "'" +
            ", tradingName='" + getTradingName() + "'" +
            ", custType='" + getCustType() + "'" +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerEmail='" + getCustomerEmail() + "'" +
            ", companyIdType='" + getCompanyIdType() + "'" +
            ", companyId=" + getCompanyId() +
            ", primaryConAdminIndId=" + getPrimaryConAdminIndId() +
            "}";
    }
}
