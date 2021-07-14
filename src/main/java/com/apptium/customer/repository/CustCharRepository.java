package com.apptium.customer.repository;

import com.apptium.customer.domain.CustChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCharRepository extends JpaRepository<CustChar, Long>, JpaSpecificationExecutor<CustChar> {}
