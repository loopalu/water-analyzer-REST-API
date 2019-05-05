package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.BGEComposition;
import com.mycompany.myapp.repository.BGECompositionRepository;
import com.mycompany.myapp.security.SecurityUtils;
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

import static com.mycompany.myapp.security.AuthoritiesConstants.ADMIN;

/**
 * REST controller for managing BGEComposition.
 */
@RestController
@RequestMapping("/api")
public class BGECompositionResource {

    private final Logger log = LoggerFactory.getLogger(BGECompositionResource.class);

    private static final String ENTITY_NAME = "bGEComposition";

    private final BGECompositionRepository bGECompositionRepository;

    public BGECompositionResource(BGECompositionRepository bGECompositionRepository) {
        this.bGECompositionRepository = bGECompositionRepository;
    }

    /**
     * POST  /bge-compositions : Create a new bGEComposition.
     *
     * @param bGEComposition the bGEComposition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bGEComposition, or with status 400 (Bad Request) if the bGEComposition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bge-compositions")
    public ResponseEntity<BGEComposition> createBGEComposition(@RequestBody BGEComposition bGEComposition) throws URISyntaxException {
            if (!SecurityUtils.isCurrentUserInRole(ADMIN)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"not-authenticated","You need to be logged in to perform this action.")).body(null);
            }
        log.debug("REST request to save BGEComposition : {}", bGEComposition);
        if (bGEComposition.getId() != null) {
            throw new BadRequestAlertException("A new bGEComposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BGEComposition result = bGECompositionRepository.save(bGEComposition);
        return ResponseEntity.created(new URI("/api/bge-compositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bge-compositions : Updates an existing bGEComposition.
     *
     * @param bGEComposition the bGEComposition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bGEComposition,
     * or with status 400 (Bad Request) if the bGEComposition is not valid,
     * or with status 500 (Internal Server Error) if the bGEComposition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bge-compositions")
    public ResponseEntity<BGEComposition> updateBGEComposition(@RequestBody BGEComposition bGEComposition) throws URISyntaxException {
        if (!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"not-authenticated","You need to be logged in to perform this action.")).body(null);
        }
        log.debug("REST request to update BGEComposition : {}", bGEComposition);
        if (bGEComposition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BGEComposition result = bGECompositionRepository.save(bGEComposition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bGEComposition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bge-compositions : get all the bGECompositions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bGECompositions in body
     */
    @GetMapping("/bge-compositions")
    public List<BGEComposition> getAllBGECompositions() {
        log.debug("REST request to get all BGECompositions");
        return bGECompositionRepository.findAll();
    }

    /**
     * GET  /bge-compositions/:id : get the "id" bGEComposition.
     *
     * @param id the id of the bGEComposition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bGEComposition, or with status 404 (Not Found)
     */
    @GetMapping("/bge-compositions/{id}")
    public ResponseEntity<BGEComposition> getBGEComposition(@PathVariable Long id) {
        log.debug("REST request to get BGEComposition : {}", id);
        Optional<BGEComposition> bGEComposition = bGECompositionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bGEComposition);
    }

    /**
     * DELETE  /bge-compositions/:id : delete the "id" bGEComposition.
     *
     * @param id the id of the bGEComposition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bge-compositions/{id}")
    public ResponseEntity<Void> deleteBGEComposition(@PathVariable Long id) {
        log.debug("REST request to delete BGEComposition : {}", id);
        bGECompositionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
