package com.apptium.customer.repository;

import com.apptium.customer.domain.CustBillingAcc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustBillingAcc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustBillingAccRepository extends JpaRepository<CustBillingAcc, Long>, JpaSpecificationExecutor<CustBillingAcc> {}
