package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.BookIssueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookIssue and its DTO BookIssueDTO.
 */
@Mapper(componentModel = "spring", uses = {BookInfoMapper.class})
public interface BookIssueMapper extends EntityMapper<BookIssueDTO, BookIssue> {

    @Mapping(source = "bookInfo.id", target = "bookInfoId")
    BookIssueDTO toDto(BookIssue bookIssue);

    @Mapping(source = "bookInfoId", target = "bookInfo")
    @Mapping(target = "bookReturns", ignore = true)
    BookIssue toEntity(BookIssueDTO bookIssueDTO);

    default BookIssue fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookIssue bookIssue = new BookIssue();
        bookIssue.setId(id);
        return bookIssue;
    }
}
