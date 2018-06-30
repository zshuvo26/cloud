package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookRequisitionService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookRequisitionDTO;
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
 * REST controller for managing BookRequisition.
 */
@RestController
@RequestMapping("/api")
public class BookRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(BookRequisitionResource.class);

    private static final String ENTITY_NAME = "bookRequisition";

    private final BookRequisitionService bookRequisitionService;

    public BookRequisitionResource(BookRequisitionService bookRequisitionService) {
        this.bookRequisitionService = bookRequisitionService;
    }

    /**
     * POST  /book-requisitions : Create a new bookRequisition.
     *
     * @param bookRequisitionDTO the bookRequisitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookRequisitionDTO, or with status 400 (Bad Request) if the bookRequisition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-requisitions")
    @Timed
    public ResponseEntity<BookRequisitionDTO> createBookRequisition(@Valid @RequestBody BookRequisitionDTO bookRequisitionDTO) throws URISyntaxException {
        log.debug("REST request to save BookRequisition : {}", bookRequisitionDTO);
        if (bookRequisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookRequisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookRequisitionDTO result = bookRequisitionService.save(bookRequisitionDTO);
        return ResponseEntity.created(new URI("/api/book-requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-requisitions : Updates an existing bookRequisition.
     *
     * @param bookRequisitionDTO the bookRequisitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookRequisitionDTO,
     * or with status 400 (Bad Request) if the bookRequisitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookRequisitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-requisitions")
    @Timed
    public ResponseEntity<BookRequisitionDTO> updateBookRequisition(@Valid @RequestBody BookRequisitionDTO bookRequisitionDTO) throws URISyntaxException {
        log.debug("REST request to update BookRequisition : {}", bookRequisitionDTO);
        if (bookRequisitionDTO.getId() == null) {
            return createBookRequisition(bookRequisitionDTO);
        }
        BookRequisitionDTO result = bookRequisitionService.save(bookRequisitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookRequisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-requisitions : get all the bookRequisitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookRequisitions in body
     */
    @GetMapping("/book-requisitions")
    @Timed
    public ResponseEntity<List<BookRequisitionDTO>> getAllBookRequisitions(Pageable pageable) {
        log.debug("REST request to get a page of BookRequisitions");
        Page<BookRequisitionDTO> page = bookRequisitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-requisitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-requisitions/:id : get the "id" bookRequisition.
     *
     * @param id the id of the bookRequisitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookRequisitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-requisitions/{id}")
    @Timed
    public ResponseEntity<BookRequisitionDTO> getBookRequisition(@PathVariable Long id) {
        log.debug("REST request to get BookRequisition : {}", id);
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookRequisitionDTO));
    }

    /**
     * DELETE  /book-requisitions/:id : delete the "id" bookRequisition.
     *
     * @param id the id of the bookRequisitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-requisitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookRequisition(@PathVariable Long id) {
        log.debug("REST request to delete BookRequisition : {}", id);
        bookRequisitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
