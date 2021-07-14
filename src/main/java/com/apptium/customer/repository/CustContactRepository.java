package com.apptium.customer.repository;

import com.apptium.customer.domain.CustContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustContactRepository extends JpaRepository<CustContact, Long>, JpaSpecificationExecutor<CustContact> {}
