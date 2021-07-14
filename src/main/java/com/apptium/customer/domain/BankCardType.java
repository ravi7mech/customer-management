package com.apptium.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BankCardType.
 */
@Entity
@Table(name = "bank_card_type")
public class BankCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotNull
    @Column(name = "card_type", nullable = false)
    private String cardType;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private Long cardNumber;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private Integer cvv;

    @NotNull
    @Column(name = "last_four_digits", nullable = false)
    private Integer lastFourDigits;

    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bankCardTypes", "custBillingRef" }, allowSetters = true)
    private CustPaymentMethod custPaymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankCardType id(Long id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return this.brand;
    }

    public BankCardType brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCardType() {
        return this.cardType;
    }

    public BankCardType cardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return this.cardNumber;
    }

    public BankCardType cardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Instant getExpirationDate() {
        return this.expirationDate;
    }

    public BankCardType expirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getCvv() {
        return this.cvv;
    }

    public BankCardType cvv(Integer cvv) {
        this.cvv = cvv;
        return this;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getLastFourDigits() {
        return this.lastFourDigits;
    }

    public BankCardType lastFourDigits(Integer lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
        return this;
    }

    public void setLastFourDigits(Integer lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getBank() {
        return this.bank;
    }

    public BankCardType bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public CustPaymentMethod getCustPaymentMethod() {
        return this.custPaymentMethod;
    }

    public BankCardType custPaymentMethod(CustPaymentMethod custPaymentMethod) {
        this.setCustPaymentMethod(custPaymentMethod);
        return this;
    }

    public void setCustPaymentMethod(CustPaymentMethod custPaymentMethod) {
        this.custPaymentMethod = custPaymentMethod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankCardType)) {
            return false;
        }
        return id != null && id.equals(((BankCardType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankCardType{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", cvv=" + getCvv() +
            ", lastFourDigits=" + getLastFourDigits() +
            ", bank='" + getBank() + "'" +
            "}";
    }
}
