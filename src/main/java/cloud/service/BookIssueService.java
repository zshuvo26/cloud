package cloud.service;

import cloud.service.dto.BookIssueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookIssue.
 */
public interface BookIssueService {

    /**
     * Save a bookIssue.
     *
     * @param bookIssueDTO the entity to save
     * @return the persisted entity
     */
    BookIssueDTO save(BookIssueDTO bookIssueDTO);

    /**
     * Get all the bookIssues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookIssueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookIssue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookIssueDTO findOne(Long id);

    /**
     * Delete the "id" bookIssue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
