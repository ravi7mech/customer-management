package com.apptium.customer.repository;

import com.apptium.customer.domain.IndContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndContactRepository extends JpaRepository<IndContact, Long>, JpaSpecificationExecutor<IndContact> {}
