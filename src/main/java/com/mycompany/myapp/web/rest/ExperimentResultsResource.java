package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.ExperimentResults;
import com.mycompany.myapp.repository.ExperimentResultsRepository;
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
 * REST controller for managing ExperimentResults.
 */
@RestController
@RequestMapping("/api")
public class ExperimentResultsResource {

    private final Logger log = LoggerFactory.getLogger(ExperimentResultsResource.class);

    private static final String ENTITY_NAME = "experimentResults";

    private final ExperimentResultsRepository experimentResultsRepository;

    public ExperimentResultsResource(ExperimentResultsRepository experimentResultsRepository) {
        this.experimentResultsRepository = experimentResultsRepository;
    }

    /**
     * POST  /experiment-results : Create a new experimentResults.
     *
     * @param experimentResults the experimentResults to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experimentResults, or with status 400 (Bad Request) if the experimentResults has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiment-results")
    public ResponseEntity<ExperimentResults> createExperimentResults(@RequestBody ExperimentResults experimentResults) throws URISyntaxException {
        log.debug("REST request to save ExperimentResults : {}", experimentResults);
        if (experimentResults.getId() != null) {
            throw new BadRequestAlertException("A new experimentResults cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperimentResults result = experimentResultsRepository.save(experimentResults);
        return ResponseEntity.created(new URI("/api/experiment-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiment-results : Updates an existing experimentResults.
     *
     * @param experimentResults the experimentResults to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experimentResults,
     * or with status 400 (Bad Request) if the experimentResults is not valid,
     * or with status 500 (Internal Server Error) if the experimentResults couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiment-results")
    public ResponseEntity<ExperimentResults> updateExperimentResults(@RequestBody ExperimentResults experimentResults) throws URISyntaxException {
        log.debug("REST request to update ExperimentResults : {}", experimentResults);
        if (experimentResults.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperimentResults result = experimentResultsRepository.save(experimentResults);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experimentResults.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiment-results : get all the experimentResults.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experimentResults in body
     */
    @GetMapping("/experiment-results")
    public List<ExperimentResults> getAllExperimentResults() {
        log.debug("REST request to get all ExperimentResults");
        return experimentResultsRepository.findAll();
    }

    /**
     * GET  /experiment-results/:id : get the "id" experimentResults.
     *
     * @param id the id of the experimentResults to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experimentResults, or with status 404 (Not Found)
     */
    @GetMapping("/experiment-results/{id}")
    public ResponseEntity<ExperimentResults> getExperimentResults(@PathVariable Long id) {
        log.debug("REST request to get ExperimentResults : {}", id);
        Optional<ExperimentResults> experimentResults = experimentResultsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experimentResults);
    }

    /**
     * DELETE  /experiment-results/:id : delete the "id" experimentResults.
     *
     * @param id the id of the experimentResults to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiment-results/{id}")
    public ResponseEntity<Void> deleteExperimentResults(@PathVariable Long id) {
        log.debug("REST request to delete ExperimentResults : {}", id);
        experimentResultsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
