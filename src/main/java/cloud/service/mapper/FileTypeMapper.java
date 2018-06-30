package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.FileTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileType and its DTO FileTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileTypeMapper extends EntityMapper<FileTypeDTO, FileType> {


    @Mapping(target = "digitalContents", ignore = true)
    FileType toEntity(FileTypeDTO fileTypeDTO);

    default FileType fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileType fileType = new FileType();
        fileType.setId(id);
        return fileType;
    }
}
