package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookType and its DTO BookTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookTypeMapper extends EntityMapper<BookTypeDTO, BookType> {


    @Mapping(target = "bookCategories", ignore = true)
    @Mapping(target = "bookFineSettings", ignore = true)
    BookType toEntity(BookTypeDTO bookTypeDTO);

    default BookType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookType bookType = new BookType();
        bookType.setId(id);
        return bookType;
    }
}
