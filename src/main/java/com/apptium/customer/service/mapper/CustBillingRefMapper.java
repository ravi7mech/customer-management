package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustBillingRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustBillingRef} and its DTO {@link CustBillingRefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustBillingRefMapper extends EntityMapper<CustBillingRefDTO, CustBillingRef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustBillingRefDTO toDtoId(CustBillingRef custBillingRef);
}
