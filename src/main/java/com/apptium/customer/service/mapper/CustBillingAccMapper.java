package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustBillingAccDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustBillingAcc} and its DTO {@link CustBillingAccDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustBillingAccMapper extends EntityMapper<CustBillingAccDTO, CustBillingAcc> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustBillingAccDTO toDtoId(CustBillingAcc custBillingAcc);
}
