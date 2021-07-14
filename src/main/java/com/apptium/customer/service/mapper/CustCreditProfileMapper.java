package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustCreditProfile} and its DTO {@link CustCreditProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustCreditProfileMapper extends EntityMapper<CustCreditProfileDTO, CustCreditProfile> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustCreditProfileDTO toDtoId(CustCreditProfile custCreditProfile);
}
