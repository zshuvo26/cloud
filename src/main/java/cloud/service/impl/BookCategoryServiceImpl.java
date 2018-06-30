package cloud.service.impl;

import cloud.service.BookCategoryService;
import cloud.domain.BookCategory;
import cloud.repository.BookCategoryRepository;
import cloud.service.dto.BookCategoryDTO;
import cloud.service.mapper.BookCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookCategory.
 */
@Service
@Transactional
public class BookCategoryServiceImpl implements BookCategoryService {

    private final Logger log = LoggerFactory.getLogger(BookCategoryServiceImpl.class);

    private final BookCategoryRepository bookCategoryRepository;

    private final BookCategoryMapper bookCategoryMapper;

    public BookCategoryServiceImpl(BookCategoryRepository bookCategoryRepository, BookCategoryMapper bookCategoryMapper) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookCategoryMapper = bookCategoryMapper;
    }

    /**
     * Save a bookCategory.
     *
     * @param bookCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookCategoryDTO save(BookCategoryDTO bookCategoryDTO) {
        log.debug("Request to save BookCategory : {}", bookCategoryDTO);
        BookCategory bookCategory = bookCategoryMapper.toEntity(bookCategoryDTO);
        bookCategory = bookCategoryRepository.save(bookCategory);
        return bookCategoryMapper.toDto(bookCategory);
    }

    /**
     * Get all the bookCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookCategories");
        return bookCategoryRepository.findAll(pageable)
            .map(bookCategoryMapper::toDto);
    }

    /**
     * Get one bookCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookCategoryDTO findOne(Long id) {
        log.debug("Request to get BookCategory : {}", id);
        BookCategory bookCategory = bookCategoryRepository.findOne(id);
        return bookCategoryMapper.toDto(bookCategory);
    }

    /**
     * Delete the bookCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookCategory : {}", id);
        bookCategoryRepository.delete(id);
    }
}
