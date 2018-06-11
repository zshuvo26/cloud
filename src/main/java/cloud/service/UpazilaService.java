package cloud.service;

import cloud.service.dto.UpazilaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Upazila.
 */
public interface UpazilaService {

    /**
     * Save a upazila.
     *
     * @param upazilaDTO the entity to save
     * @return the persisted entity
     */
    UpazilaDTO save(UpazilaDTO upazilaDTO);

    /**
     * Get all the upazilas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UpazilaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" upazila.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UpazilaDTO findOne(Long id);

    /**
     * Delete the "id" upazila.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
