package com.apptium.customer.repository;

import com.apptium.customer.domain.CreditCheckVerification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CreditCheckVerification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCheckVerificationRepository
    extends JpaRepository<CreditCheckVerification, Long>, JpaSpecificationExecutor<CreditCheckVerification> {}
