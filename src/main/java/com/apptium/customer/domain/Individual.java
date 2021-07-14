package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Individual.
 */
@Entity
@Table(name = "individual")
public class Individual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "formatted_name", nullable = false)
    private String formattedName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "status")
    private String status;

    @JsonIgnoreProperties(value = { "individual" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private IndActivation indActivation;

    @JsonIgnoreProperties(value = { "individual", "newsLetterType" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private IndNewsLetterConf indNewsLetterConf;

    @OneToMany(mappedBy = "individual")
    @JsonIgnoreProperties(value = { "indContactChars", "individual" }, allowSetters = true)
    private Set<IndContact> indContacts = new HashSet<>();

    @OneToMany(mappedBy = "individual")
    @JsonIgnoreProperties(value = { "individual" }, allowSetters = true)
    private Set<IndChar> indChars = new HashSet<>();

    @OneToMany(mappedBy = "individual")
    @JsonIgnoreProperties(value = { "individual" }, allowSetters = true)
    private Set<IndAuditTrial> indAuditTrials = new HashSet<>();

    @OneToMany(mappedBy = "individual")
    @JsonIgnoreProperties(value = { "customer", "department", "roleTypeRef", "individual" }, allowSetters = true)
    private Set<CustRelParty> custRelParties = new HashSet<>();

    @OneToMany(mappedBy = "individual")
    @JsonIgnoreProperties(value = { "customer", "individual" }, allowSetters = true)
    private Set<ShoppingSessionRef> shoppingSessionRefs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Individual id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Individual title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Individual firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Individual lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Individual middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFormattedName() {
        return this.formattedName;
    }

    public Individual formattedName(String formattedName) {
        this.formattedName = formattedName;
        return this;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Individual dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public Individual gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public Individual maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Individual nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return this.status;
    }

    public Individual status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IndActivation getIndActivation() {
        return this.indActivation;
    }

    public Individual indActivation(IndActivation indActivation) {
        this.setIndActivation(indActivation);
        return this;
    }

    public void setIndActivation(IndActivation indActivation) {
        this.indActivation = indActivation;
    }

    public IndNewsLetterConf getIndNewsLetterConf() {
        return this.indNewsLetterConf;
    }

    public Individual indNewsLetterConf(IndNewsLetterConf indNewsLetterConf) {
        this.setIndNewsLetterConf(indNewsLetterConf);
        return this;
    }

    public void setIndNewsLetterConf(IndNewsLetterConf indNewsLetterConf) {
        this.indNewsLetterConf = indNewsLetterConf;
    }

    public Set<IndContact> getIndContacts() {
        return this.indContacts;
    }

    public Individual indContacts(Set<IndContact> indContacts) {
        this.setIndContacts(indContacts);
        return this;
    }

    public Individual addIndContact(IndContact indContact) {
        this.indContacts.add(indContact);
        indContact.setIndividual(this);
        return this;
    }

    public Individual removeIndContact(IndContact indContact) {
        this.indContacts.remove(indContact);
        indContact.setIndividual(null);
        return this;
    }

    public void setIndContacts(Set<IndContact> indContacts) {
        if (this.indContacts != null) {
            this.indContacts.forEach(i -> i.setIndividual(null));
        }
        if (indContacts != null) {
            indContacts.forEach(i -> i.setIndividual(this));
        }
        this.indContacts = indContacts;
    }

    public Set<IndChar> getIndChars() {
        return this.indChars;
    }

    public Individual indChars(Set<IndChar> indChars) {
        this.setIndChars(indChars);
        return this;
    }

    public Individual addIndChar(IndChar indChar) {
        this.indChars.add(indChar);
        indChar.setIndividual(this);
        return this;
    }

    public Individual removeIndChar(IndChar indChar) {
        this.indChars.remove(indChar);
        indChar.setIndividual(null);
        return this;
    }

    public void setIndChars(Set<IndChar> indChars) {
        if (this.indChars != null) {
            this.indChars.forEach(i -> i.setIndividual(null));
        }
        if (indChars != null) {
            indChars.forEach(i -> i.setIndividual(this));
        }
        this.indChars = indChars;
    }

    public Set<IndAuditTrial> getIndAuditTrials() {
        return this.indAuditTrials;
    }

    public Individual indAuditTrials(Set<IndAuditTrial> indAuditTrials) {
        this.setIndAuditTrials(indAuditTrials);
        return this;
    }

    public Individual addIndAuditTrial(IndAuditTrial indAuditTrial) {
        this.indAuditTrials.add(indAuditTrial);
        indAuditTrial.setIndividual(this);
        return this;
    }

    public Individual removeIndAuditTrial(IndAuditTrial indAuditTrial) {
        this.indAuditTrials.remove(indAuditTrial);
        indAuditTrial.setIndividual(null);
        return this;
    }

    public void setIndAuditTrials(Set<IndAuditTrial> indAuditTrials) {
        if (this.indAuditTrials != null) {
            this.indAuditTrials.forEach(i -> i.setIndividual(null));
        }
        if (indAuditTrials != null) {
            indAuditTrials.forEach(i -> i.setIndividual(this));
        }
        this.indAuditTrials = indAuditTrials;
    }

    public Set<CustRelParty> getCustRelParties() {
        return this.custRelParties;
    }

    public Individual custRelParties(Set<CustRelParty> custRelParties) {
        this.setCustRelParties(custRelParties);
        return this;
    }

    public Individual addCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.add(custRelParty);
        custRelParty.setIndividual(this);
        return this;
    }

    public Individual removeCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.remove(custRelParty);
        custRelParty.setIndividual(null);
        return this;
    }

    public void setCustRelParties(Set<CustRelParty> custRelParties) {
        if (this.custRelParties != null) {
            this.custRelParties.forEach(i -> i.setIndividual(null));
        }
        if (custRelParties != null) {
            custRelParties.forEach(i -> i.setIndividual(this));
        }
        this.custRelParties = custRelParties;
    }

    public Set<ShoppingSessionRef> getShoppingSessionRefs() {
        return this.shoppingSessionRefs;
    }

    public Individual shoppingSessionRefs(Set<ShoppingSessionRef> shoppingSessionRefs) {
        this.setShoppingSessionRefs(shoppingSessionRefs);
        return this;
    }

    public Individual addShoppingSessionRef(ShoppingSessionRef shoppingSessionRef) {
        this.shoppingSessionRefs.add(shoppingSessionRef);
        shoppingSessionRef.setIndividual(this);
        return this;
    }

    public Individual removeShoppingSessionRef(ShoppingSessionRef shoppingSessionRef) {
        this.shoppingSessionRefs.remove(shoppingSessionRef);
        shoppingSessionRef.setIndividual(null);
        return this;
    }

    public void setShoppingSessionRefs(Set<ShoppingSessionRef> shoppingSessionRefs) {
        if (this.shoppingSessionRefs != null) {
            this.shoppingSessionRefs.forEach(i -> i.setIndividual(null));
        }
        if (shoppingSessionRefs != null) {
            shoppingSessionRefs.forEach(i -> i.setIndividual(this));
        }
        this.shoppingSessionRefs = shoppingSessionRefs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Individual)) {
            return false;
        }
        return id != null && id.equals(((Individual) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Individual{" +
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
            "}";
    }
}
