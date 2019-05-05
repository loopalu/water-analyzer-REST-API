package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExperimentPeaks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExperimentPeaks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperimentPeaksRepository extends JpaRepository<ExperimentPeaks, Long> {

}
