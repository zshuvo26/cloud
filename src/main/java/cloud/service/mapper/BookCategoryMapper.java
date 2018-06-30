package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookCategory and its DTO BookCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {BookTypeMapper.class})
public interface BookCategoryMapper extends EntityMapper<BookCategoryDTO, BookCategory> {

    @Mapping(source = "bookType.id", target = "bookTypeId")
    BookCategoryDTO toDto(BookCategory bookCategory);

    @Mapping(target = "bookSubCategories", ignore = true)
    @Mapping(source = "bookTypeId", target = "bookType")
    BookCategory toEntity(BookCategoryDTO bookCategoryDTO);

    default BookCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(id);
        return bookCategory;
    }
}
