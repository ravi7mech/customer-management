package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CustBillingRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CustBillingRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cust-billing-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustBillingRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amountDue;

    private InstantFilter billDate;

    private LongFilter billNo;

    private InstantFilter billingPeriod;

    private StringFilter category;

    private StringFilter href;

    private InstantFilter lastUpdatedDate;

    private InstantFilter nextUpdatedDate;

    private InstantFilter paymentDueDate;

    private StringFilter state;

    private BigDecimalFilter taxExcludedAmount;

    private BigDecimalFilter taxIncludedAmount;

    private LongFilter customerId;

    private LongFilter custPaymentMethodId;

    private LongFilter customerId;

    public CustBillingRefCriteria() {}

    public CustBillingRefCriteria(CustBillingRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amountDue = other.amountDue == null ? null : other.amountDue.copy();
        this.billDate = other.billDate == null ? null : other.billDate.copy();
        this.billNo = other.billNo == null ? null : other.billNo.copy();
        this.billingPeriod = other.billingPeriod == null ? null : other.billingPeriod.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.nextUpdatedDate = other.nextUpdatedDate == null ? null : other.nextUpdatedDate.copy();
        this.paymentDueDate = other.paymentDueDate == null ? null : other.paymentDueDate.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.taxExcludedAmount = other.taxExcludedAmount == null ? null : other.taxExcludedAmount.copy();
        this.taxIncludedAmount = other.taxIncludedAmount == null ? null : other.taxIncludedAmount.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.custPaymentMethodId = other.custPaymentMethodId == null ? null : other.custPaymentMethodId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CustBillingRefCriteria copy() {
        return new CustBillingRefCriteria(this);
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

    public BigDecimalFilter getAmountDue() {
        return amountDue;
    }

    public BigDecimalFilter amountDue() {
        if (amountDue == null) {
            amountDue = new BigDecimalFilter();
        }
        return amountDue;
    }

    public void setAmountDue(BigDecimalFilter amountDue) {
        this.amountDue = amountDue;
    }

    public InstantFilter getBillDate() {
        return billDate;
    }

    public InstantFilter billDate() {
        if (billDate == null) {
            billDate = new InstantFilter();
        }
        return billDate;
    }

    public void setBillDate(InstantFilter billDate) {
        this.billDate = billDate;
    }

    public LongFilter getBillNo() {
        return billNo;
    }

    public LongFilter billNo() {
        if (billNo == null) {
            billNo = new LongFilter();
        }
        return billNo;
    }

    public void setBillNo(LongFilter billNo) {
        this.billNo = billNo;
    }

    public InstantFilter getBillingPeriod() {
        return billingPeriod;
    }

    public InstantFilter billingPeriod() {
        if (billingPeriod == null) {
            billingPeriod = new InstantFilter();
        }
        return billingPeriod;
    }

    public void setBillingPeriod(InstantFilter billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
    }

    public InstantFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public InstantFilter lastUpdatedDate() {
        if (lastUpdatedDate == null) {
            lastUpdatedDate = new InstantFilter();
        }
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(InstantFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public InstantFilter getNextUpdatedDate() {
        return nextUpdatedDate;
    }

    public InstantFilter nextUpdatedDate() {
        if (nextUpdatedDate == null) {
            nextUpdatedDate = new InstantFilter();
        }
        return nextUpdatedDate;
    }

    public void setNextUpdatedDate(InstantFilter nextUpdatedDate) {
        this.nextUpdatedDate = nextUpdatedDate;
    }

    public InstantFilter getPaymentDueDate() {
        return paymentDueDate;
    }

    public InstantFilter paymentDueDate() {
        if (paymentDueDate == null) {
            paymentDueDate = new InstantFilter();
        }
        return paymentDueDate;
    }

    public void setPaymentDueDate(InstantFilter paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public BigDecimalFilter getTaxExcludedAmount() {
        return taxExcludedAmount;
    }

    public BigDecimalFilter taxExcludedAmount() {
        if (taxExcludedAmount == null) {
            taxExcludedAmount = new BigDecimalFilter();
        }
        return taxExcludedAmount;
    }

    public void setTaxExcludedAmount(BigDecimalFilter taxExcludedAmount) {
        this.taxExcludedAmount = taxExcludedAmount;
    }

    public BigDecimalFilter getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public BigDecimalFilter taxIncludedAmount() {
        if (taxIncludedAmount == null) {
            taxIncludedAmount = new BigDecimalFilter();
        }
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimalFilter taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustBillingRefCriteria that = (CustBillingRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amountDue, that.amountDue) &&
            Objects.equals(billDate, that.billDate) &&
            Objects.equals(billNo, that.billNo) &&
            Objects.equals(billingPeriod, that.billingPeriod) &&
            Objects.equals(category, that.category) &&
            Objects.equals(href, that.href) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(nextUpdatedDate, that.nextUpdatedDate) &&
            Objects.equals(paymentDueDate, that.paymentDueDate) &&
            Objects.equals(state, that.state) &&
            Objects.equals(taxExcludedAmount, that.taxExcludedAmount) &&
            Objects.equals(taxIncludedAmount, that.taxIncludedAmount) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(custPaymentMethodId, that.custPaymentMethodId) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            amountDue,
            billDate,
            billNo,
            billingPeriod,
            category,
            href,
            lastUpdatedDate,
            nextUpdatedDate,
            paymentDueDate,
            state,
            taxExcludedAmount,
            taxIncludedAmount,
            customerId,
            custPaymentMethodId,
            customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amountDue != null ? "amountDue=" + amountDue + ", " : "") +
            (billDate != null ? "billDate=" + billDate + ", " : "") +
            (billNo != null ? "billNo=" + billNo + ", " : "") +
            (billingPeriod != null ? "billingPeriod=" + billingPeriod + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            (nextUpdatedDate != null ? "nextUpdatedDate=" + nextUpdatedDate + ", " : "") +
            (paymentDueDate != null ? "paymentDueDate=" + paymentDueDate + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (taxExcludedAmount != null ? "taxExcludedAmount=" + taxExcludedAmount + ", " : "") +
            (taxIncludedAmount != null ? "taxIncludedAmount=" + taxIncludedAmount + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (custPaymentMethodId != null ? "custPaymentMethodId=" + custPaymentMethodId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
