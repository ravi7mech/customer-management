package com.apptium.customer.repository;

import com.apptium.customer.domain.CustRelParty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustRelParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustRelPartyRepository extends JpaRepository<CustRelParty, Long>, JpaSpecificationExecutor<CustRelParty> {}
