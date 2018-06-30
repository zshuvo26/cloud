package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DigitalContent entity.
 */
public class DigitalContentDTO implements Serializable {

    private Long id;

    @NotNull
    private String accessionNo;

    @NotNull
    private String title;

    private String isbnNo;

    private String authorName;

    @Lob
    private byte[] coverPhoto;
    private String coverPhotoContentType;

    private String coverPhotoType;

    private String coverPhotoName;

    @Lob
    private byte[] content;
    private String contentContentType;

    private String contentType;

    private String contentName;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private Integer updateBy;

    private Long bookSubCategoryId;

    private Long fileTypeId;

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
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

    public Long getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DigitalContentDTO digitalContentDTO = (DigitalContentDTO) o;
        if(digitalContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), digitalContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DigitalContentDTO{" +
            "id=" + getId() +
            ", accessionNo='" + getAccessionNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", isbnNo='" + getIsbnNo() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", coverPhoto='" + getCoverPhoto() + "'" +
            ", coverPhotoType='" + getCoverPhotoType() + "'" +
            ", coverPhotoName='" + getCoverPhotoName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", contentName='" + getContentName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
