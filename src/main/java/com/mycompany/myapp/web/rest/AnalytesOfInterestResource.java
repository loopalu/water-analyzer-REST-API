package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.AnalytesOfInterest;
import com.mycompany.myapp.repository.AnalytesOfInterestRepository;
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
 * REST controller for managing AnalytesOfInterest.
 */
@RestController
@RequestMapping("/api")
public class AnalytesOfInterestResource {

    private final Logger log = LoggerFactory.getLogger(AnalytesOfInterestResource.class);

    private static final String ENTITY_NAME = "analytesOfInterest";

    private final AnalytesOfInterestRepository analytesOfInterestRepository;

    public AnalytesOfInterestResource(AnalytesOfInterestRepository analytesOfInterestRepository) {
        this.analytesOfInterestRepository = analytesOfInterestRepository;
    }

    /**
     * POST  /analytes-of-interests : Create a new analytesOfInterest.
     *
     * @param analytesOfInterest the analytesOfInterest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analytesOfInterest, or with status 400 (Bad Request) if the analytesOfInterest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/analytes-of-interests")
    public ResponseEntity<AnalytesOfInterest> createAnalytesOfInterest(@RequestBody AnalytesOfInterest analytesOfInterest) throws URISyntaxException {
        log.debug("REST request to save AnalytesOfInterest : {}", analytesOfInterest);
        if (analytesOfInterest.getId() != null) {
            throw new BadRequestAlertException("A new analytesOfInterest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalytesOfInterest result = analytesOfInterestRepository.save(analytesOfInterest);
        return ResponseEntity.created(new URI("/api/analytes-of-interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analytes-of-interests : Updates an existing analytesOfInterest.
     *
     * @param analytesOfInterest the analytesOfInterest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analytesOfInterest,
     * or with status 400 (Bad Request) if the analytesOfInterest is not valid,
     * or with status 500 (Internal Server Error) if the analytesOfInterest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/analytes-of-interests")
    public ResponseEntity<AnalytesOfInterest> updateAnalytesOfInterest(@RequestBody AnalytesOfInterest analytesOfInterest) throws URISyntaxException {
        log.debug("REST request to update AnalytesOfInterest : {}", analytesOfInterest);
        if (analytesOfInterest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnalytesOfInterest result = analytesOfInterestRepository.save(analytesOfInterest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, analytesOfInterest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analytes-of-interests : get all the analytesOfInterests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of analytesOfInterests in body
     */
    @GetMapping("/analytes-of-interests")
    public List<AnalytesOfInterest> getAllAnalytesOfInterests() {
        log.debug("REST request to get all AnalytesOfInterests");
        return analytesOfInterestRepository.findAll();
    }

    /**
     * GET  /analytes-of-interests/:id : get the "id" analytesOfInterest.
     *
     * @param id the id of the analytesOfInterest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analytesOfInterest, or with status 404 (Not Found)
     */
    @GetMapping("/analytes-of-interests/{id}")
    public ResponseEntity<AnalytesOfInterest> getAnalytesOfInterest(@PathVariable Long id) {
        log.debug("REST request to get AnalytesOfInterest : {}", id);
        Optional<AnalytesOfInterest> analytesOfInterest = analytesOfInterestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(analytesOfInterest);
    }

    /**
     * DELETE  /analytes-of-interests/:id : delete the "id" analytesOfInterest.
     *
     * @param id the id of the analytesOfInterest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/analytes-of-interests/{id}")
    public ResponseEntity<Void> deleteAnalytesOfInterest(@PathVariable Long id) {
        log.debug("REST request to delete AnalytesOfInterest : {}", id);
        analytesOfInterestRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
