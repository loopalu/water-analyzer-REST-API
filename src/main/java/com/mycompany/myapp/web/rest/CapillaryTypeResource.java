package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.CapillaryType;
import com.mycompany.myapp.repository.CapillaryTypeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CapillaryType.
 */
@RestController
@RequestMapping("/api")
public class CapillaryTypeResource {

    private final Logger log = LoggerFactory.getLogger(CapillaryTypeResource.class);

    private static final String ENTITY_NAME = "capillaryType";

    private final CapillaryTypeRepository capillaryTypeRepository;

    public CapillaryTypeResource(CapillaryTypeRepository capillaryTypeRepository) {
        this.capillaryTypeRepository = capillaryTypeRepository;
    }

    /**
     * POST  /capillary-types : Create a new capillaryType.
     *
     * @param capillaryType the capillaryType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capillaryType, or with status 400 (Bad Request) if the capillaryType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/capillary-types")
    public ResponseEntity<CapillaryType> createCapillaryType(@Valid @RequestBody CapillaryType capillaryType) throws URISyntaxException {
        log.debug("REST request to save CapillaryType : {}", capillaryType);
        if (capillaryType.getId() != null) {
            throw new BadRequestAlertException("A new capillaryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapillaryType result = capillaryTypeRepository.save(capillaryType);
        return ResponseEntity.created(new URI("/api/capillary-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capillary-types : Updates an existing capillaryType.
     *
     * @param capillaryType the capillaryType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capillaryType,
     * or with status 400 (Bad Request) if the capillaryType is not valid,
     * or with status 500 (Internal Server Error) if the capillaryType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/capillary-types")
    public ResponseEntity<CapillaryType> updateCapillaryType(@Valid @RequestBody CapillaryType capillaryType) throws URISyntaxException {
        log.debug("REST request to update CapillaryType : {}", capillaryType);
        if (capillaryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CapillaryType result = capillaryTypeRepository.save(capillaryType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, capillaryType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /capillary-types : get all the capillaryTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of capillaryTypes in body
     */
    @GetMapping("/capillary-types")
    public List<CapillaryType> getAllCapillaryTypes() {
        log.debug("REST request to get all CapillaryTypes");
        return capillaryTypeRepository.findAll();
    }

    /**
     * GET  /capillary-types/:id : get the "id" capillaryType.
     *
     * @param id the id of the capillaryType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capillaryType, or with status 404 (Not Found)
     */
    @GetMapping("/capillary-types/{id}")
    public ResponseEntity<CapillaryType> getCapillaryType(@PathVariable Long id) {
        log.debug("REST request to get CapillaryType : {}", id);
        Optional<CapillaryType> capillaryType = capillaryTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(capillaryType);
    }

    /**
     * DELETE  /capillary-types/:id : delete the "id" capillaryType.
     *
     * @param id the id of the capillaryType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/capillary-types/{id}")
    public ResponseEntity<Void> deleteCapillaryType(@PathVariable Long id) {
        log.debug("REST request to delete CapillaryType : {}", id);
        capillaryTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
