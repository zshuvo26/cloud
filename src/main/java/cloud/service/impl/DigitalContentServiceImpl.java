package cloud.service.impl;

import cloud.service.DigitalContentService;
import cloud.domain.DigitalContent;
import cloud.repository.DigitalContentRepository;
import cloud.service.dto.DigitalContentDTO;
import cloud.service.mapper.DigitalContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DigitalContent.
 */
@Service
@Transactional
public class DigitalContentServiceImpl implements DigitalContentService {

    private final Logger log = LoggerFactory.getLogger(DigitalContentServiceImpl.class);

    private final DigitalContentRepository digitalContentRepository;

    private final DigitalContentMapper digitalContentMapper;

    public DigitalContentServiceImpl(DigitalContentRepository digitalContentRepository, DigitalContentMapper digitalContentMapper) {
        this.digitalContentRepository = digitalContentRepository;
        this.digitalContentMapper = digitalContentMapper;
    }

    /**
     * Save a digitalContent.
     *
     * @param digitalContentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DigitalContentDTO save(DigitalContentDTO digitalContentDTO) {
        log.debug("Request to save DigitalContent : {}", digitalContentDTO);
        DigitalContent digitalContent = digitalContentMapper.toEntity(digitalContentDTO);
        digitalContent = digitalContentRepository.save(digitalContent);
        return digitalContentMapper.toDto(digitalContent);
    }

    /**
     * Get all the digitalContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DigitalContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DigitalContents");
        return digitalContentRepository.findAll(pageable)
            .map(digitalContentMapper::toDto);
    }

    /**
     * Get one digitalContent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DigitalContentDTO findOne(Long id) {
        log.debug("Request to get DigitalContent : {}", id);
        DigitalContent digitalContent = digitalContentRepository.findOne(id);
        return digitalContentMapper.toDto(digitalContent);
    }

    /**
     * Delete the digitalContent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DigitalContent : {}", id);
        digitalContentRepository.delete(id);
    }
}
