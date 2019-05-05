package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Experiment;
import com.mycompany.myapp.repository.ExperimentRepository;
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
 * REST controller for managing Experiment.
 */
@RestController
@RequestMapping("/api")
public class ExperimentResource {

    private final Logger log = LoggerFactory.getLogger(ExperimentResource.class);

    private static final String ENTITY_NAME = "experiment";

    private final ExperimentRepository experimentRepository;

    public ExperimentResource(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    /**
     * POST  /experiments : Create a new experiment.
     *
     * @param experiment the experiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experiment, or with status 400 (Bad Request) if the experiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiments")
    public ResponseEntity<Experiment> createExperiment(@Valid @RequestBody Experiment experiment) throws URISyntaxException {
        log.debug("REST request to save Experiment : {}", experiment);
        if (experiment.getId() != null) {
            throw new BadRequestAlertException("A new experiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Experiment result = experimentRepository.save(experiment);
        return ResponseEntity.created(new URI("/api/experiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiments : Updates an existing experiment.
     *
     * @param experiment the experiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experiment,
     * or with status 400 (Bad Request) if the experiment is not valid,
     * or with status 500 (Internal Server Error) if the experiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiments")
    public ResponseEntity<Experiment> updateExperiment(@Valid @RequestBody Experiment experiment) throws URISyntaxException {
        log.debug("REST request to update Experiment : {}", experiment);
        if (experiment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Experiment result = experimentRepository.save(experiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiments : get all the experiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experiments in body
     */
    @GetMapping("/experiments")
    public List<Experiment> getAllExperiments() {
        log.debug("REST request to get all Experiments");
        return experimentRepository.findAll();
    }

    /**
     * GET  /experiments/:id : get the "id" experiment.
     *
     * @param id the id of the experiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experiment, or with status 404 (Not Found)
     */
    @GetMapping("/experiments/{id}")
    public ResponseEntity<Experiment> getExperiment(@PathVariable Long id) {
        log.debug("REST request to get Experiment : {}", id);
        Optional<Experiment> experiment = experimentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experiment);
    }

    /**
     * DELETE  /experiments/:id : delete the "id" experiment.
     *
     * @param id the id of the experiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiments/{id}")
    public ResponseEntity<Void> deleteExperiment(@PathVariable Long id) {
        log.debug("REST request to delete Experiment : {}", id);
        experimentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
