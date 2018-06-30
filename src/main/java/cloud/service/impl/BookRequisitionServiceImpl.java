package cloud.service.impl;

import cloud.service.BookRequisitionService;
import cloud.domain.BookRequisition;
import cloud.repository.BookRequisitionRepository;
import cloud.service.dto.BookRequisitionDTO;
import cloud.service.mapper.BookRequisitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookRequisition.
 */
@Service
@Transactional
public class BookRequisitionServiceImpl implements BookRequisitionService {

    private final Logger log = LoggerFactory.getLogger(BookRequisitionServiceImpl.class);

    private final BookRequisitionRepository bookRequisitionRepository;

    private final BookRequisitionMapper bookRequisitionMapper;

    public BookRequisitionServiceImpl(BookRequisitionRepository bookRequisitionRepository, BookRequisitionMapper bookRequisitionMapper) {
        this.bookRequisitionRepository = bookRequisitionRepository;
        this.bookRequisitionMapper = bookRequisitionMapper;
    }

    /**
     * Save a bookRequisition.
     *
     * @param bookRequisitionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookRequisitionDTO save(BookRequisitionDTO bookRequisitionDTO) {
        log.debug("Request to save BookRequisition : {}", bookRequisitionDTO);
        BookRequisition bookRequisition = bookRequisitionMapper.toEntity(bookRequisitionDTO);
        bookRequisition = bookRequisitionRepository.save(bookRequisition);
        return bookRequisitionMapper.toDto(bookRequisition);
    }

    /**
     * Get all the bookRequisitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookRequisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookRequisitions");
        return bookRequisitionRepository.findAll(pageable)
            .map(bookRequisitionMapper::toDto);
    }

    /**
     * Get one bookRequisition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookRequisitionDTO findOne(Long id) {
        log.debug("Request to get BookRequisition : {}", id);
        BookRequisition bookRequisition = bookRequisitionRepository.findOne(id);
        return bookRequisitionMapper.toDto(bookRequisition);
    }

    /**
     * Delete the bookRequisition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookRequisition : {}", id);
        bookRequisitionRepository.delete(id);
    }
}
