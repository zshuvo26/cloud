package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookReturnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookReturn and its DTO BookReturnDTO.
 */
@Mapper(componentModel = "spring", uses = {BookIssueMapper.class, BookFineSettingMapper.class})
public interface BookReturnMapper extends EntityMapper<BookReturnDTO, BookReturn> {

    @Mapping(source = "bookIssue.id", target = "bookIssueId")
    @Mapping(source = "bookFineSetting.id", target = "bookFineSettingId")
    BookReturnDTO toDto(BookReturn bookReturn);

    @Mapping(source = "bookIssueId", target = "bookIssue")
    @Mapping(source = "bookFineSettingId", target = "bookFineSetting")
    BookReturn toEntity(BookReturnDTO bookReturnDTO);

    default BookReturn fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookReturn bookReturn = new BookReturn();
        bookReturn.setId(id);
        return bookReturn;
    }
}
