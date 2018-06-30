package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookCategoryService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookCategoryDTO;
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
 * REST controller for managing BookCategory.
 */
@RestController
@RequestMapping("/api")
public class BookCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BookCategoryResource.class);

    private static final String ENTITY_NAME = "bookCategory";

    private final BookCategoryService bookCategoryService;

    public BookCategoryResource(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    /**
     * POST  /book-categories : Create a new bookCategory.
     *
     * @param bookCategoryDTO the bookCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookCategoryDTO, or with status 400 (Bad Request) if the bookCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-categories")
    @Timed
    public ResponseEntity<BookCategoryDTO> createBookCategory(@RequestBody BookCategoryDTO bookCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save BookCategory : {}", bookCategoryDTO);
        if (bookCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookCategoryDTO result = bookCategoryService.save(bookCategoryDTO);
        return ResponseEntity.created(new URI("/api/book-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-categories : Updates an existing bookCategory.
     *
     * @param bookCategoryDTO the bookCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookCategoryDTO,
     * or with status 400 (Bad Request) if the bookCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-categories")
    @Timed
    public ResponseEntity<BookCategoryDTO> updateBookCategory(@RequestBody BookCategoryDTO bookCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update BookCategory : {}", bookCategoryDTO);
        if (bookCategoryDTO.getId() == null) {
            return createBookCategory(bookCategoryDTO);
        }
        BookCategoryDTO result = bookCategoryService.save(bookCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-categories : get all the bookCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookCategories in body
     */
    @GetMapping("/book-categories")
    @Timed
    public ResponseEntity<List<BookCategoryDTO>> getAllBookCategories(Pageable pageable) {
        log.debug("REST request to get a page of BookCategories");
        Page<BookCategoryDTO> page = bookCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-categories/:id : get the "id" bookCategory.
     *
     * @param id the id of the bookCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-categories/{id}")
    @Timed
    public ResponseEntity<BookCategoryDTO> getBookCategory(@PathVariable Long id) {
        log.debug("REST request to get BookCategory : {}", id);
        BookCategoryDTO bookCategoryDTO = bookCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookCategoryDTO));
    }

    /**
     * DELETE  /book-categories/:id : delete the "id" bookCategory.
     *
     * @param id the id of the bookCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookCategory(@PathVariable Long id) {
        log.debug("REST request to delete BookCategory : {}", id);
        bookCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
