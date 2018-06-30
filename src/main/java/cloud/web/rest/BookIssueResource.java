package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookIssueService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookIssueDTO;
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
 * REST controller for managing BookIssue.
 */
@RestController
@RequestMapping("/api")
public class BookIssueResource {

    private final Logger log = LoggerFactory.getLogger(BookIssueResource.class);

    private static final String ENTITY_NAME = "bookIssue";

    private final BookIssueService bookIssueService;

    public BookIssueResource(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    /**
     * POST  /book-issues : Create a new bookIssue.
     *
     * @param bookIssueDTO the bookIssueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookIssueDTO, or with status 400 (Bad Request) if the bookIssue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-issues")
    @Timed
    public ResponseEntity<BookIssueDTO> createBookIssue(@Valid @RequestBody BookIssueDTO bookIssueDTO) throws URISyntaxException {
        log.debug("REST request to save BookIssue : {}", bookIssueDTO);
        if (bookIssueDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookIssue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookIssueDTO result = bookIssueService.save(bookIssueDTO);
        return ResponseEntity.created(new URI("/api/book-issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-issues : Updates an existing bookIssue.
     *
     * @param bookIssueDTO the bookIssueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookIssueDTO,
     * or with status 400 (Bad Request) if the bookIssueDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookIssueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-issues")
    @Timed
    public ResponseEntity<BookIssueDTO> updateBookIssue(@Valid @RequestBody BookIssueDTO bookIssueDTO) throws URISyntaxException {
        log.debug("REST request to update BookIssue : {}", bookIssueDTO);
        if (bookIssueDTO.getId() == null) {
            return createBookIssue(bookIssueDTO);
        }
        BookIssueDTO result = bookIssueService.save(bookIssueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookIssueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-issues : get all the bookIssues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookIssues in body
     */
    @GetMapping("/book-issues")
    @Timed
    public ResponseEntity<List<BookIssueDTO>> getAllBookIssues(Pageable pageable) {
        log.debug("REST request to get a page of BookIssues");
        Page<BookIssueDTO> page = bookIssueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-issues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-issues/:id : get the "id" bookIssue.
     *
     * @param id the id of the bookIssueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookIssueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-issues/{id}")
    @Timed
    public ResponseEntity<BookIssueDTO> getBookIssue(@PathVariable Long id) {
        log.debug("REST request to get BookIssue : {}", id);
        BookIssueDTO bookIssueDTO = bookIssueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookIssueDTO));
    }

    /**
     * DELETE  /book-issues/:id : delete the "id" bookIssue.
     *
     * @param id the id of the bookIssueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-issues/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookIssue(@PathVariable Long id) {
        log.debug("REST request to delete BookIssue : {}", id);
        bookIssueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
