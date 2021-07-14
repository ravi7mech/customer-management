package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustSecurityCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustSecurityChar} and its DTO {@link CustSecurityCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustSecurityCharMapper extends EntityMapper<CustSecurityCharDTO, CustSecurityChar> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustSecurityCharDTO toDto(CustSecurityChar s);
}
