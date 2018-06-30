package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.DigitalContentService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.DigitalContentDTO;
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
 * REST controller for managing DigitalContent.
 */
@RestController
@RequestMapping("/api")
public class DigitalContentResource {

    private final Logger log = LoggerFactory.getLogger(DigitalContentResource.class);

    private static final String ENTITY_NAME = "digitalContent";

    private final DigitalContentService digitalContentService;

    public DigitalContentResource(DigitalContentService digitalContentService) {
        this.digitalContentService = digitalContentService;
    }

    /**
     * POST  /digital-contents : Create a new digitalContent.
     *
     * @param digitalContentDTO the digitalContentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new digitalContentDTO, or with status 400 (Bad Request) if the digitalContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/digital-contents")
    @Timed
    public ResponseEntity<DigitalContentDTO> createDigitalContent(@Valid @RequestBody DigitalContentDTO digitalContentDTO) throws URISyntaxException {
        log.debug("REST request to save DigitalContent : {}", digitalContentDTO);
        if (digitalContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new digitalContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DigitalContentDTO result = digitalContentService.save(digitalContentDTO);
        return ResponseEntity.created(new URI("/api/digital-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /digital-contents : Updates an existing digitalContent.
     *
     * @param digitalContentDTO the digitalContentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated digitalContentDTO,
     * or with status 400 (Bad Request) if the digitalContentDTO is not valid,
     * or with status 500 (Internal Server Error) if the digitalContentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/digital-contents")
    @Timed
    public ResponseEntity<DigitalContentDTO> updateDigitalContent(@Valid @RequestBody DigitalContentDTO digitalContentDTO) throws URISyntaxException {
        log.debug("REST request to update DigitalContent : {}", digitalContentDTO);
        if (digitalContentDTO.getId() == null) {
            return createDigitalContent(digitalContentDTO);
        }
        DigitalContentDTO result = digitalContentService.save(digitalContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, digitalContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /digital-contents : get all the digitalContents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of digitalContents in body
     */
    @GetMapping("/digital-contents")
    @Timed
    public ResponseEntity<List<DigitalContentDTO>> getAllDigitalContents(Pageable pageable) {
        log.debug("REST request to get a page of DigitalContents");
        Page<DigitalContentDTO> page = digitalContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/digital-contents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /digital-contents/:id : get the "id" digitalContent.
     *
     * @param id the id of the digitalContentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the digitalContentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/digital-contents/{id}")
    @Timed
    public ResponseEntity<DigitalContentDTO> getDigitalContent(@PathVariable Long id) {
        log.debug("REST request to get DigitalContent : {}", id);
        DigitalContentDTO digitalContentDTO = digitalContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(digitalContentDTO));
    }

    /**
     * DELETE  /digital-contents/:id : delete the "id" digitalContent.
     *
     * @param id the id of the digitalContentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/digital-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteDigitalContent(@PathVariable Long id) {
        log.debug("REST request to delete DigitalContent : {}", id);
        digitalContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
