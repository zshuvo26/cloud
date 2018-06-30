package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookFineSettingService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookFineSettingDTO;
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
 * REST controller for managing BookFineSetting.
 */
@RestController
@RequestMapping("/api")
public class BookFineSettingResource {

    private final Logger log = LoggerFactory.getLogger(BookFineSettingResource.class);

    private static final String ENTITY_NAME = "bookFineSetting";

    private final BookFineSettingService bookFineSettingService;

    public BookFineSettingResource(BookFineSettingService bookFineSettingService) {
        this.bookFineSettingService = bookFineSettingService;
    }

    /**
     * POST  /book-fine-settings : Create a new bookFineSetting.
     *
     * @param bookFineSettingDTO the bookFineSettingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookFineSettingDTO, or with status 400 (Bad Request) if the bookFineSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-fine-settings")
    @Timed
    public ResponseEntity<BookFineSettingDTO> createBookFineSetting(@RequestBody BookFineSettingDTO bookFineSettingDTO) throws URISyntaxException {
        log.debug("REST request to save BookFineSetting : {}", bookFineSettingDTO);
        if (bookFineSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookFineSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookFineSettingDTO result = bookFineSettingService.save(bookFineSettingDTO);
        return ResponseEntity.created(new URI("/api/book-fine-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-fine-settings : Updates an existing bookFineSetting.
     *
     * @param bookFineSettingDTO the bookFineSettingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookFineSettingDTO,
     * or with status 400 (Bad Request) if the bookFineSettingDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookFineSettingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-fine-settings")
    @Timed
    public ResponseEntity<BookFineSettingDTO> updateBookFineSetting(@RequestBody BookFineSettingDTO bookFineSettingDTO) throws URISyntaxException {
        log.debug("REST request to update BookFineSetting : {}", bookFineSettingDTO);
        if (bookFineSettingDTO.getId() == null) {
            return createBookFineSetting(bookFineSettingDTO);
        }
        BookFineSettingDTO result = bookFineSettingService.save(bookFineSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookFineSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-fine-settings : get all the bookFineSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookFineSettings in body
     */
    @GetMapping("/book-fine-settings")
    @Timed
    public ResponseEntity<List<BookFineSettingDTO>> getAllBookFineSettings(Pageable pageable) {
        log.debug("REST request to get a page of BookFineSettings");
        Page<BookFineSettingDTO> page = bookFineSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-fine-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-fine-settings/:id : get the "id" bookFineSetting.
     *
     * @param id the id of the bookFineSettingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookFineSettingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-fine-settings/{id}")
    @Timed
    public ResponseEntity<BookFineSettingDTO> getBookFineSetting(@PathVariable Long id) {
        log.debug("REST request to get BookFineSetting : {}", id);
        BookFineSettingDTO bookFineSettingDTO = bookFineSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookFineSettingDTO));
    }

    /**
     * DELETE  /book-fine-settings/:id : delete the "id" bookFineSetting.
     *
     * @param id the id of the bookFineSettingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-fine-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookFineSetting(@PathVariable Long id) {
        log.debug("REST request to delete BookFineSetting : {}", id);
        bookFineSettingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
