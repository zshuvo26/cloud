package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.DigitalContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DigitalContent and its DTO DigitalContentDTO.
 */
@Mapper(componentModel = "spring", uses = {BookSubCategoryMapper.class, FileTypeMapper.class})
public interface DigitalContentMapper extends EntityMapper<DigitalContentDTO, DigitalContent> {

    @Mapping(source = "bookSubCategory.id", target = "bookSubCategoryId")
    @Mapping(source = "fileType.id", target = "fileTypeId")
    DigitalContentDTO toDto(DigitalContent digitalContent);

    @Mapping(source = "bookSubCategoryId", target = "bookSubCategory")
    @Mapping(source = "fileTypeId", target = "fileType")
    DigitalContent toEntity(DigitalContentDTO digitalContentDTO);

    default DigitalContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        DigitalContent digitalContent = new DigitalContent();
        digitalContent.setId(id);
        return digitalContent;
    }
}
