package cloud.service.impl;

import cloud.service.BookSubCategoryService;
import cloud.domain.BookSubCategory;
import cloud.repository.BookSubCategoryRepository;
import cloud.service.dto.BookSubCategoryDTO;
import cloud.service.mapper.BookSubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookSubCategory.
 */
@Service
@Transactional
public class BookSubCategoryServiceImpl implements BookSubCategoryService {

    private final Logger log = LoggerFactory.getLogger(BookSubCategoryServiceImpl.class);

    private final BookSubCategoryRepository bookSubCategoryRepository;

    private final BookSubCategoryMapper bookSubCategoryMapper;

    public BookSubCategoryServiceImpl(BookSubCategoryRepository bookSubCategoryRepository, BookSubCategoryMapper bookSubCategoryMapper) {
        this.bookSubCategoryRepository = bookSubCategoryRepository;
        this.bookSubCategoryMapper = bookSubCategoryMapper;
    }

    /**
     * Save a bookSubCategory.
     *
     * @param bookSubCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookSubCategoryDTO save(BookSubCategoryDTO bookSubCategoryDTO) {
        log.debug("Request to save BookSubCategory : {}", bookSubCategoryDTO);
        BookSubCategory bookSubCategory = bookSubCategoryMapper.toEntity(bookSubCategoryDTO);
        bookSubCategory = bookSubCategoryRepository.save(bookSubCategory);
        return bookSubCategoryMapper.toDto(bookSubCategory);
    }

    /**
     * Get all the bookSubCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookSubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookSubCategories");
        return bookSubCategoryRepository.findAll(pageable)
            .map(bookSubCategoryMapper::toDto);
    }

    /**
     * Get one bookSubCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookSubCategoryDTO findOne(Long id) {
        log.debug("Request to get BookSubCategory : {}", id);
        BookSubCategory bookSubCategory = bookSubCategoryRepository.findOne(id);
        return bookSubCategoryMapper.toDto(bookSubCategory);
    }

    /**
     * Delete the bookSubCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookSubCategory : {}", id);
        bookSubCategoryRepository.delete(id);
    }
}
