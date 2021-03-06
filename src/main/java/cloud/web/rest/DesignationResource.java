package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.DesignationService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.DesignationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Designation.
 */
@RestController
@RequestMapping("/api")
public class DesignationResource {

    private final Logger log = LoggerFactory.getLogger(DesignationResource.class);

    private static final String ENTITY_NAME = "designation";

    private final DesignationService designationService;

    public DesignationResource(DesignationService designationService) {
        this.designationService = designationService;
    }

    /**
     * POST  /designations : Create a new designation.
     *
     * @param designationDTO the designationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designationDTO, or with status 400 (Bad Request) if the designation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designations")
    @Timed
    public ResponseEntity<DesignationDTO> createDesignation(@Valid @RequestBody DesignationDTO designationDTO) throws URISyntaxException {
        log.debug("REST request to save Designation : {}", designationDTO);
        if (designationDTO.getId() != null) {
            throw new BadRequestAlertException("A new designation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignationDTO result = designationService.save(designationDTO);
        return ResponseEntity.created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designations : Updates an existing designation.
     *
     * @param designationDTO the designationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designationDTO,
     * or with status 400 (Bad Request) if the designationDTO is not valid,
     * or with status 500 (Internal Server Error) if the designationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designations")
    @Timed
    public ResponseEntity<DesignationDTO> updateDesignation(@Valid @RequestBody DesignationDTO designationDTO) throws URISyntaxException {
        log.debug("REST request to update Designation : {}", designationDTO);
        if (designationDTO.getId() == null) {
            return createDesignation(designationDTO);
        }
        DesignationDTO result = designationService.save(designationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designations : get all the designations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designations in body
     */
    @GetMapping("/designations")
    @Timed
    public ResponseEntity<List<DesignationDTO>> getAllDesignations(Pageable pageable) {
        log.debug("REST request to get a page of Designations");
        Page<DesignationDTO> page = designationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designations/:id : get the "id" designation.
     *
     * @param id the id of the designationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/designations/{id}")
    @Timed
    public ResponseEntity<DesignationDTO> getDesignation(@PathVariable Long id) {
        log.debug("REST request to get Designation : {}", id);
        DesignationDTO designationDTO = designationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designationDTO));
    }

    /**
     * DELETE  /designations/:id : delete the "id" designation.
     *
     * @param id the id of the designationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        log.debug("REST request to delete Designation : {}", id);
        designationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
