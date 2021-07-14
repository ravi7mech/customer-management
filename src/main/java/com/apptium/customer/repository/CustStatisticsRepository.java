package com.apptium.customer.repository;

import com.apptium.customer.domain.CustStatistics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustStatisticsRepository extends JpaRepository<CustStatistics, Long>, JpaSpecificationExecutor<CustStatistics> {}
