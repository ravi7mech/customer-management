package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A IndContact.
 */
@Entity
@Table(name = "ind_contact")
public class IndContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "preferred", nullable = false)
    private String preferred;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Long individualId;

    @OneToMany(mappedBy = "indContact")
    @JsonIgnoreProperties(value = { "indContact" }, allowSetters = true)
    private Set<IndContactChar> indContactChars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indContacts", "indChars", "indAuditTrials", "custRelParties", "shoppingSessionRefs",
        },
        allowSetters = true
    )
    private Individual individual;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndContact id(Long id) {
        this.id = id;
        return this;
    }

    public String getPreferred() {
        return this.preferred;
    }

    public IndContact preferred(String preferred) {
        this.preferred = preferred;
        return this;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }

    public String getType() {
        return this.type;
    }

    public IndContact type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public IndContact validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return this.validTo;
    }

    public IndContact validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getIndividualId() {
        return this.individualId;
    }

    public IndContact individualId(Long individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }

    public Set<IndContactChar> getIndContactChars() {
        return this.indContactChars;
    }

    public IndContact indContactChars(Set<IndContactChar> indContactChars) {
        this.setIndContactChars(indContactChars);
        return this;
    }

    public IndContact addIndContactChar(IndContactChar indContactChar) {
        this.indContactChars.add(indContactChar);
        indContactChar.setIndContact(this);
        return this;
    }

    public IndContact removeIndContactChar(IndContactChar indContactChar) {
        this.indContactChars.remove(indContactChar);
        indContactChar.setIndContact(null);
        return this;
    }

    public void setIndContactChars(Set<IndContactChar> indContactChars) {
        if (this.indContactChars != null) {
            this.indContactChars.forEach(i -> i.setIndContact(null));
        }
        if (indContactChars != null) {
            indContactChars.forEach(i -> i.setIndContact(this));
        }
        this.indContactChars = indContactChars;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public IndContact individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndContact)) {
            return false;
        }
        return id != null && id.equals(((IndContact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContact{" +
            "id=" + getId() +
            ", preferred='" + getPreferred() + "'" +
            ", type='" + getType() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
