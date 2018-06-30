package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookTypeService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookTypeDTO;
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
 * REST controller for managing BookType.
 */
@RestController
@RequestMapping("/api")
public class BookTypeResource {

    private final Logger log = LoggerFactory.getLogger(BookTypeResource.class);

    private static final String ENTITY_NAME = "bookType";

    private final BookTypeService bookTypeService;

    public BookTypeResource(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }

    /**
     * POST  /book-types : Create a new bookType.
     *
     * @param bookTypeDTO the bookTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookTypeDTO, or with status 400 (Bad Request) if the bookType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-types")
    @Timed
    public ResponseEntity<BookTypeDTO> createBookType(@RequestBody BookTypeDTO bookTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BookType : {}", bookTypeDTO);
        if (bookTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookTypeDTO result = bookTypeService.save(bookTypeDTO);
        return ResponseEntity.created(new URI("/api/book-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-types : Updates an existing bookType.
     *
     * @param bookTypeDTO the bookTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookTypeDTO,
     * or with status 400 (Bad Request) if the bookTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-types")
    @Timed
    public ResponseEntity<BookTypeDTO> updateBookType(@RequestBody BookTypeDTO bookTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BookType : {}", bookTypeDTO);
        if (bookTypeDTO.getId() == null) {
            return createBookType(bookTypeDTO);
        }
        BookTypeDTO result = bookTypeService.save(bookTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-types : get all the bookTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookTypes in body
     */
    @GetMapping("/book-types")
    @Timed
    public ResponseEntity<List<BookTypeDTO>> getAllBookTypes(Pageable pageable) {
        log.debug("REST request to get a page of BookTypes");
        Page<BookTypeDTO> page = bookTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-types/:id : get the "id" bookType.
     *
     * @param id the id of the bookTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-types/{id}")
    @Timed
    public ResponseEntity<BookTypeDTO> getBookType(@PathVariable Long id) {
        log.debug("REST request to get BookType : {}", id);
        BookTypeDTO bookTypeDTO = bookTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookTypeDTO));
    }

    /**
     * DELETE  /book-types/:id : delete the "id" bookType.
     *
     * @param id the id of the bookTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookType(@PathVariable Long id) {
        log.debug("REST request to delete BookType : {}", id);
        bookTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
