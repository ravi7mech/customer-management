package com.apptium.customer.repository;

import com.apptium.customer.domain.CustBillingRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustBillingRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustBillingRefRepository extends JpaRepository<CustBillingRef, Long>, JpaSpecificationExecutor<CustBillingRef> {}
