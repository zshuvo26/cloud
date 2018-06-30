package cloud.service.impl;

import cloud.service.BookFineSettingService;
import cloud.domain.BookFineSetting;
import cloud.repository.BookFineSettingRepository;
import cloud.service.dto.BookFineSettingDTO;
import cloud.service.mapper.BookFineSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookFineSetting.
 */
@Service
@Transactional
public class BookFineSettingServiceImpl implements BookFineSettingService {

    private final Logger log = LoggerFactory.getLogger(BookFineSettingServiceImpl.class);

    private final BookFineSettingRepository bookFineSettingRepository;

    private final BookFineSettingMapper bookFineSettingMapper;

    public BookFineSettingServiceImpl(BookFineSettingRepository bookFineSettingRepository, BookFineSettingMapper bookFineSettingMapper) {
        this.bookFineSettingRepository = bookFineSettingRepository;
        this.bookFineSettingMapper = bookFineSettingMapper;
    }

    /**
     * Save a bookFineSetting.
     *
     * @param bookFineSettingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookFineSettingDTO save(BookFineSettingDTO bookFineSettingDTO) {
        log.debug("Request to save BookFineSetting : {}", bookFineSettingDTO);
        BookFineSetting bookFineSetting = bookFineSettingMapper.toEntity(bookFineSettingDTO);
        bookFineSetting = bookFineSettingRepository.save(bookFineSetting);
        return bookFineSettingMapper.toDto(bookFineSetting);
    }

    /**
     * Get all the bookFineSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookFineSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookFineSettings");
        return bookFineSettingRepository.findAll(pageable)
            .map(bookFineSettingMapper::toDto);
    }

    /**
     * Get one bookFineSetting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookFineSettingDTO findOne(Long id) {
        log.debug("Request to get BookFineSetting : {}", id);
        BookFineSetting bookFineSetting = bookFineSettingRepository.findOne(id);
        return bookFineSettingMapper.toDto(bookFineSetting);
    }

    /**
     * Delete the bookFineSetting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookFineSetting : {}", id);
        bookFineSettingRepository.delete(id);
    }
}
