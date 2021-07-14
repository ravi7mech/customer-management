package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustBillingAccMapper.class, CustCreditProfileMapper.class, CustBillingRefMapper.class })
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "custBillingAcc", source = "custBillingAcc", qualifiedByName = "id")
    @Mapping(target = "custCreditProfile", source = "custCreditProfile", qualifiedByName = "id")
    @Mapping(target = "custBillingRef", source = "custBillingRef", qualifiedByName = "id")
    CustomerDTO toDto(Customer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoId(Customer customer);
}
