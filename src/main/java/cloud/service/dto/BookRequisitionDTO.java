package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookRequisition entity.
 */
public class BookRequisitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String edition;

    private String authorName;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private Integer updateBy;

    private Long bookSubCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Long getBookSubCategoryId() {
        return bookSubCategoryId;
    }

    public void setBookSubCategoryId(Long bookSubCategoryId) {
        this.bookSubCategoryId = bookSubCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookRequisitionDTO bookRequisitionDTO = (BookRequisitionDTO) o;
        if(bookRequisitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookRequisitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookRequisitionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", edition='" + getEdition() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
