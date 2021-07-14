package com.apptium.customer.repository;

import com.apptium.customer.domain.IndChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndCharRepository extends JpaRepository<IndChar, Long>, JpaSpecificationExecutor<IndChar> {}
