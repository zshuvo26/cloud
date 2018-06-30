package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookSubCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookSubCategory and its DTO BookSubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {BookCategoryMapper.class})
public interface BookSubCategoryMapper extends EntityMapper<BookSubCategoryDTO, BookSubCategory> {

    @Mapping(source = "bookCategory.id", target = "bookCategoryId")
    BookSubCategoryDTO toDto(BookSubCategory bookSubCategory);

    @Mapping(source = "bookCategoryId", target = "bookCategory")
    @Mapping(target = "bookInfos", ignore = true)
    @Mapping(target = "bookRequisitions", ignore = true)
    @Mapping(target = "digitalContents", ignore = true)
    BookSubCategory toEntity(BookSubCategoryDTO bookSubCategoryDTO);

    default BookSubCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookSubCategory bookSubCategory = new BookSubCategory();
        bookSubCategory.setId(id);
        return bookSubCategory;
    }
}
