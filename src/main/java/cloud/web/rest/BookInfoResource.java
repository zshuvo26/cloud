package cloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.service.BookInfoService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.BookInfoDTO;
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
 * REST controller for managing BookInfo.
 */
@RestController
@RequestMapping("/api")
public class BookInfoResource {

    private final Logger log = LoggerFactory.getLogger(BookInfoResource.class);

    private static final String ENTITY_NAME = "bookInfo";

    private final BookInfoService bookInfoService;

    public BookInfoResource(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    /**
     * POST  /book-infos : Create a new bookInfo.
     *
     * @param bookInfoDTO the bookInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookInfoDTO, or with status 400 (Bad Request) if the bookInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-infos")
    @Timed
    public ResponseEntity<BookInfoDTO> createBookInfo(@Valid @RequestBody BookInfoDTO bookInfoDTO) throws URISyntaxException {
        log.debug("REST request to save BookInfo : {}", bookInfoDTO);
        if (bookInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookInfoDTO result = bookInfoService.save(bookInfoDTO);
        return ResponseEntity.created(new URI("/api/book-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-infos : Updates an existing bookInfo.
     *
     * @param bookInfoDTO the bookInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookInfoDTO,
     * or with status 400 (Bad Request) if the bookInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-infos")
    @Timed
    public ResponseEntity<BookInfoDTO> updateBookInfo(@Valid @RequestBody BookInfoDTO bookInfoDTO) throws URISyntaxException {
        log.debug("REST request to update BookInfo : {}", bookInfoDTO);
        if (bookInfoDTO.getId() == null) {
            return createBookInfo(bookInfoDTO);
        }
        BookInfoDTO result = bookInfoService.save(bookInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-infos : get all the bookInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookInfos in body
     */
    @GetMapping("/book-infos")
    @Timed
    public ResponseEntity<List<BookInfoDTO>> getAllBookInfos(Pageable pageable) {
        log.debug("REST request to get a page of BookInfos");
        Page<BookInfoDTO> page = bookInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /book-infos/:id : get the "id" bookInfo.
     *
     * @param id the id of the bookInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-infos/{id}")
    @Timed
    public ResponseEntity<BookInfoDTO> getBookInfo(@PathVariable Long id) {
        log.debug("REST request to get BookInfo : {}", id);
        BookInfoDTO bookInfoDTO = bookInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookInfoDTO));
    }

    /**
     * DELETE  /book-infos/:id : delete the "id" bookInfo.
     *
     * @param id the id of the bookInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookInfo(@PathVariable Long id) {
        log.debug("REST request to delete BookInfo : {}", id);
        bookInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
