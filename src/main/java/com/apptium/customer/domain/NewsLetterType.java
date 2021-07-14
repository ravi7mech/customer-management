package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A NewsLetterType.
 */
@Entity
@Table(name = "news_letter_type")
public class NewsLetterType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "new_letter_type", nullable = false)
    private String newLetterType;

    @NotNull
    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "newsLetterType")
    @JsonIgnoreProperties(value = { "customer", "newsLetterType" }, allowSetters = true)
    private Set<CustNewsLetterConfig> custNewsLetterConfigs = new HashSet<>();

    @OneToMany(mappedBy = "newsLetterType")
    @JsonIgnoreProperties(value = { "individual", "newsLetterType" }, allowSetters = true)
    private Set<IndNewsLetterConf> indNewsLetterConfs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsLetterType id(Long id) {
        this.id = id;
        return this;
    }

    public String getNewLetterType() {
        return this.newLetterType;
    }

    public NewsLetterType newLetterType(String newLetterType) {
        this.newLetterType = newLetterType;
        return this;
    }

    public void setNewLetterType(String newLetterType) {
        this.newLetterType = newLetterType;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }

    public NewsLetterType displayValue(String displayValue) {
        this.displayValue = displayValue;
        return this;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDescription() {
        return this.description;
    }

    public NewsLetterType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public NewsLetterType status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<CustNewsLetterConfig> getCustNewsLetterConfigs() {
        return this.custNewsLetterConfigs;
    }

    public NewsLetterType custNewsLetterConfigs(Set<CustNewsLetterConfig> custNewsLetterConfigs) {
        this.setCustNewsLetterConfigs(custNewsLetterConfigs);
        return this;
    }

    public NewsLetterType addCustNewsLetterConfig(CustNewsLetterConfig custNewsLetterConfig) {
        this.custNewsLetterConfigs.add(custNewsLetterConfig);
        custNewsLetterConfig.setNewsLetterType(this);
        return this;
    }

    public NewsLetterType removeCustNewsLetterConfig(CustNewsLetterConfig custNewsLetterConfig) {
        this.custNewsLetterConfigs.remove(custNewsLetterConfig);
        custNewsLetterConfig.setNewsLetterType(null);
        return this;
    }

    public void setCustNewsLetterConfigs(Set<CustNewsLetterConfig> custNewsLetterConfigs) {
        if (this.custNewsLetterConfigs != null) {
            this.custNewsLetterConfigs.forEach(i -> i.setNewsLetterType(null));
        }
        if (custNewsLetterConfigs != null) {
            custNewsLetterConfigs.forEach(i -> i.setNewsLetterType(this));
        }
        this.custNewsLetterConfigs = custNewsLetterConfigs;
    }

    public Set<IndNewsLetterConf> getIndNewsLetterConfs() {
        return this.indNewsLetterConfs;
    }

    public NewsLetterType indNewsLetterConfs(Set<IndNewsLetterConf> indNewsLetterConfs) {
        this.setIndNewsLetterConfs(indNewsLetterConfs);
        return this;
    }

    public NewsLetterType addIndNewsLetterConf(IndNewsLetterConf indNewsLetterConf) {
        this.indNewsLetterConfs.add(indNewsLetterConf);
        indNewsLetterConf.setNewsLetterType(this);
        return this;
    }

    public NewsLetterType removeIndNewsLetterConf(IndNewsLetterConf indNewsLetterConf) {
        this.indNewsLetterConfs.remove(indNewsLetterConf);
        indNewsLetterConf.setNewsLetterType(null);
        return this;
    }

    public void setIndNewsLetterConfs(Set<IndNewsLetterConf> indNewsLetterConfs) {
        if (this.indNewsLetterConfs != null) {
            this.indNewsLetterConfs.forEach(i -> i.setNewsLetterType(null));
        }
        if (indNewsLetterConfs != null) {
            indNewsLetterConfs.forEach(i -> i.setNewsLetterType(this));
        }
        this.indNewsLetterConfs = indNewsLetterConfs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsLetterType)) {
            return false;
        }
        return id != null && id.equals(((NewsLetterType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsLetterType{" +
            "id=" + getId() +
            ", newLetterType='" + getNewLetterType() + "'" +
            ", displayValue='" + getDisplayValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
