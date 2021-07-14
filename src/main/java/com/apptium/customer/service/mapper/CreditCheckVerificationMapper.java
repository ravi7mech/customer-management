package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCheckVerification} and its DTO {@link CreditCheckVerificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditCheckVerificationMapper extends EntityMapper<CreditCheckVerificationDTO, CreditCheckVerification> {}
