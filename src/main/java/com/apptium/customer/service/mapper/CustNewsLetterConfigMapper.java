package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustNewsLetterConfig} and its DTO {@link CustNewsLetterConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class, NewsLetterTypeMapper.class })
public interface CustNewsLetterConfigMapper extends EntityMapper<CustNewsLetterConfigDTO, CustNewsLetterConfig> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    @Mapping(target = "newsLetterType", source = "newsLetterType", qualifiedByName = "id")
    CustNewsLetterConfigDTO toDto(CustNewsLetterConfig s);
}
