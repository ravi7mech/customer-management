package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.AutoPay} entity. This class is used
 * in {@link com.apptium.customer.web.rest.AutoPayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /auto-pays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AutoPayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter channel;

    private LongFilter autoPayId;

    private LocalDateFilter debitDate;

    private StringFilter status;

    private LongFilter customerId;

    public AutoPayCriteria() {}

    public AutoPayCriteria(AutoPayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.autoPayId = other.autoPayId == null ? null : other.autoPayId.copy();
        this.debitDate = other.debitDate == null ? null : other.debitDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public AutoPayCriteria copy() {
        return new AutoPayCriteria(this);
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

    public StringFilter getChannel() {
        return channel;
    }

    public StringFilter channel() {
        if (channel == null) {
            channel = new StringFilter();
        }
        return channel;
    }

    public void setChannel(StringFilter channel) {
        this.channel = channel;
    }

    public LongFilter getAutoPayId() {
        return autoPayId;
    }

    public LongFilter autoPayId() {
        if (autoPayId == null) {
            autoPayId = new LongFilter();
        }
        return autoPayId;
    }

    public void setAutoPayId(LongFilter autoPayId) {
        this.autoPayId = autoPayId;
    }

    public LocalDateFilter getDebitDate() {
        return debitDate;
    }

    public LocalDateFilter debitDate() {
        if (debitDate == null) {
            debitDate = new LocalDateFilter();
        }
        return debitDate;
    }

    public void setDebitDate(LocalDateFilter debitDate) {
        this.debitDate = debitDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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
        final AutoPayCriteria that = (AutoPayCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(autoPayId, that.autoPayId) &&
            Objects.equals(debitDate, that.debitDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(customerId, that.customerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channel, autoPayId, debitDate, status, customerId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoPayCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (autoPayId != null ? "autoPayId=" + autoPayId + ", " : "") +
            (debitDate != null ? "debitDate=" + debitDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }
}
