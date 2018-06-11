package cloud.service.impl;

import cloud.service.UpazilaService;
import cloud.domain.Upazila;
import cloud.repository.UpazilaRepository;
import cloud.service.dto.UpazilaDTO;
import cloud.service.mapper.UpazilaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Upazila.
 */
@Service
@Transactional
public class UpazilaServiceImpl implements UpazilaService {

    private final Logger log = LoggerFactory.getLogger(UpazilaServiceImpl.class);

    private final UpazilaRepository upazilaRepository;

    private final UpazilaMapper upazilaMapper;

    public UpazilaServiceImpl(UpazilaRepository upazilaRepository, UpazilaMapper upazilaMapper) {
        this.upazilaRepository = upazilaRepository;
        this.upazilaMapper = upazilaMapper;
    }

    /**
     * Save a upazila.
     *
     * @param upazilaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UpazilaDTO save(UpazilaDTO upazilaDTO) {
        log.debug("Request to save Upazila : {}", upazilaDTO);
        Upazila upazila = upazilaMapper.toEntity(upazilaDTO);
        upazila = upazilaRepository.save(upazila);
        return upazilaMapper.toDto(upazila);
    }

    /**
     * Get all the upazilas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UpazilaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Upazilas");
        return upazilaRepository.findAll(pageable)
            .map(upazilaMapper::toDto);
    }

    /**
     * Get one upazila by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UpazilaDTO findOne(Long id) {
        log.debug("Request to get Upazila : {}", id);
        Upazila upazila = upazilaRepository.findOne(id);
        return upazilaMapper.toDto(upazila);
    }

    /**
     * Delete the upazila by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Upazila : {}", id);
        upazilaRepository.delete(id);
    }
}
