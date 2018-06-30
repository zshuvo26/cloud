package cloud.service.impl;

import cloud.service.BookTypeService;
import cloud.domain.BookType;
import cloud.repository.BookTypeRepository;
import cloud.service.dto.BookTypeDTO;
import cloud.service.mapper.BookTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookType.
 */
@Service
@Transactional
public class BookTypeServiceImpl implements BookTypeService {

    private final Logger log = LoggerFactory.getLogger(BookTypeServiceImpl.class);

    private final BookTypeRepository bookTypeRepository;

    private final BookTypeMapper bookTypeMapper;

    public BookTypeServiceImpl(BookTypeRepository bookTypeRepository, BookTypeMapper bookTypeMapper) {
        this.bookTypeRepository = bookTypeRepository;
        this.bookTypeMapper = bookTypeMapper;
    }

    /**
     * Save a bookType.
     *
     * @param bookTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookTypeDTO save(BookTypeDTO bookTypeDTO) {
        log.debug("Request to save BookType : {}", bookTypeDTO);
        BookType bookType = bookTypeMapper.toEntity(bookTypeDTO);
        bookType = bookTypeRepository.save(bookType);
        return bookTypeMapper.toDto(bookType);
    }

    /**
     * Get all the bookTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookTypes");
        return bookTypeRepository.findAll(pageable)
            .map(bookTypeMapper::toDto);
    }

    /**
     * Get one bookType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookTypeDTO findOne(Long id) {
        log.debug("Request to get BookType : {}", id);
        BookType bookType = bookTypeRepository.findOne(id);
        return bookTypeMapper.toDto(bookType);
    }

    /**
     * Delete the bookType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookType : {}", id);
        bookTypeRepository.delete(id);
    }
}
