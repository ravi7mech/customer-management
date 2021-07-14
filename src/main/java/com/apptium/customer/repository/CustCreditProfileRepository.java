package com.apptium.customer.repository;

import com.apptium.customer.domain.CustCreditProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustCreditProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCreditProfileRepository extends JpaRepository<CustCreditProfile, Long>, JpaSpecificationExecutor<CustCreditProfile> {}
