package com.apptium.customer.repository;

import com.apptium.customer.domain.IndNewsLetterConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndNewsLetterConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndNewsLetterConfRepository extends JpaRepository<IndNewsLetterConf, Long>, JpaSpecificationExecutor<IndNewsLetterConf> {}
