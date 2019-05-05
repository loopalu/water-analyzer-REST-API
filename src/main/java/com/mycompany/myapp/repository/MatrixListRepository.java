package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MatrixList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatrixList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatrixListRepository extends JpaRepository<MatrixList, Long> {

}
