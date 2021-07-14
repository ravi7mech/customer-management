package com.apptium.customer.repository;

import com.apptium.customer.domain.IndAuditTrial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndAuditTrial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndAuditTrialRepository extends JpaRepository<IndAuditTrial, Long>, JpaSpecificationExecutor<IndAuditTrial> {}
