package cloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Edition.
 */
@Entity
@Table(name = "edition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Edition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "edition_name", nullable = false)
    private String editionName;

    @Column(name = "total_copies")
    private String totalCopies;

    @Column(name = "compensation")
    private String compensation;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditionName() {
        return editionName;
    }

    public Edition editionName(String editionName) {
        this.editionName = editionName;
        return this;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public Edition totalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
        return this;
    }

    public void setTotalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
    }

    public String getCompensation() {
        return compensation;
    }

    public Edition compensation(String compensation) {
        this.compensation = compensation;
        return this;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Edition createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Edition updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public Edition createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public Edition updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public Edition bookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        return this;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
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
        Edition edition = (Edition) o;
        if (edition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), edition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Edition{" +
            "id=" + getId() +
            ", editionName='" + getEditionName() + "'" +
            ", totalCopies='" + getTotalCopies() + "'" +
            ", compensation='" + getCompensation() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
