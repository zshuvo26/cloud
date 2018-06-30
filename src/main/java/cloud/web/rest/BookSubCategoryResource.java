package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookSubCategoryService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookSubCategoryDTO;
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
 * REST controller for managing BookSubCategory.
 */
@RestController
@RequestMapping("/api")
public class BookSubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BookSubCategoryResource.class);

    private static final String ENTITY_NAME = "bookSubCategory";

    private final BookSubCategoryService bookSubCategoryService;

    public BookSubCategoryResource(BookSubCategoryService bookSubCategoryService) {
        this.bookSubCategoryService = bookSubCategoryService;
    }

    /**
     * POST  /book-sub-categories : Create a new bookSubCategory.
     *
     * @param bookSubCategoryDTO the bookSubCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookSubCategoryDTO, or with status 400 (Bad Request) if the bookSubCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-sub-categories")
    @Timed
    public ResponseEntity<BookSubCategoryDTO> createBookSubCategory(@RequestBody BookSubCategoryDTO bookSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save BookSubCategory : {}", bookSubCategoryDTO);
        if (bookSubCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookSubCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookSubCategoryDTO result = bookSubCategoryService.save(bookSubCategoryDTO);
        return ResponseEntity.created(new URI("/api/book-sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-sub-categories : Updates an existing bookSubCategory.
     *
     * @param bookSubCategoryDTO the bookSubCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookSubCategoryDTO,
     * or with status 400 (Bad Request) if the bookSubCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookSubCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-sub-categories")
    @Timed
    public ResponseEntity<BookSubCategoryDTO> updateBookSubCategory(@RequestBody BookSubCategoryDTO bookSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update BookSubCategory : {}", bookSubCategoryDTO);
        if (bookSubCategoryDTO.getId() == null) {
            return createBookSubCategory(bookSubCategoryDTO);
        }
        BookSubCategoryDTO result = bookSubCategoryService.save(bookSubCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookSubCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-sub-categories : get all the bookSubCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookSubCategories in body
     */
    @GetMapping("/book-sub-categories")
    @Timed
    public ResponseEntity<List<BookSubCategoryDTO>> getAllBookSubCategories(Pageable pageable) {
        log.debug("REST request to get a page of BookSubCategories");
        Page<BookSubCategoryDTO> page = bookSubCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-sub-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-sub-categories/:id : get the "id" bookSubCategory.
     *
     * @param id the id of the bookSubCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookSubCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-sub-categories/{id}")
    @Timed
    public ResponseEntity<BookSubCategoryDTO> getBookSubCategory(@PathVariable Long id) {
        log.debug("REST request to get BookSubCategory : {}", id);
        BookSubCategoryDTO bookSubCategoryDTO = bookSubCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookSubCategoryDTO));
    }

    /**
     * DELETE  /book-sub-categories/:id : delete the "id" bookSubCategory.
     *
     * @param id the id of the bookSubCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-sub-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete BookSubCategory : {}", id);
        bookSubCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
