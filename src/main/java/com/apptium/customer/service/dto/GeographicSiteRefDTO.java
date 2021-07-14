package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.GeographicSiteRef} entity.
 */
public class GeographicSiteRefDTO implements Serializable {

    private Long id;

    @NotNull
    private String siteRef;

    @NotNull
    private String location;

    @NotNull
    private Long custConId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteRef() {
        return siteRef;
    }

    public void setSiteRef(String siteRef) {
        this.siteRef = siteRef;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCustConId() {
        return custConId;
    }

    public void setCustConId(Long custConId) {
        this.custConId = custConId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeographicSiteRefDTO)) {
            return false;
        }

        GeographicSiteRefDTO geographicSiteRefDTO = (GeographicSiteRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, geographicSiteRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicSiteRefDTO{" +
            "id=" + getId() +
            ", siteRef='" + getSiteRef() + "'" +
            ", location='" + getLocation() + "'" +
            ", custConId=" + getCustConId() +
            "}";
    }
}
