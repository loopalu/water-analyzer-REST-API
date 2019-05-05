package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Analyte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Analyte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyteRepository extends JpaRepository<Analyte, Long> {

}
