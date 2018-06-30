package cloud.service;

import cloud.service.dto.BookCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookCategory.
 */
public interface BookCategoryService {

    /**
     * Save a bookCategory.
     *
     * @param bookCategoryDTO the entity to save
     * @return the persisted entity
     */
    BookCategoryDTO save(BookCategoryDTO bookCategoryDTO);

    /**
     * Get all the bookCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookCategoryDTO findOne(Long id);

    /**
     * Delete the "id" bookCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
