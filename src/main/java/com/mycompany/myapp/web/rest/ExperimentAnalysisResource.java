package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.ExperimentAnalysis;
import com.mycompany.myapp.repository.ExperimentAnalysisRepository;
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
 * REST controller for managing ExperimentAnalysis.
 */
@RestController
@RequestMapping("/api")
public class ExperimentAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(ExperimentAnalysisResource.class);

    private static final String ENTITY_NAME = "experimentAnalysis";

    private final ExperimentAnalysisRepository experimentAnalysisRepository;

    public ExperimentAnalysisResource(ExperimentAnalysisRepository experimentAnalysisRepository) {
        this.experimentAnalysisRepository = experimentAnalysisRepository;
    }

    /**
     * POST  /experiment-analyses : Create a new experimentAnalysis.
     *
     * @param experimentAnalysis the experimentAnalysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experimentAnalysis, or with status 400 (Bad Request) if the experimentAnalysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiment-analyses")
    public ResponseEntity<ExperimentAnalysis> createExperimentAnalysis(@Valid @RequestBody ExperimentAnalysis experimentAnalysis) throws URISyntaxException {
        log.debug("REST request to save ExperimentAnalysis : {}", experimentAnalysis);
        if (experimentAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new experimentAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperimentAnalysis result = experimentAnalysisRepository.save(experimentAnalysis);
        return ResponseEntity.created(new URI("/api/experiment-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiment-analyses : Updates an existing experimentAnalysis.
     *
     * @param experimentAnalysis the experimentAnalysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experimentAnalysis,
     * or with status 400 (Bad Request) if the experimentAnalysis is not valid,
     * or with status 500 (Internal Server Error) if the experimentAnalysis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiment-analyses")
    public ResponseEntity<ExperimentAnalysis> updateExperimentAnalysis(@Valid @RequestBody ExperimentAnalysis experimentAnalysis) throws URISyntaxException {
        log.debug("REST request to update ExperimentAnalysis : {}", experimentAnalysis);
        if (experimentAnalysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperimentAnalysis result = experimentAnalysisRepository.save(experimentAnalysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experimentAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiment-analyses : get all the experimentAnalyses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experimentAnalyses in body
     */
    @GetMapping("/experiment-analyses")
    public List<ExperimentAnalysis> getAllExperimentAnalyses() {
        log.debug("REST request to get all ExperimentAnalyses");
        return experimentAnalysisRepository.findAll();
    }

    /**
     * GET  /experiment-analyses/:id : get the "id" experimentAnalysis.
     *
     * @param id the id of the experimentAnalysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experimentAnalysis, or with status 404 (Not Found)
     */
    @GetMapping("/experiment-analyses/{id}")
    public ResponseEntity<ExperimentAnalysis> getExperimentAnalysis(@PathVariable Long id) {
        log.debug("REST request to get ExperimentAnalysis : {}", id);
        Optional<ExperimentAnalysis> experimentAnalysis = experimentAnalysisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experimentAnalysis);
    }

    /**
     * DELETE  /experiment-analyses/:id : delete the "id" experimentAnalysis.
     *
     * @param id the id of the experimentAnalysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiment-analyses/{id}")
    public ResponseEntity<Void> deleteExperimentAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete ExperimentAnalysis : {}", id);
        experimentAnalysisRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
