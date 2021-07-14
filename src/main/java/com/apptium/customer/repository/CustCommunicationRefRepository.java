package com.apptium.customer.repository;

import com.apptium.customer.domain.CustCommunicationRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustCommunicationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCommunicationRefRepository
    extends JpaRepository<CustCommunicationRef, Long>, JpaSpecificationExecutor<CustCommunicationRef> {}
