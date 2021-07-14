package com.apptium.customer.repository;

import com.apptium.customer.domain.CustNewsLetterConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustNewsLetterConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustNewsLetterConfigRepository
    extends JpaRepository<CustNewsLetterConfig, Long>, JpaSpecificationExecutor<CustNewsLetterConfig> {}
