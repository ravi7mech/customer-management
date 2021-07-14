package com.apptium.customer.repository;

import com.apptium.customer.domain.IndActivation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndActivation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndActivationRepository extends JpaRepository<IndActivation, Long>, JpaSpecificationExecutor<IndActivation> {}
