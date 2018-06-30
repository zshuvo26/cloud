package cloud.service;

import cloud.service.dto.DigitalContentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DigitalContent.
 */
public interface DigitalContentService {

    /**
     * Save a digitalContent.
     *
     * @param digitalContentDTO the entity to save
     * @return the persisted entity
     */
    DigitalContentDTO save(DigitalContentDTO digitalContentDTO);

    /**
     * Get all the digitalContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DigitalContentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" digitalContent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DigitalContentDTO findOne(Long id);

    /**
     * Delete the "id" digitalContent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
