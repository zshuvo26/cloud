package cloud.service;

import cloud.service.dto.FileTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FileType.
 */
public interface FileTypeService {

    /**
     * Save a fileType.
     *
     * @param fileTypeDTO the entity to save
     * @return the persisted entity
     */
    FileTypeDTO save(FileTypeDTO fileTypeDTO);

    /**
     * Get all the fileTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FileTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FileTypeDTO findOne(Long id);

    /**
     * Delete the "id" fileType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
