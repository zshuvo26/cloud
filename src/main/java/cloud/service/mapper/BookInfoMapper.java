package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookInfo and its DTO BookInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {InstituteMapper.class, PublisherMapper.class, BookSubCategoryMapper.class})
public interface BookInfoMapper extends EntityMapper<BookInfoDTO, BookInfo> {

    @Mapping(source = "institute.id", target = "instituteId")
    @Mapping(source = "publisher.id", target = "publisherId")
    @Mapping(source = "bookSubCategory.id", target = "bookSubCategoryId")
    BookInfoDTO toDto(BookInfo bookInfo);

    @Mapping(source = "instituteId", target = "institute")
    @Mapping(source = "publisherId", target = "publisher")
    @Mapping(target = "bookIssues", ignore = true)
    @Mapping(target = "editions", ignore = true)
    @Mapping(source = "bookSubCategoryId", target = "bookSubCategory")
    BookInfo toEntity(BookInfoDTO bookInfoDTO);

    default BookInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(id);
        return bookInfo;
    }
}
