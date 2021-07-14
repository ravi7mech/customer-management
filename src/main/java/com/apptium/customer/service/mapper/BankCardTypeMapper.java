package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.BankCardTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankCardType} and its DTO {@link BankCardTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustPaymentMethodMapper.class })
public interface BankCardTypeMapper extends EntityMapper<BankCardTypeDTO, BankCardType> {
    @Mapping(target = "custPaymentMethod", source = "custPaymentMethod", qualifiedByName = "id")
    BankCardTypeDTO toDto(BankCardType s);
}
