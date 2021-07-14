package com.apptium.customer.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AutoPay.
 */
@Entity
@Table(name = "auto_pay")
public class AutoPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @NotNull
    @Column(name = "auto_pay_id", nullable = false)
    private Long autoPayId;

    @NotNull
    @Column(name = "debit_date", nullable = false)
    private LocalDate debitDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutoPay id(Long id) {
        this.id = id;
        return this;
    }

    public String getChannel() {
        return this.channel;
    }

    public AutoPay channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getAutoPayId() {
        return this.autoPayId;
    }

    public AutoPay autoPayId(Long autoPayId) {
        this.autoPayId = autoPayId;
        return this;
    }

    public void setAutoPayId(Long autoPayId) {
        this.autoPayId = autoPayId;
    }

    public LocalDate getDebitDate() {
        return this.debitDate;
    }

    public AutoPay debitDate(LocalDate debitDate) {
        this.debitDate = debitDate;
        return this;
    }

    public void setDebitDate(LocalDate debitDate) {
        this.debitDate = debitDate;
    }

    public String getStatus() {
        return this.status;
    }

    public AutoPay status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public AutoPay customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutoPay)) {
            return false;
        }
        return id != null && id.equals(((AutoPay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoPay{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", autoPayId=" + getAutoPayId() +
            ", debitDate='" + getDebitDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
