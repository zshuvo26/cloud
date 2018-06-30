package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.FileTypeService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.FileTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FileType.
 */
@RestController
@RequestMapping("/api")
public class FileTypeResource {

    private final Logger log = LoggerFactory.getLogger(FileTypeResource.class);

    private static final String ENTITY_NAME = "fileType";

    private final FileTypeService fileTypeService;

    public FileTypeResource(FileTypeService fileTypeService) {
        this.fileTypeService = fileTypeService;
    }

    /**
     * POST  /file-types : Create a new fileType.
     *
     * @param fileTypeDTO the fileTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileTypeDTO, or with status 400 (Bad Request) if the fileType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-types")
    @Timed
    public ResponseEntity<FileTypeDTO> createFileType(@RequestBody FileTypeDTO fileTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FileType : {}", fileTypeDTO);
        if (fileTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        return ResponseEntity.created(new URI("/api/file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-types : Updates an existing fileType.
     *
     * @param fileTypeDTO the fileTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileTypeDTO,
     * or with status 400 (Bad Request) if the fileTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-types")
    @Timed
    public ResponseEntity<FileTypeDTO> updateFileType(@RequestBody FileTypeDTO fileTypeDTO) throws URISyntaxException {
        log.debug("REST request to update FileType : {}", fileTypeDTO);
        if (fileTypeDTO.getId() == null) {
            return createFileType(fileTypeDTO);
        }
        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-types : get all the fileTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileTypes in body
     */
    @GetMapping("/file-types")
    @Timed
    public ResponseEntity<List<FileTypeDTO>> getAllFileTypes(Pageable pageable) {
        log.debug("REST request to get a page of FileTypes");
        Page<FileTypeDTO> page = fileTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-types/:id : get the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-types/{id}")
    @Timed
    public ResponseEntity<FileTypeDTO> getFileType(@PathVariable Long id) {
        log.debug("REST request to get FileType : {}", id);
        FileTypeDTO fileTypeDTO = fileTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileTypeDTO));
    }

    /**
     * DELETE  /file-types/:id : delete the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileType(@PathVariable Long id) {
        log.debug("REST request to delete FileType : {}", id);
        fileTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
