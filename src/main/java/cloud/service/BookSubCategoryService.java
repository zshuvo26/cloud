package cloud.service;

import cloud.service.dto.BookSubCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookSubCategory.
 */
public interface BookSubCategoryService {

    /**
     * Save a bookSubCategory.
     *
     * @param bookSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    BookSubCategoryDTO save(BookSubCategoryDTO bookSubCategoryDTO);

    /**
     * Get all the bookSubCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookSubCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookSubCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookSubCategoryDTO findOne(Long id);

    /**
     * Delete the "id" bookSubCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
