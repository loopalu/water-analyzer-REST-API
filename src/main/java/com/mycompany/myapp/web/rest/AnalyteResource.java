package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Analyte;
import com.mycompany.myapp.repository.AnalyteRepository;
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
 * REST controller for managing Analyte.
 */
@RestController
@RequestMapping("/api")
public class AnalyteResource {

    private final Logger log = LoggerFactory.getLogger(AnalyteResource.class);

    private static final String ENTITY_NAME = "analyte";

    private final AnalyteRepository analyteRepository;

    public AnalyteResource(AnalyteRepository analyteRepository) {
        this.analyteRepository = analyteRepository;
    }

    /**
     * POST  /analytes : Create a new analyte.
     *
     * @param analyte the analyte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analyte, or with status 400 (Bad Request) if the analyte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/analytes")
    public ResponseEntity<Analyte> createAnalyte(@Valid @RequestBody Analyte analyte) throws URISyntaxException {
        log.debug("REST request to save Analyte : {}", analyte);
        if (analyte.getId() != null) {
            throw new BadRequestAlertException("A new analyte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Analyte result = analyteRepository.save(analyte);
        return ResponseEntity.created(new URI("/api/analytes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analytes : Updates an existing analyte.
     *
     * @param analyte the analyte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analyte,
     * or with status 400 (Bad Request) if the analyte is not valid,
     * or with status 500 (Internal Server Error) if the analyte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/analytes")
    public ResponseEntity<Analyte> updateAnalyte(@Valid @RequestBody Analyte analyte) throws URISyntaxException {
        log.debug("REST request to update Analyte : {}", analyte);
        if (analyte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Analyte result = analyteRepository.save(analyte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, analyte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analytes : get all the analytes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of analytes in body
     */
    @GetMapping("/analytes")
    public List<Analyte> getAllAnalytes() {
        log.debug("REST request to get all Analytes");
        return analyteRepository.findAll();
    }

    /**
     * GET  /analytes/:id : get the "id" analyte.
     *
     * @param id the id of the analyte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analyte, or with status 404 (Not Found)
     */
    @GetMapping("/analytes/{id}")
    public ResponseEntity<Analyte> getAnalyte(@PathVariable Long id) {
        log.debug("REST request to get Analyte : {}", id);
        Optional<Analyte> analyte = analyteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(analyte);
    }

    /**
     * DELETE  /analytes/:id : delete the "id" analyte.
     *
     * @param id the id of the analyte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/analytes/{id}")
    public ResponseEntity<Void> deleteAnalyte(@PathVariable Long id) {
        log.debug("REST request to delete Analyte : {}", id);
        analyteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
