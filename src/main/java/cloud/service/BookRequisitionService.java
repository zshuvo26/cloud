package cloud.service;

import cloud.service.dto.BookRequisitionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookRequisition.
 */
public interface BookRequisitionService {

    /**
     * Save a bookRequisition.
     *
     * @param bookRequisitionDTO the entity to save
     * @return the persisted entity
     */
    BookRequisitionDTO save(BookRequisitionDTO bookRequisitionDTO);

    /**
     * Get all the bookRequisitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookRequisitionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookRequisition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookRequisitionDTO findOne(Long id);

    /**
     * Delete the "id" bookRequisition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
