package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.BankCardType} entity.
 */
public class BankCardTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String brand;

    @NotNull
    private String cardType;

    @NotNull
    private Long cardNumber;

    @NotNull
    private Instant expirationDate;

    @NotNull
    private Integer cvv;

    @NotNull
    private Integer lastFourDigits;

    @NotNull
    private String bank;

    private CustPaymentMethodDTO custPaymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(Integer lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public CustPaymentMethodDTO getCustPaymentMethod() {
        return custPaymentMethod;
    }

    public void setCustPaymentMethod(CustPaymentMethodDTO custPaymentMethod) {
        this.custPaymentMethod = custPaymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankCardTypeDTO)) {
            return false;
        }

        BankCardTypeDTO bankCardTypeDTO = (BankCardTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankCardTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankCardTypeDTO{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", cvv=" + getCvv() +
            ", lastFourDigits=" + getLastFourDigits() +
            ", bank='" + getBank() + "'" +
            ", custPaymentMethod=" + getCustPaymentMethod() +
            "}";
    }
}
