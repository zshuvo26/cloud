package cloud.service;

import cloud.service.dto.BookInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BookInfo.
 */
public interface BookInfoService {

    /**
     * Save a bookInfo.
     *
     * @param bookInfoDTO the entity to save
     * @return the persisted entity
     */
    BookInfoDTO save(BookInfoDTO bookInfoDTO);

    /**
     * Get all the bookInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BookInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BookInfoDTO findOne(Long id);

    /**
     * Delete the "id" bookInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
