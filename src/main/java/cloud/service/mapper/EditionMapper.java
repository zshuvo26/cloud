package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.EditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Edition and its DTO EditionDTO.
 */
@Mapper(componentModel = "spring", uses = {BookInfoMapper.class})
public interface EditionMapper extends EntityMapper<EditionDTO, Edition> {

    @Mapping(source = "bookInfo.id", target = "bookInfoId")
    EditionDTO toDto(Edition edition);

    @Mapping(source = "bookInfoId", target = "bookInfo")
    Edition toEntity(EditionDTO editionDTO);

    default Edition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Edition edition = new Edition();
        edition.setId(id);
        return edition;
    }
}
