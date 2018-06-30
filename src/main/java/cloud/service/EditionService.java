package cloud.service;

import cloud.service.dto.EditionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Edition.
 */
public interface EditionService {

    /**
     * Save a edition.
     *
     * @param editionDTO the entity to save
     * @return the persisted entity
     */
    EditionDTO save(EditionDTO editionDTO);

    /**
     * Get all the editions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EditionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" edition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EditionDTO findOne(Long id);

    /**
     * Delete the "id" edition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
