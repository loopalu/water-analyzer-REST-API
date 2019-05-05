package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CapillaryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CapillaryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapillaryTypeRepository extends JpaRepository<CapillaryType, Long> {

}
