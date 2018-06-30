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
 * A Publisher.
 */
@Entity
@Table(name = "publisher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "place")
    private String place;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookInfo> bookInfos = new HashSet<>();

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

    public Publisher name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public Publisher place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Publisher createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Publisher updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public Publisher createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public Publisher updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Set<BookInfo> getBookInfos() {
        return bookInfos;
    }

    public Publisher bookInfos(Set<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
        return this;
    }

    public Publisher addBookInfo(BookInfo bookInfo) {
        this.bookInfos.add(bookInfo);
        bookInfo.setPublisher(this);
        return this;
    }

    public Publisher removeBookInfo(BookInfo bookInfo) {
        this.bookInfos.remove(bookInfo);
        bookInfo.setPublisher(null);
        return this;
    }

    public void setBookInfos(Set<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
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
        Publisher publisher = (Publisher) o;
        if (publisher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publisher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Publisher{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", place='" + getPlace() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
