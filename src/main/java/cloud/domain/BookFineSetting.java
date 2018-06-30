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
 * A BookFineSetting.
 */
@Entity
@Table(name = "book_fine_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookFineSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_day_for_staff")
    private String maxDayForStaff;

    @Column(name = "max_day_for_student")
    private String maxDayForStudent;

    @Column(name = "fine_per_day_for_satff")
    private String finePerDayForSatff;

    @Column(name = "fine_per_day_for_student")
    private String finePerDayForStudent;

    @Column(name = "max_books_for_staff")
    private String maxBooksForStaff;

    @Column(name = "max_books_for_studnt")
    private String maxBooksForStudnt;

    @OneToMany(mappedBy = "bookFineSetting")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookReturn> bookReturns = new HashSet<>();

    @ManyToOne
    private BookType bookType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaxDayForStaff() {
        return maxDayForStaff;
    }

    public BookFineSetting maxDayForStaff(String maxDayForStaff) {
        this.maxDayForStaff = maxDayForStaff;
        return this;
    }

    public void setMaxDayForStaff(String maxDayForStaff) {
        this.maxDayForStaff = maxDayForStaff;
    }

    public String getMaxDayForStudent() {
        return maxDayForStudent;
    }

    public BookFineSetting maxDayForStudent(String maxDayForStudent) {
        this.maxDayForStudent = maxDayForStudent;
        return this;
    }

    public void setMaxDayForStudent(String maxDayForStudent) {
        this.maxDayForStudent = maxDayForStudent;
    }

    public String getFinePerDayForSatff() {
        return finePerDayForSatff;
    }

    public BookFineSetting finePerDayForSatff(String finePerDayForSatff) {
        this.finePerDayForSatff = finePerDayForSatff;
        return this;
    }

    public void setFinePerDayForSatff(String finePerDayForSatff) {
        this.finePerDayForSatff = finePerDayForSatff;
    }

    public String getFinePerDayForStudent() {
        return finePerDayForStudent;
    }

    public BookFineSetting finePerDayForStudent(String finePerDayForStudent) {
        this.finePerDayForStudent = finePerDayForStudent;
        return this;
    }

    public void setFinePerDayForStudent(String finePerDayForStudent) {
        this.finePerDayForStudent = finePerDayForStudent;
    }

    public String getMaxBooksForStaff() {
        return maxBooksForStaff;
    }

    public BookFineSetting maxBooksForStaff(String maxBooksForStaff) {
        this.maxBooksForStaff = maxBooksForStaff;
        return this;
    }

    public void setMaxBooksForStaff(String maxBooksForStaff) {
        this.maxBooksForStaff = maxBooksForStaff;
    }

    public String getMaxBooksForStudnt() {
        return maxBooksForStudnt;
    }

    public BookFineSetting maxBooksForStudnt(String maxBooksForStudnt) {
        this.maxBooksForStudnt = maxBooksForStudnt;
        return this;
    }

    public void setMaxBooksForStudnt(String maxBooksForStudnt) {
        this.maxBooksForStudnt = maxBooksForStudnt;
    }

    public Set<BookReturn> getBookReturns() {
        return bookReturns;
    }

    public BookFineSetting bookReturns(Set<BookReturn> bookReturns) {
        this.bookReturns = bookReturns;
        return this;
    }

    public BookFineSetting addBookReturn(BookReturn bookReturn) {
        this.bookReturns.add(bookReturn);
        bookReturn.setBookFineSetting(this);
        return this;
    }

    public BookFineSetting removeBookReturn(BookReturn bookReturn) {
        this.bookReturns.remove(bookReturn);
        bookReturn.setBookFineSetting(null);
        return this;
    }

    public void setBookReturns(Set<BookReturn> bookReturns) {
        this.bookReturns = bookReturns;
    }

    public BookType getBookType() {
        return bookType;
    }

    public BookFineSetting bookType(BookType bookType) {
        this.bookType = bookType;
        return this;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
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
        BookFineSetting bookFineSetting = (BookFineSetting) o;
        if (bookFineSetting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookFineSetting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookFineSetting{" +
            "id=" + getId() +
            ", maxDayForStaff='" + getMaxDayForStaff() + "'" +
            ", maxDayForStudent='" + getMaxDayForStudent() + "'" +
            ", finePerDayForSatff='" + getFinePerDayForSatff() + "'" +
            ", finePerDayForStudent='" + getFinePerDayForStudent() + "'" +
            ", maxBooksForStaff='" + getMaxBooksForStaff() + "'" +
            ", maxBooksForStudnt='" + getMaxBooksForStudnt() + "'" +
            "}";
    }
}
