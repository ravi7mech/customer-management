package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustBillingRef} entity.
 */
public class CustBillingRefDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amountDue;

    @NotNull
    private Instant billDate;

    @NotNull
    private Long billNo;

    @NotNull
    private Instant billingPeriod;

    @NotNull
    private String category;

    @NotNull
    private String href;

    @NotNull
    private Instant lastUpdatedDate;

    @NotNull
    private Instant nextUpdatedDate;

    @NotNull
    private Instant paymentDueDate;

    @NotNull
    private String state;

    @NotNull
    private BigDecimal taxExcludedAmount;

    @NotNull
    private BigDecimal taxIncludedAmount;

    @NotNull
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public Instant getBillDate() {
        return billDate;
    }

    public void setBillDate(Instant billDate) {
        this.billDate = billDate;
    }

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public Instant getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(Instant billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Instant getNextUpdatedDate() {
        return nextUpdatedDate;
    }

    public void setNextUpdatedDate(Instant nextUpdatedDate) {
        this.nextUpdatedDate = nextUpdatedDate;
    }

    public Instant getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Instant paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxExcludedAmount() {
        return taxExcludedAmount;
    }

    public void setTaxExcludedAmount(BigDecimal taxExcludedAmount) {
        this.taxExcludedAmount = taxExcludedAmount;
    }

    public BigDecimal getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustBillingRefDTO)) {
            return false;
        }

        CustBillingRefDTO custBillingRefDTO = (CustBillingRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custBillingRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustBillingRefDTO{" +
            "id=" + getId() +
            ", amountDue=" + getAmountDue() +
            ", billDate='" + getBillDate() + "'" +
            ", billNo=" + getBillNo() +
            ", billingPeriod='" + getBillingPeriod() + "'" +
            ", category='" + getCategory() + "'" +
            ", href='" + getHref() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", nextUpdatedDate='" + getNextUpdatedDate() + "'" +
            ", paymentDueDate='" + getPaymentDueDate() + "'" +
            ", state='" + getState() + "'" +
            ", taxExcludedAmount=" + getTaxExcludedAmount() +
            ", taxIncludedAmount=" + getTaxIncludedAmount() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
