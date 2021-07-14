package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CustISVChar} entity.
 */
public class CustISVCharDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer value;

    @NotNull
    private String valueType;

    @NotNull
    private Integer custIsvId;

    private CustISVRefDTO custISVRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Integer getCustIsvId() {
        return custIsvId;
    }

    public void setCustIsvId(Integer custIsvId) {
        this.custIsvId = custIsvId;
    }

    public CustISVRefDTO getCustISVRef() {
        return custISVRef;
    }

    public void setCustISVRef(CustISVRefDTO custISVRef) {
        this.custISVRef = custISVRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustISVCharDTO)) {
            return false;
        }

        CustISVCharDTO custISVCharDTO = (CustISVCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, custISVCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVCharDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", valueType='" + getValueType() + "'" +
            ", custIsvId=" + getCustIsvId() +
            ", custISVRef=" + getCustISVRef() +
            "}";
    }
}
