package cloud.service;

import cloud.service.dto.BookFineSettingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookFineSetting.
 */
public interface BookFineSettingService {

    /**
     * Save a bookFineSetting.
     *
     * @param bookFineSettingDTO the entity to save
     * @return the persisted entity
     */
    BookFineSettingDTO save(BookFineSettingDTO bookFineSettingDTO);

    /**
     * Get all the bookFineSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookFineSettingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookFineSetting.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookFineSettingDTO findOne(Long id);

    /**
     * Delete the "id" bookFineSetting.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
