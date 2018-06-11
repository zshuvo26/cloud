package cloud.service.impl;

import cloud.service.InstituteService;
import cloud.domain.Institute;
import cloud.repository.InstituteRepository;
import cloud.service.dto.InstituteDTO;
import cloud.service.mapper.InstituteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Institute.
 */
@Service
@Transactional
public class InstituteServiceImpl implements InstituteService {

    private final Logger log = LoggerFactory.getLogger(InstituteServiceImpl.class);

    private final InstituteRepository instituteRepository;

    private final InstituteMapper instituteMapper;

    public InstituteServiceImpl(InstituteRepository instituteRepository, InstituteMapper instituteMapper) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
    }

    /**
     * Save a institute.
     *
     * @param instituteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InstituteDTO save(InstituteDTO instituteDTO) {
        log.debug("Request to save Institute : {}", instituteDTO);
        Institute institute = instituteMapper.toEntity(instituteDTO);
        institute = instituteRepository.save(institute);
        return instituteMapper.toDto(institute);
    }

    /**
     * Get all the institutes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InstituteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Institutes");
        return instituteRepository.findAll(pageable)
            .map(instituteMapper::toDto);
    }

    /**
     * Get one institute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDTO findOne(Long id) {
        log.debug("Request to get Institute : {}", id);
        Institute institute = instituteRepository.findOne(id);
        return instituteMapper.toDto(institute);
    }

    /**
     * Delete the institute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institute : {}", id);
        instituteRepository.delete(id);
    }
}
