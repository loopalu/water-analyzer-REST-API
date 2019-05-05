package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.ExperimentPeaks;
import com.mycompany.myapp.repository.ExperimentPeaksRepository;
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
 * REST controller for managing ExperimentPeaks.
 */
@RestController
@RequestMapping("/api")
public class ExperimentPeaksResource {

    private final Logger log = LoggerFactory.getLogger(ExperimentPeaksResource.class);

    private static final String ENTITY_NAME = "experimentPeaks";

    private final ExperimentPeaksRepository experimentPeaksRepository;

    public ExperimentPeaksResource(ExperimentPeaksRepository experimentPeaksRepository) {
        this.experimentPeaksRepository = experimentPeaksRepository;
    }

    /**
     * POST  /experiment-peaks : Create a new experimentPeaks.
     *
     * @param experimentPeaks the experimentPeaks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experimentPeaks, or with status 400 (Bad Request) if the experimentPeaks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiment-peaks")
    public ResponseEntity<ExperimentPeaks> createExperimentPeaks(@RequestBody ExperimentPeaks experimentPeaks) throws URISyntaxException {
        log.debug("REST request to save ExperimentPeaks : {}", experimentPeaks);
        if (experimentPeaks.getId() != null) {
            throw new BadRequestAlertException("A new experimentPeaks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperimentPeaks result = experimentPeaksRepository.save(experimentPeaks);
        return ResponseEntity.created(new URI("/api/experiment-peaks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiment-peaks : Updates an existing experimentPeaks.
     *
     * @param experimentPeaks the experimentPeaks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experimentPeaks,
     * or with status 400 (Bad Request) if the experimentPeaks is not valid,
     * or with status 500 (Internal Server Error) if the experimentPeaks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiment-peaks")
    public ResponseEntity<ExperimentPeaks> updateExperimentPeaks(@RequestBody ExperimentPeaks experimentPeaks) throws URISyntaxException {
        log.debug("REST request to update ExperimentPeaks : {}", experimentPeaks);
        if (experimentPeaks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperimentPeaks result = experimentPeaksRepository.save(experimentPeaks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experimentPeaks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiment-peaks : get all the experimentPeaks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experimentPeaks in body
     */
    @GetMapping("/experiment-peaks")
    public List<ExperimentPeaks> getAllExperimentPeaks() {
        log.debug("REST request to get all ExperimentPeaks");
        return experimentPeaksRepository.findAll();
    }

    /**
     * GET  /experiment-peaks/:id : get the "id" experimentPeaks.
     *
     * @param id the id of the experimentPeaks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experimentPeaks, or with status 404 (Not Found)
     */
    @GetMapping("/experiment-peaks/{id}")
    public ResponseEntity<ExperimentPeaks> getExperimentPeaks(@PathVariable Long id) {
        log.debug("REST request to get ExperimentPeaks : {}", id);
        Optional<ExperimentPeaks> experimentPeaks = experimentPeaksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experimentPeaks);
    }

    /**
     * DELETE  /experiment-peaks/:id : delete the "id" experimentPeaks.
     *
     * @param id the id of the experimentPeaks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiment-peaks/{id}")
    public ResponseEntity<Void> deleteExperimentPeaks(@PathVariable Long id) {
        log.debug("REST request to delete ExperimentPeaks : {}", id);
        experimentPeaksRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
