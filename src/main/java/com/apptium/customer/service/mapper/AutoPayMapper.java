package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.AutoPayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutoPay} and its DTO {@link AutoPayDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutoPayMapper extends EntityMapper<AutoPayDTO, AutoPay> {}
