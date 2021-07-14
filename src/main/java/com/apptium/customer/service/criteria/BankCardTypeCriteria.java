package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.BankCardType} entity. This class is used
 * in {@link com.apptium.customer.web.rest.BankCardTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bank-card-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BankCardTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter brand;

    private StringFilter cardType;

    private LongFilter cardNumber;

    private InstantFilter expirationDate;

    private IntegerFilter cvv;

    private IntegerFilter lastFourDigits;

    private StringFilter bank;

    private LongFilter custPaymentMethodId;

    public BankCardTypeCriteria() {}

    public BankCardTypeCriteria(BankCardTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.cardType = other.cardType == null ? null : other.cardType.copy();
        this.cardNumber = other.cardNumber == null ? null : other.cardNumber.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.cvv = other.cvv == null ? null : other.cvv.copy();
        this.lastFourDigits = other.lastFourDigits == null ? null : other.lastFourDigits.copy();
        this.bank = other.bank == null ? null : other.bank.copy();
        this.custPaymentMethodId = other.custPaymentMethodId == null ? null : other.custPaymentMethodId.copy();
    }

    @Override
    public BankCardTypeCriteria copy() {
        return new BankCardTypeCriteria(this);
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

    public StringFilter getBrand() {
        return brand;
    }

    public StringFilter brand() {
        if (brand == null) {
            brand = new StringFilter();
        }
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getCardType() {
        return cardType;
    }

    public StringFilter cardType() {
        if (cardType == null) {
            cardType = new StringFilter();
        }
        return cardType;
    }

    public void setCardType(StringFilter cardType) {
        this.cardType = cardType;
    }

    public LongFilter getCardNumber() {
        return cardNumber;
    }

    public LongFilter cardNumber() {
        if (cardNumber == null) {
            cardNumber = new LongFilter();
        }
        return cardNumber;
    }

    public void setCardNumber(LongFilter cardNumber) {
        this.cardNumber = cardNumber;
    }

    public InstantFilter getExpirationDate() {
        return expirationDate;
    }

    public InstantFilter expirationDate() {
        if (expirationDate == null) {
            expirationDate = new InstantFilter();
        }
        return expirationDate;
    }

    public void setExpirationDate(InstantFilter expirationDate) {
        this.expirationDate = expirationDate;
    }

    public IntegerFilter getCvv() {
        return cvv;
    }

    public IntegerFilter cvv() {
        if (cvv == null) {
            cvv = new IntegerFilter();
        }
        return cvv;
    }

    public void setCvv(IntegerFilter cvv) {
        this.cvv = cvv;
    }

    public IntegerFilter getLastFourDigits() {
        return lastFourDigits;
    }

    public IntegerFilter lastFourDigits() {
        if (lastFourDigits == null) {
            lastFourDigits = new IntegerFilter();
        }
        return lastFourDigits;
    }

    public void setLastFourDigits(IntegerFilter lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public StringFilter getBank() {
        return bank;
    }

    public StringFilter bank() {
        if (bank == null) {
            bank = new StringFilter();
        }
        return bank;
    }

    public void setBank(StringFilter bank) {
        this.bank = bank;
    }

    public LongFilter getCustPaymentMethodId() {
        return custPaymentMethodId;
    }

    public LongFilter custPaymentMethodId() {
        if (custPaymentMethodId == null) {
            custPaymentMethodId = new LongFilter();
        }
        return custPaymentMethodId;
    }

    public void setCustPaymentMethodId(LongFilter custPaymentMethodId) {
        this.custPaymentMethodId = custPaymentMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BankCardTypeCriteria that = (BankCardTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(cardType, that.cardType) &&
            Objects.equals(cardNumber, that.cardNumber) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(cvv, that.cvv) &&
            Objects.equals(lastFourDigits, that.lastFourDigits) &&
            Objects.equals(bank, that.bank) &&
            Objects.equals(custPaymentMethodId, that.custPaymentMethodId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, cardType, cardNumber, expirationDate, cvv, lastFourDigits, bank, custPaymentMethodId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankCardTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (brand != null ? "brand=" + brand + ", " : "") +
            (cardType != null ? "cardType=" + cardType + ", " : "") +
            (cardNumber != null ? "cardNumber=" + cardNumber + ", " : "") +
            (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
            (cvv != null ? "cvv=" + cvv + ", " : "") +
            (lastFourDigits != null ? "lastFourDigits=" + lastFourDigits + ", " : "") +
            (bank != null ? "bank=" + bank + ", " : "") +
            (custPaymentMethodId != null ? "custPaymentMethodId=" + custPaymentMethodId + ", " : "") +
            "}";
    }
}
