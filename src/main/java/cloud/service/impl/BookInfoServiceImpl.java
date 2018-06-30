package cloud.service.impl;

import cloud.service.BookInfoService;
import cloud.domain.BookInfo;
import cloud.repository.BookInfoRepository;
import cloud.service.dto.BookInfoDTO;
import cloud.service.mapper.BookInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BookInfo.
 */
@Service
@Transactional
public class BookInfoServiceImpl implements BookInfoService {

    private final Logger log = LoggerFactory.getLogger(BookInfoServiceImpl.class);

    private final BookInfoRepository bookInfoRepository;

    private final BookInfoMapper bookInfoMapper;

    public BookInfoServiceImpl(BookInfoRepository bookInfoRepository, BookInfoMapper bookInfoMapper) {
        this.bookInfoRepository = bookInfoRepository;
        this.bookInfoMapper = bookInfoMapper;
    }

    /**
     * Save a bookInfo.
     *
     * @param bookInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookInfoDTO save(BookInfoDTO bookInfoDTO) {
        log.debug("Request to save BookInfo : {}", bookInfoDTO);
        BookInfo bookInfo = bookInfoMapper.toEntity(bookInfoDTO);
        bookInfo = bookInfoRepository.save(bookInfo);
        return bookInfoMapper.toDto(bookInfo);
    }

    /**
     * Get all the bookInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookInfos");
        return bookInfoRepository.findAll(pageable)
            .map(bookInfoMapper::toDto);
    }

    /**
     * Get one bookInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookInfoDTO findOne(Long id) {
        log.debug("Request to get BookInfo : {}", id);
        BookInfo bookInfo = bookInfoRepository.findOne(id);
        return bookInfoMapper.toDto(bookInfo);
    }

    /**
     * Delete the bookInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookInfo : {}", id);
        bookInfoRepository.delete(id);
    }
}
