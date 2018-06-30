package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookFineSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookFineSetting and its DTO BookFineSettingDTO.
 */
@Mapper(componentModel = "spring", uses = {BookTypeMapper.class})
public interface BookFineSettingMapper extends EntityMapper<BookFineSettingDTO, BookFineSetting> {

    @Mapping(source = "bookType.id", target = "bookTypeId")
    BookFineSettingDTO toDto(BookFineSetting bookFineSetting);

    @Mapping(target = "bookReturns", ignore = true)
    @Mapping(source = "bookTypeId", target = "bookType")
    BookFineSetting toEntity(BookFineSettingDTO bookFineSettingDTO);

    default BookFineSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookFineSetting bookFineSetting = new BookFineSetting();
        bookFineSetting.setId(id);
        return bookFineSetting;
    }
}
