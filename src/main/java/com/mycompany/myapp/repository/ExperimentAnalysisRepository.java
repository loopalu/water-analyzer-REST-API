package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExperimentAnalysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExperimentAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperimentAnalysisRepository extends JpaRepository<ExperimentAnalysis, Long> {

}
