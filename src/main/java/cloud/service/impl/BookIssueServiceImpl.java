package cloud.service.impl;

import cloud.service.BookIssueService;
import cloud.domain.BookIssue;
import cloud.repository.BookIssueRepository;
import cloud.service.dto.BookIssueDTO;
import cloud.service.mapper.BookIssueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookIssue.
 */
@Service
@Transactional
public class BookIssueServiceImpl implements BookIssueService {

    private final Logger log = LoggerFactory.getLogger(BookIssueServiceImpl.class);

    private final BookIssueRepository bookIssueRepository;

    private final BookIssueMapper bookIssueMapper;

    public BookIssueServiceImpl(BookIssueRepository bookIssueRepository, BookIssueMapper bookIssueMapper) {
        this.bookIssueRepository = bookIssueRepository;
        this.bookIssueMapper = bookIssueMapper;
    }

    /**
     * Save a bookIssue.
     *
     * @param bookIssueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookIssueDTO save(BookIssueDTO bookIssueDTO) {
        log.debug("Request to save BookIssue : {}", bookIssueDTO);
        BookIssue bookIssue = bookIssueMapper.toEntity(bookIssueDTO);
        bookIssue = bookIssueRepository.save(bookIssue);
        return bookIssueMapper.toDto(bookIssue);
    }

    /**
     * Get all the bookIssues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookIssueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookIssues");
        return bookIssueRepository.findAll(pageable)
            .map(bookIssueMapper::toDto);
    }

    /**
     * Get one bookIssue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookIssueDTO findOne(Long id) {
        log.debug("Request to get BookIssue : {}", id);
        BookIssue bookIssue = bookIssueRepository.findOne(id);
        return bookIssueMapper.toDto(bookIssue);
    }

    /**
     * Delete the bookIssue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookIssue : {}", id);
        bookIssueRepository.delete(id);
    }
}
