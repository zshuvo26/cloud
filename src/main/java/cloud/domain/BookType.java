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
 * A BookType.
 */
@Entity
@Table(name = "book_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "p_status")
    private Boolean pStatus;

    @OneToMany(mappedBy = "bookType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookCategory> bookCategories = new HashSet<>();

    @OneToMany(mappedBy = "bookType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookFineSetting> bookFineSettings = new HashSet<>();

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

    public BookType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean ispStatus() {
        return pStatus;
    }

    public BookType pStatus(Boolean pStatus) {
        this.pStatus = pStatus;
        return this;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public Set<BookCategory> getBookCategories() {
        return bookCategories;
    }

    public BookType bookCategories(Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
        return this;
    }

    public BookType addBookCategory(BookCategory bookCategory) {
        this.bookCategories.add(bookCategory);
        bookCategory.setBookType(this);
        return this;
    }

    public BookType removeBookCategory(BookCategory bookCategory) {
        this.bookCategories.remove(bookCategory);
        bookCategory.setBookType(null);
        return this;
    }

    public void setBookCategories(Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
    }

    public Set<BookFineSetting> getBookFineSettings() {
        return bookFineSettings;
    }

    public BookType bookFineSettings(Set<BookFineSetting> bookFineSettings) {
        this.bookFineSettings = bookFineSettings;
        return this;
    }

    public BookType addBookFineSetting(BookFineSetting bookFineSetting) {
        this.bookFineSettings.add(bookFineSetting);
        bookFineSetting.setBookType(this);
        return this;
    }

    public BookType removeBookFineSetting(BookFineSetting bookFineSetting) {
        this.bookFineSettings.remove(bookFineSetting);
        bookFineSetting.setBookType(null);
        return this;
    }

    public void setBookFineSettings(Set<BookFineSetting> bookFineSettings) {
        this.bookFineSettings = bookFineSettings;
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
        BookType bookType = (BookType) o;
        if (bookType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
