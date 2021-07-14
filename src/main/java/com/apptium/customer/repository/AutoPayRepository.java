package com.apptium.customer.repository;

import com.apptium.customer.domain.AutoPay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AutoPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoPayRepository extends JpaRepository<AutoPay, Long>, JpaSpecificationExecutor<AutoPay> {}
