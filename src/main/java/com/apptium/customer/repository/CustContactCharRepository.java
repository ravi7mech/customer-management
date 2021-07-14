package com.apptium.customer.repository;

import com.apptium.customer.domain.CustContactChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustContactChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustContactCharRepository extends JpaRepository<CustContactChar, Long>, JpaSpecificationExecutor<CustContactChar> {}
