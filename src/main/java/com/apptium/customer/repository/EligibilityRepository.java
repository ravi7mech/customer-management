package com.apptium.customer.repository;

import com.apptium.customer.domain.Eligibility;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Eligibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityRepository extends JpaRepository<Eligibility, Long>, JpaSpecificationExecutor<Eligibility> {}
