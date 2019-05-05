package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BGEComposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BGEComposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BGECompositionRepository extends JpaRepository<BGEComposition, Long> {

}
