package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Method;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Method entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {

}
