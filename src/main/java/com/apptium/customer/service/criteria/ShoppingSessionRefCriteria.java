package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.ShoppingSessionRef} entity. This class is used
 * in {@link com.apptium.customer.web.rest.ShoppingSessionRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shopping-session-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShoppingSessionRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter href;

    private StringFilter shoppingSessionId;

    private StringFilter status;

    private BooleanFilter sessionAbondoned;

    private LongFilter customerId;

    private StringFilter channel;

    private LongFilter individualId;

    private LongFilter customerId;

    private LongFilter individualId;

    public ShoppingSessionRefCriteria() {}

    public ShoppingSessionRefCriteria(ShoppingSessionRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.shoppingSessionId = other.shoppingSessionId == null ? null : other.shoppingSessionId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.sessionAbondoned = other.sessionAbondoned == null ? null : other.sessionAbondoned.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.individualId = other.individualId == null ? null : other.individualId.copy();
    }

    @Override
    public ShoppingSessionRefCriteria copy() {
        return new ShoppingSessionRefCriteria(this);
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

    public StringFilter getShoppingSessionId() {
        return shoppingSessionId;
    }

    public StringFilter shoppingSessionId() {
        if (shoppingSessionId == null) {
            shoppingSessionId = new StringFilter();
        }
        return shoppingSessionId;
    }

    public void setShoppingSessionId(StringFilter shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
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

    public BooleanFilter getSessionAbondoned() {
        return sessionAbondoned;
    }

    public BooleanFilter sessionAbondoned() {
        if (sessionAbondoned == null) {
            sessionAbondoned = new BooleanFilter();
        }
        return sessionAbondoned;
    }

    public void setSessionAbondoned(BooleanFilter sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
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

    public LongFilter getIndividualId() {
        return individualId;
    }

    public LongFilter individualId() {
        if (individualId == null) {
            individualId = new LongFilter();
        }
        return individualId;
    }

    public void setIndividualId(LongFilter individualId) {
        this.individualId = individualId;
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

    public LongFilter getIndividualId() {
        return individualId;
    }

    public LongFilter individualId() {
        if (individualId == null) {
            individualId = new LongFilter();
        }
        return individualId;
    }

    public void setIndividualId(LongFilter individualId) {
        this.individualId = individualId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShoppingSessionRefCriteria that = (ShoppingSessionRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(href, that.href) &&
            Objects.equals(shoppingSessionId, that.shoppingSessionId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(sessionAbondoned, that.sessionAbondoned) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(individualId, that.individualId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(individualId, that.individualId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            href,
            shoppingSessionId,
            status,
            sessionAbondoned,
            customerId,
            channel,
            individualId,
            customerId,
            individualId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingSessionRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (shoppingSessionId != null ? "shoppingSessionId=" + shoppingSessionId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (sessionAbondoned != null ? "sessionAbondoned=" + sessionAbondoned + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (individualId != null ? "individualId=" + individualId + ", " : "") +
            "}";
    }
}
