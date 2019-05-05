package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnalytesOfInterest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnalytesOfInterest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalytesOfInterestRepository extends JpaRepository<AnalytesOfInterest, Long> {

}
