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

/**
 * A BookIssue.
 */
@Entity
@Table(name = "book_issue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "no_of_copies", nullable = false)
    private String noOfCopies;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private BookInfo bookInfo;

    @OneToMany(mappedBy = "bookIssue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookReturn> bookReturns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoOfCopies() {
        return noOfCopies;
    }

    public BookIssue noOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
        return this;
    }

    public void setNoOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BookIssue returnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public BookIssue createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public BookIssue updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public BookIssue createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public BookIssue updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public BookIssue bookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        return this;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public Set<BookReturn> getBookReturns() {
        return bookReturns;
    }

    public BookIssue bookReturns(Set<BookReturn> bookReturns) {
        this.bookReturns = bookReturns;
        return this;
    }

    public BookIssue addBookReturn(BookReturn bookReturn) {
        this.bookReturns.add(bookReturn);
        bookReturn.setBookIssue(this);
        return this;
    }

    public BookIssue removeBookReturn(BookReturn bookReturn) {
        this.bookReturns.remove(bookReturn);
        bookReturn.setBookIssue(null);
        return this;
    }

    public void setBookReturns(Set<BookReturn> bookReturns) {
        this.bookReturns = bookReturns;
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
        BookIssue bookIssue = (BookIssue) o;
        if (bookIssue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookIssue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookIssue{" +
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
