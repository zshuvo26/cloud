package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.UpazilaService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.UpazilaDTO;
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
 * REST controller for managing Upazila.
 */
@RestController
@RequestMapping("/api")
public class UpazilaResource {

    private final Logger log = LoggerFactory.getLogger(UpazilaResource.class);

    private static final String ENTITY_NAME = "upazila";

    private final UpazilaService upazilaService;

    public UpazilaResource(UpazilaService upazilaService) {
        this.upazilaService = upazilaService;
    }

    /**
     * POST  /upazilas : Create a new upazila.
     *
     * @param upazilaDTO the upazilaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upazilaDTO, or with status 400 (Bad Request) if the upazila has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/upazilas")
    @Timed
    public ResponseEntity<UpazilaDTO> createUpazila(@Valid @RequestBody UpazilaDTO upazilaDTO) throws URISyntaxException {
        log.debug("REST request to save Upazila : {}", upazilaDTO);
        if (upazilaDTO.getId() != null) {
            throw new BadRequestAlertException("A new upazila cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpazilaDTO result = upazilaService.save(upazilaDTO);
        return ResponseEntity.created(new URI("/api/upazilas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upazilas : Updates an existing upazila.
     *
     * @param upazilaDTO the upazilaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upazilaDTO,
     * or with status 400 (Bad Request) if the upazilaDTO is not valid,
     * or with status 500 (Internal Server Error) if the upazilaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/upazilas")
    @Timed
    public ResponseEntity<UpazilaDTO> updateUpazila(@Valid @RequestBody UpazilaDTO upazilaDTO) throws URISyntaxException {
        log.debug("REST request to update Upazila : {}", upazilaDTO);
        if (upazilaDTO.getId() == null) {
            return createUpazila(upazilaDTO);
        }
        UpazilaDTO result = upazilaService.save(upazilaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, upazilaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upazilas : get all the upazilas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upazilas in body
     */
    @GetMapping("/upazilas")
    @Timed
    public ResponseEntity<List<UpazilaDTO>> getAllUpazilas(Pageable pageable) {
        log.debug("REST request to get a page of Upazilas");
        Page<UpazilaDTO> page = upazilaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/upazilas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /upazilas/:id : get the "id" upazila.
     *
     * @param id the id of the upazilaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upazilaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upazilas/{id}")
    @Timed
    public ResponseEntity<UpazilaDTO> getUpazila(@PathVariable Long id) {
        log.debug("REST request to get Upazila : {}", id);
        UpazilaDTO upazilaDTO = upazilaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(upazilaDTO));
    }

    /**
     * DELETE  /upazilas/:id : delete the "id" upazila.
     *
     * @param id the id of the upazilaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/upazilas/{id}")
    @Timed
    public ResponseEntity<Void> deleteUpazila(@PathVariable Long id) {
        log.debug("REST request to delete Upazila : {}", id);
        upazilaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
