package cloud.service;

import cloud.service.dto.BookTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookType.
 */
public interface BookTypeService {

    /**
     * Save a bookType.
     *
     * @param bookTypeDTO the entity to save
     * @return the persisted entity
     */
    BookTypeDTO save(BookTypeDTO bookTypeDTO);

    /**
     * Get all the bookTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookTypeDTO findOne(Long id);

    /**
     * Delete the "id" bookType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
