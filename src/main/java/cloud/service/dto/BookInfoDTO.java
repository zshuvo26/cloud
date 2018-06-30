package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import cloud.domain.enumeration.BookCondition;
import cloud.domain.enumeration.ContBookLang;

/**
 * A DTO for the BookInfo entity.
 */
public class BookInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String accessionNo;

    @NotNull
    private String title;

    private String isbnNo;

    private String authorName;

    private String billNo;

    private LocalDate billDate;

    @Lob
    private byte[] coverPhoto;
    private String coverPhotoContentType;

    private String coverPhotoType;

    private String coverPhotoName;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private BookCondition bookCondition;

    private ContBookLang contBookLang;

    private Integer updateBy;

    private Long instituteId;

    private Long publisherId;

    private Long bookSubCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessionNo() {
        return accessionNo;
    }

    public void setAccessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getCoverPhotoContentType() {
        return coverPhotoContentType;
    }

    public void setCoverPhotoContentType(String coverPhotoContentType) {
        this.coverPhotoContentType = coverPhotoContentType;
    }

    public String getCoverPhotoType() {
        return coverPhotoType;
    }

    public void setCoverPhotoType(String coverPhotoType) {
        this.coverPhotoType = coverPhotoType;
    }

    public String getCoverPhotoName() {
        return coverPhotoName;
    }

    public void setCoverPhotoName(String coverPhotoName) {
        this.coverPhotoName = coverPhotoName;
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

    public BookCondition getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }

    public ContBookLang getContBookLang() {
        return contBookLang;
    }

    public void setContBookLang(ContBookLang contBookLang) {
        this.contBookLang = contBookLang;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
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

        BookInfoDTO bookInfoDTO = (BookInfoDTO) o;
        if(bookInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookInfoDTO{" +
            "id=" + getId() +
            ", accessionNo='" + getAccessionNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", isbnNo='" + getIsbnNo() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", billNo='" + getBillNo() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", coverPhoto='" + getCoverPhoto() + "'" +
            ", coverPhotoType='" + getCoverPhotoType() + "'" +
            ", coverPhotoName='" + getCoverPhotoName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", bookCondition='" + getBookCondition() + "'" +
            ", contBookLang='" + getContBookLang() + "'" +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
