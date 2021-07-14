package com.apptium.customer.repository;

import com.apptium.customer.domain.NewsLetterType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NewsLetterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsLetterTypeRepository extends JpaRepository<NewsLetterType, Long>, JpaSpecificationExecutor<NewsLetterType> {}
