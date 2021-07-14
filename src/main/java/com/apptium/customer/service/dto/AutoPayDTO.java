package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.AutoPay} entity.
 */
public class AutoPayDTO implements Serializable {

    private Long id;

    @NotNull
    private String channel;

    @NotNull
    private Long autoPayId;

    @NotNull
    private LocalDate debitDate;

    @NotNull
    private String status;

    @NotNull
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getAutoPayId() {
        return autoPayId;
    }

    public void setAutoPayId(Long autoPayId) {
        this.autoPayId = autoPayId;
    }

    public LocalDate getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(LocalDate debitDate) {
        this.debitDate = debitDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof AutoPayDTO)) {
            return false;
        }

        AutoPayDTO autoPayDTO = (AutoPayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, autoPayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoPayDTO{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", autoPayId=" + getAutoPayId() +
            ", debitDate='" + getDebitDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
