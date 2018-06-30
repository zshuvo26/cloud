package cloud.service.impl;

import cloud.service.FileTypeService;
import cloud.domain.FileType;
import cloud.repository.FileTypeRepository;
import cloud.service.dto.FileTypeDTO;
import cloud.service.mapper.FileTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FileType.
 */
@Service
@Transactional
public class FileTypeServiceImpl implements FileTypeService {

    private final Logger log = LoggerFactory.getLogger(FileTypeServiceImpl.class);

    private final FileTypeRepository fileTypeRepository;

    private final FileTypeMapper fileTypeMapper;

    public FileTypeServiceImpl(FileTypeRepository fileTypeRepository, FileTypeMapper fileTypeMapper) {
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeMapper = fileTypeMapper;
    }

    /**
     * Save a fileType.
     *
     * @param fileTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileTypeDTO save(FileTypeDTO fileTypeDTO) {
        log.debug("Request to save FileType : {}", fileTypeDTO);
        FileType fileType = fileTypeMapper.toEntity(fileTypeDTO);
        fileType = fileTypeRepository.save(fileType);
        return fileTypeMapper.toDto(fileType);
    }

    /**
     * Get all the fileTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileTypes");
        return fileTypeRepository.findAll(pageable)
            .map(fileTypeMapper::toDto);
    }

    /**
     * Get one fileType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FileTypeDTO findOne(Long id) {
        log.debug("Request to get FileType : {}", id);
        FileType fileType = fileTypeRepository.findOne(id);
        return fileTypeMapper.toDto(fileType);
    }

    /**
     * Delete the fileType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileType : {}", id);
        fileTypeRepository.delete(id);
    }
}
