package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Method;
import com.mycompany.myapp.repository.MethodRepository;
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
 * REST controller for managing Method.
 */
@RestController
@RequestMapping("/api")
public class MethodResource {

    private final Logger log = LoggerFactory.getLogger(MethodResource.class);

    private static final String ENTITY_NAME = "method";

    private final MethodRepository methodRepository;

    public MethodResource(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    /**
     * POST  /methods : Create a new method.
     *
     * @param method the method to create
     * @return the ResponseEntity with status 201 (Created) and with body the new method, or with status 400 (Bad Request) if the method has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/methods")
    public ResponseEntity<Method> createMethod(@Valid @RequestBody Method method) throws URISyntaxException {
        log.debug("REST request to save Method : {}", method);
        if (method.getId() != null) {
            throw new BadRequestAlertException("A new method cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Method result = methodRepository.save(method);
        return ResponseEntity.created(new URI("/api/methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /methods : Updates an existing method.
     *
     * @param method the method to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated method,
     * or with status 400 (Bad Request) if the method is not valid,
     * or with status 500 (Internal Server Error) if the method couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/methods")
    public ResponseEntity<Method> updateMethod(@Valid @RequestBody Method method) throws URISyntaxException {
        log.debug("REST request to update Method : {}", method);
        if (method.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Method result = methodRepository.save(method);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, method.getId().toString()))
            .body(result);
    }

    /**
     * GET  /methods : get all the methods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of methods in body
     */
    @GetMapping("/methods")
    public List<Method> getAllMethods() {
        log.debug("REST request to get all Methods");
        return methodRepository.findAll();
    }

    /**
     * GET  /methods/:id : get the "id" method.
     *
     * @param id the id of the method to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the method, or with status 404 (Not Found)
     */
    @GetMapping("/methods/{id}")
    public ResponseEntity<Method> getMethod(@PathVariable Long id) {
        log.debug("REST request to get Method : {}", id);
        Optional<Method> method = methodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(method);
    }

    /**
     * DELETE  /methods/:id : delete the "id" method.
     *
     * @param id the id of the method to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/methods/{id}")
    public ResponseEntity<Void> deleteMethod(@PathVariable Long id) {
        log.debug("REST request to delete Method : {}", id);
        methodRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
