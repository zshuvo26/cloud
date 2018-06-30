package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookIssue entity.
 */
public class BookIssueDTO implements Serializable {

    private Long id;

    @NotNull
    private String noOfCopies;

    private LocalDate returnDate;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private Integer updateBy;

    private Long bookInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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

    public Long getBookInfoId() {
        return bookInfoId;
    }

    public void setBookInfoId(Long bookInfoId) {
        this.bookInfoId = bookInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookIssueDTO bookIssueDTO = (BookIssueDTO) o;
        if(bookIssueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookIssueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookIssueDTO{" +
            "id=" + getId() +
            ", noOfCopies='" + getNoOfCopies() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
