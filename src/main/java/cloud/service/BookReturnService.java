package cloud.service;

import cloud.service.dto.BookReturnDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookReturn.
 */
public interface BookReturnService {

    /**
     * Save a bookReturn.
     *
     * @param bookReturnDTO the entity to save
     * @return the persisted entity
     */
    BookReturnDTO save(BookReturnDTO bookReturnDTO);

    /**
     * Get all the bookReturns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookReturnDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookReturn.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookReturnDTO findOne(Long id);

    /**
     * Delete the "id" bookReturn.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
