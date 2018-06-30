package cloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import cloud.domain.enumeration.BookCondition;

import cloud.domain.enumeration.ContBookLang;

/**
 * A BookInfo.
 */
@Entity
@Table(name = "book_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookInfo implements Serializable {

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

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Lob
    @Column(name = "cover_photo")
    private byte[] coverPhoto;

    @Column(name = "cover_photo_content_type")
    private String coverPhotoContentType;

    @Column(name = "cover_photo_type")
    private String coverPhotoType;

    @Column(name = "cover_photo_name")
    private String coverPhotoName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_condition")
    private BookCondition bookCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "cont_book_lang")
    private ContBookLang contBookLang;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private Institute institute;

    @ManyToOne
    private Publisher publisher;

    @OneToMany(mappedBy = "bookInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookIssue> bookIssues = new HashSet<>();

    @OneToMany(mappedBy = "bookInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Edition> editions = new HashSet<>();

    @ManyToOne
    private BookSubCategory bookSubCategory;

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

    public BookInfo accessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
        return this;
    }

    public void setAccessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
    }

    public String getTitle() {
        return title;
    }

    public BookInfo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public BookInfo isbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
        return this;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public BookInfo authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBillNo() {
        return billNo;
    }

    public BookInfo billNo(String billNo) {
        this.billNo = billNo;
        return this;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public BookInfo billDate(LocalDate billDate) {
        this.billDate = billDate;
        return this;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public BookInfo coverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
        return this;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getCoverPhotoContentType() {
        return coverPhotoContentType;
    }

    public BookInfo coverPhotoContentType(String coverPhotoContentType) {
        this.coverPhotoContentType = coverPhotoContentType;
        return this;
    }

    public void setCoverPhotoContentType(String coverPhotoContentType) {
        this.coverPhotoContentType = coverPhotoContentType;
    }

    public String getCoverPhotoType() {
        return coverPhotoType;
    }

    public BookInfo coverPhotoType(String coverPhotoType) {
        this.coverPhotoType = coverPhotoType;
        return this;
    }

    public void setCoverPhotoType(String coverPhotoType) {
        this.coverPhotoType = coverPhotoType;
    }

    public String getCoverPhotoName() {
        return coverPhotoName;
    }

    public BookInfo coverPhotoName(String coverPhotoName) {
        this.coverPhotoName = coverPhotoName;
        return this;
    }

    public void setCoverPhotoName(String coverPhotoName) {
        this.coverPhotoName = coverPhotoName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public BookInfo createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public BookInfo updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public BookInfo createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public BookCondition getBookCondition() {
        return bookCondition;
    }

    public BookInfo bookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
        return this;
    }

    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }

    public ContBookLang getContBookLang() {
        return contBookLang;
    }

    public BookInfo contBookLang(ContBookLang contBookLang) {
        this.contBookLang = contBookLang;
        return this;
    }

    public void setContBookLang(ContBookLang contBookLang) {
        this.contBookLang = contBookLang;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public BookInfo updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Institute getInstitute() {
        return institute;
    }

    public BookInfo institute(Institute institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public BookInfo publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<BookIssue> getBookIssues() {
        return bookIssues;
    }

    public BookInfo bookIssues(Set<BookIssue> bookIssues) {
        this.bookIssues = bookIssues;
        return this;
    }

    public BookInfo addBookIssue(BookIssue bookIssue) {
        this.bookIssues.add(bookIssue);
        bookIssue.setBookInfo(this);
        return this;
    }

    public BookInfo removeBookIssue(BookIssue bookIssue) {
        this.bookIssues.remove(bookIssue);
        bookIssue.setBookInfo(null);
        return this;
    }

    public void setBookIssues(Set<BookIssue> bookIssues) {
        this.bookIssues = bookIssues;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public BookInfo editions(Set<Edition> editions) {
        this.editions = editions;
        return this;
    }

    public BookInfo addEdition(Edition edition) {
        this.editions.add(edition);
        edition.setBookInfo(this);
        return this;
    }

    public BookInfo removeEdition(Edition edition) {
        this.editions.remove(edition);
        edition.setBookInfo(null);
        return this;
    }

    public void setEditions(Set<Edition> editions) {
        this.editions = editions;
    }

    public BookSubCategory getBookSubCategory() {
        return bookSubCategory;
    }

    public BookInfo bookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategory = bookSubCategory;
        return this;
    }

    public void setBookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategory = bookSubCategory;
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
        BookInfo bookInfo = (BookInfo) o;
        if (bookInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookInfo{" +
            "id=" + getId() +
            ", accessionNo='" + getAccessionNo() + "'" +
            ", title='" + getTitle() + "'" +
            ", isbnNo='" + getIsbnNo() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", billNo='" + getBillNo() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", coverPhoto='" + getCoverPhoto() + "'" +
            ", coverPhotoContentType='" + getCoverPhotoContentType() + "'" +
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
