package com.apptium.customer.repository;

import com.apptium.customer.domain.CustISVRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustISVRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustISVRefRepository extends JpaRepository<CustISVRef, Long>, JpaSpecificationExecutor<CustISVRef> {}
