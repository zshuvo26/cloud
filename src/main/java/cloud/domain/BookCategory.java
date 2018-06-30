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
 * A BookCategory.
 */
@Entity
@Table(name = "book_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "p_status")
    private Boolean pStatus;

    @OneToMany(mappedBy = "bookCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookSubCategory> bookSubCategories = new HashSet<>();

    @ManyToOne
    private BookType bookType;

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

    public BookCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean ispStatus() {
        return pStatus;
    }

    public BookCategory pStatus(Boolean pStatus) {
        this.pStatus = pStatus;
        return this;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public Set<BookSubCategory> getBookSubCategories() {
        return bookSubCategories;
    }

    public BookCategory bookSubCategories(Set<BookSubCategory> bookSubCategories) {
        this.bookSubCategories = bookSubCategories;
        return this;
    }

    public BookCategory addBookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategories.add(bookSubCategory);
        bookSubCategory.setBookCategory(this);
        return this;
    }

    public BookCategory removeBookSubCategory(BookSubCategory bookSubCategory) {
        this.bookSubCategories.remove(bookSubCategory);
        bookSubCategory.setBookCategory(null);
        return this;
    }

    public void setBookSubCategories(Set<BookSubCategory> bookSubCategories) {
        this.bookSubCategories = bookSubCategories;
    }

    public BookType getBookType() {
        return bookType;
    }

    public BookCategory bookType(BookType bookType) {
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
        BookCategory bookCategory = (BookCategory) o;
        if (bookCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
