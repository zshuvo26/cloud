package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookReturnService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookReturnDTO;
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
 * REST controller for managing BookReturn.
 */
@RestController
@RequestMapping("/api")
public class BookReturnResource {

    private final Logger log = LoggerFactory.getLogger(BookReturnResource.class);

    private static final String ENTITY_NAME = "bookReturn";

    private final BookReturnService bookReturnService;

    public BookReturnResource(BookReturnService bookReturnService) {
        this.bookReturnService = bookReturnService;
    }

    /**
     * POST  /book-returns : Create a new bookReturn.
     *
     * @param bookReturnDTO the bookReturnDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookReturnDTO, or with status 400 (Bad Request) if the bookReturn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-returns")
    @Timed
    public ResponseEntity<BookReturnDTO> createBookReturn(@RequestBody BookReturnDTO bookReturnDTO) throws URISyntaxException {
        log.debug("REST request to save BookReturn : {}", bookReturnDTO);
        if (bookReturnDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookReturn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookReturnDTO result = bookReturnService.save(bookReturnDTO);
        return ResponseEntity.created(new URI("/api/book-returns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-returns : Updates an existing bookReturn.
     *
     * @param bookReturnDTO the bookReturnDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookReturnDTO,
     * or with status 400 (Bad Request) if the bookReturnDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookReturnDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-returns")
    @Timed
    public ResponseEntity<BookReturnDTO> updateBookReturn(@RequestBody BookReturnDTO bookReturnDTO) throws URISyntaxException {
        log.debug("REST request to update BookReturn : {}", bookReturnDTO);
        if (bookReturnDTO.getId() == null) {
            return createBookReturn(bookReturnDTO);
        }
        BookReturnDTO result = bookReturnService.save(bookReturnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookReturnDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-returns : get all the bookReturns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookReturns in body
     */
    @GetMapping("/book-returns")
    @Timed
    public ResponseEntity<List<BookReturnDTO>> getAllBookReturns(Pageable pageable) {
        log.debug("REST request to get a page of BookReturns");
        Page<BookReturnDTO> page = bookReturnService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-returns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-returns/:id : get the "id" bookReturn.
     *
     * @param id the id of the bookReturnDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookReturnDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-returns/{id}")
    @Timed
    public ResponseEntity<BookReturnDTO> getBookReturn(@PathVariable Long id) {
        log.debug("REST request to get BookReturn : {}", id);
        BookReturnDTO bookReturnDTO = bookReturnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookReturnDTO));
    }

    /**
     * DELETE  /book-returns/:id : delete the "id" bookReturn.
     *
     * @param id the id of the bookReturnDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-returns/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookReturn(@PathVariable Long id) {
        log.debug("REST request to delete BookReturn : {}", id);
        bookReturnService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
