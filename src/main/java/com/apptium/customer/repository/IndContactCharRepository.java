package com.apptium.customer.repository;

import com.apptium.customer.domain.IndContactChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndContactChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndContactCharRepository extends JpaRepository<IndContactChar, Long>, JpaSpecificationExecutor<IndContactChar> {}
