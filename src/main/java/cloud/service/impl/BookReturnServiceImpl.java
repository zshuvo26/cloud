package cloud.service.impl;

import cloud.service.BookReturnService;
import cloud.domain.BookReturn;
import cloud.repository.BookReturnRepository;
import cloud.service.dto.BookReturnDTO;
import cloud.service.mapper.BookReturnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookReturn.
 */
@Service
@Transactional
public class BookReturnServiceImpl implements BookReturnService {

    private final Logger log = LoggerFactory.getLogger(BookReturnServiceImpl.class);

    private final BookReturnRepository bookReturnRepository;

    private final BookReturnMapper bookReturnMapper;

    public BookReturnServiceImpl(BookReturnRepository bookReturnRepository, BookReturnMapper bookReturnMapper) {
        this.bookReturnRepository = bookReturnRepository;
        this.bookReturnMapper = bookReturnMapper;
    }

    /**
     * Save a bookReturn.
     *
     * @param bookReturnDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookReturnDTO save(BookReturnDTO bookReturnDTO) {
        log.debug("Request to save BookReturn : {}", bookReturnDTO);
        BookReturn bookReturn = bookReturnMapper.toEntity(bookReturnDTO);
        bookReturn = bookReturnRepository.save(bookReturn);
        return bookReturnMapper.toDto(bookReturn);
    }

    /**
     * Get all the bookReturns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookReturnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookReturns");
        return bookReturnRepository.findAll(pageable)
            .map(bookReturnMapper::toDto);
    }

    /**
     * Get one bookReturn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookReturnDTO findOne(Long id) {
        log.debug("Request to get BookReturn : {}", id);
        BookReturn bookReturn = bookReturnRepository.findOne(id);
        return bookReturnMapper.toDto(bookReturn);
    }

    /**
     * Delete the bookReturn by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookReturn : {}", id);
        bookReturnRepository.delete(id);
    }
}
