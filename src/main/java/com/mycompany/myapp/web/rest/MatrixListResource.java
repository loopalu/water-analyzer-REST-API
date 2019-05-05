package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.MatrixList;
import com.mycompany.myapp.repository.MatrixListRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MatrixList.
 */
@RestController
@RequestMapping("/api")
public class MatrixListResource {

    private final Logger log = LoggerFactory.getLogger(MatrixListResource.class);

    private static final String ENTITY_NAME = "matrixList";

    private final MatrixListRepository matrixListRepository;

    public MatrixListResource(MatrixListRepository matrixListRepository) {
        this.matrixListRepository = matrixListRepository;
    }

    /**
     * POST  /matrix-lists : Create a new matrixList.
     *
     * @param matrixList the matrixList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matrixList, or with status 400 (Bad Request) if the matrixList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matrix-lists")
    public ResponseEntity<MatrixList> createMatrixList(@RequestBody MatrixList matrixList) throws URISyntaxException {
        log.debug("REST request to save MatrixList : {}", matrixList);
        if (matrixList.getId() != null) {
            throw new BadRequestAlertException("A new matrixList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatrixList result = matrixListRepository.save(matrixList);
        return ResponseEntity.created(new URI("/api/matrix-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /matrix-lists : Updates an existing matrixList.
     *
     * @param matrixList the matrixList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matrixList,
     * or with status 400 (Bad Request) if the matrixList is not valid,
     * or with status 500 (Internal Server Error) if the matrixList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matrix-lists")
    public ResponseEntity<MatrixList> updateMatrixList(@RequestBody MatrixList matrixList) throws URISyntaxException {
        log.debug("REST request to update MatrixList : {}", matrixList);
        if (matrixList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatrixList result = matrixListRepository.save(matrixList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matrixList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /matrix-lists : get all the matrixLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matrixLists in body
     */
    @GetMapping("/matrix-lists")
    public List<MatrixList> getAllMatrixLists() {
        log.debug("REST request to get all MatrixLists");
        return matrixListRepository.findAll();
    }

    /**
     * GET  /matrix-lists/:id : get the "id" matrixList.
     *
     * @param id the id of the matrixList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matrixList, or with status 404 (Not Found)
     */
    @GetMapping("/matrix-lists/{id}")
    public ResponseEntity<MatrixList> getMatrixList(@PathVariable Long id) {
        log.debug("REST request to get MatrixList : {}", id);
        Optional<MatrixList> matrixList = matrixListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(matrixList);
    }

    /**
     * DELETE  /matrix-lists/:id : delete the "id" matrixList.
     *
     * @param id the id of the matrixList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matrix-lists/{id}")
    public ResponseEntity<Void> deleteMatrixList(@PathVariable Long id) {
        log.debug("REST request to delete MatrixList : {}", id);
        matrixListRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
