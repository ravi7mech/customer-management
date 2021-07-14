package com.apptium.customer.repository;

import com.apptium.customer.domain.CustSecurityChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustSecurityChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustSecurityCharRepository extends JpaRepository<CustSecurityChar, Long>, JpaSpecificationExecutor<CustSecurityChar> {}
