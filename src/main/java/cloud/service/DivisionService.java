package cloud.service;

import cloud.service.dto.DivisionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Division.
 */
public interface DivisionService {

    /**
     * Save a division.
     *
     * @param divisionDTO the entity to save
     * @return the persisted entity
     */
    DivisionDTO save(DivisionDTO divisionDTO);

    /**
     * Get all the divisions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DivisionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" division.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DivisionDTO findOne(Long id);

    /**
     * Delete the "id" division.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
