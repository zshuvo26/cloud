package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookRequisitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookRequisition and its DTO BookRequisitionDTO.
 */
@Mapper(componentModel = "spring", uses = {BookSubCategoryMapper.class})
public interface BookRequisitionMapper extends EntityMapper<BookRequisitionDTO, BookRequisition> {

    @Mapping(source = "bookSubCategory.id", target = "bookSubCategoryId")
    BookRequisitionDTO toDto(BookRequisition bookRequisition);

    @Mapping(source = "bookSubCategoryId", target = "bookSubCategory")
    BookRequisition toEntity(BookRequisitionDTO bookRequisitionDTO);

    default BookRequisition fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookRequisition bookRequisition = new BookRequisition();
        bookRequisition.setId(id);
        return bookRequisition;
    }
}
