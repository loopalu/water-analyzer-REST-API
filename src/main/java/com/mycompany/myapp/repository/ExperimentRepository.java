package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Experiment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Experiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

}
