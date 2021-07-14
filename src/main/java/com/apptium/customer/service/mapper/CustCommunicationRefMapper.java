package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustCommunicationRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustCommunicationRef} and its DTO {@link CustCommunicationRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustCommunicationRefMapper extends EntityMapper<CustCommunicationRefDTO, CustCommunicationRef> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustCommunicationRefDTO toDto(CustCommunicationRef s);
}
