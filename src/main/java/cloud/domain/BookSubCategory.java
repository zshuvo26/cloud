package cloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BookSubCategory.
 */
@Entity
@Table(name = "book_sub_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookSubCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "p_status")
    private Boolean pStatus;

    @ManyToOne
    private BookCategory bookCategory;

    @OneToMany(mappedBy = "bookSubCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookInfo> bookInfos = new HashSet<>();

    @OneToMany(mappedBy = "bookSubCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookRequisition> bookRequisitions = new HashSet<>();

    @OneToMany(mappedBy = "bookSubCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DigitalContent> digitalContents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BookSubCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean ispStatus() {
        return pStatus;
    }

    public BookSubCategory pStatus(Boolean pStatus) {
        this.pStatus = pStatus;
        return this;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public BookSubCategory bookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Set<BookInfo> getBookInfos() {
        return bookInfos;
    }

    public BookSubCategory bookInfos(Set<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
        return this;
    }

    public BookSubCategory addBookInfo(BookInfo bookInfo) {
        this.bookInfos.add(bookInfo);
        bookInfo.setBookSubCategory(this);
        return this;
    }

    public BookSubCategory removeBookInfo(BookInfo bookInfo) {
        this.bookInfos.remove(bookInfo);
        bookInfo.setBookSubCategory(null);
        return this;
    }

    public void setBookInfos(Set<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
    }

    public Set<BookRequisition> getBookRequisitions() {
        return bookRequisitions;
    }

    public BookSubCategory bookRequisitions(Set<BookRequisition> bookRequisitions) {
        this.bookRequisitions = bookRequisitions;
        return this;
    }

    public BookSubCategory addBookRequisition(BookRequisition bookRequisition) {
        this.bookRequisitions.add(bookRequisition);
        bookRequisition.setBookSubCategory(this);
        return this;
    }

    public BookSubCategory removeBookRequisition(BookRequisition bookRequisition) {
        this.bookRequisitions.remove(bookRequisition);
        bookRequisition.setBookSubCategory(null);
        return this;
    }

    public void setBookRequisitions(Set<BookRequisition> bookRequisitions) {
        this.bookRequisitions = bookRequisitions;
    }

    public Set<DigitalContent> getDigitalContents() {
        return digitalContents;
    }

    public BookSubCategory digitalContents(Set<DigitalContent> digitalContents) {
        this.digitalContents = digitalContents;
        return this;
    }

    public BookSubCategory addDigitalContent(DigitalContent digitalContent) {
        this.digitalContents.add(digitalContent);
        digitalContent.setBookSubCategory(this);
        return this;
    }

    public BookSubCategory removeDigitalContent(DigitalContent digitalContent) {
        this.digitalContents.remove(digitalContent);
        digitalContent.setBookSubCategory(null);
        return this;
    }

    public void setDigitalContents(Set<DigitalContent> digitalContents) {
        this.digitalContents = digitalContents;
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
        BookSubCategory bookSubCategory = (BookSubCategory) o;
        if (bookSubCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookSubCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookSubCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
