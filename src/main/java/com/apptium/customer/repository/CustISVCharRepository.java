package com.apptium.customer.repository;

import com.apptium.customer.domain.CustISVChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustISVChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustISVCharRepository extends JpaRepository<CustISVChar, Long>, JpaSpecificationExecutor<CustISVChar> {}
