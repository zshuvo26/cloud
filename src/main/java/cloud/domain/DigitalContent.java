package cloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DigitalContent.
 */
@Entity
@Table(name = "digital_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DigitalContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "accession_no", nullable = false)
    private String accessionNo;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn_no")
    private String isbnNo;

    @Column(name = "author_name")
    private String authorName;

    @Lob
    @Column(name = "cover_photo")
    private byte[] coverPhoto;

    @Column(name = "cover_photo_content_type")
    private String coverPhotoContentType;

    @Column(name = "cover_photo_type")
    private String coverPhotoType;

    @Column(name = "cover_photo_name")
    private String coverPhotoName;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_name")
    private String contentName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private BookSubCategory bookSubCategory;

    @ManyToOne
    private FileType fileType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessionNo() {
        return accessionNo;
    }

    public DigitalContent accessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
        return this;
    }

    public void setAccessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
    }

    public String getTitle() {
        return title;
    }

    public DigitalContent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public DigitalContent isbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
        return this;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public DigitalContent authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public DigitalContent coverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
        return this;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getCoverPhotoContentType() {
        return coverPhotoContentType;
    }

    public DigitalContent coverPhotoContentType(String coverPhotoContentType) {
        this.coverPhotoContentType = coverPhotoContentType;
        return this;
    }

    public void setCoverPhotoContentType(String coverPhotoContentType) {
        this.coverPhotoContentType = coverPhotoContentType;
    }

    public String getCoverPhotoType() {
        return coverPhotoType;
    }

    public DigitalContent coverPhotoType(String coverPhotoType) {
        this.coverPhotoType = coverPhotoType;
        return this;
    }

    public void setCoverPhotoType(String coverPhotoType) {
        this.coverPhotoType = coverPhotoType;
    }

    public String getCoverPhotoName() {
        return coverPhotoName;
    }

    public DigitalContent coverPhotoName(String coverPhotoName) {
        this.coverPhotoName = coverPhotoName;
        return this;
    }

    public void setCoverPhotoName(String coverPhotoName) {
        this.coverPhotoName = coverPhotoName;
    }

    public byte[] getContent() {
        return content;
    }

    public DigitalContent content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public DigitalContent contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getContentType() {
        return contentType;
    }

    public DigitalContent contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentName() {
        return contentName;
    }

    public DigitalContent contentName(String contentName) {
        this.contentName = contentName;
        return this;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public DigitalContent createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public DigitalContent updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public DigitalContent createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public DigitalContent updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public BookSubCategory getBookSubCategory() {
        return bookSubCategory;
    }

    public DigitalContent bookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategory = bookSubCategory;
        return this;
    }

    public void setBookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategory = bookSubCategory;
    }

    public FileType getFileType() {
        return fileType;
    }

    public DigitalContent fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DigitalContent digitalContent = (DigitalContent) o;
        if (digitalContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), digitalContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DigitalContent{" +
            "id=" + getId() +
            ", accessionNo='" + getAccessionNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", isbnNo='" + getIsbnNo() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", coverPhoto='" + getCoverPhoto() + "'" +
            ", coverPhotoContentType='" + getCoverPhotoContentType() + "'" +
            ", coverPhotoType='" + getCoverPhotoType() + "'" +
            ", coverPhotoName='" + getCoverPhotoName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", contentName='" + getContentName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
