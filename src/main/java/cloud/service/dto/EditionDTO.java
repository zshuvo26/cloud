package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Edition entity.
 */
public class EditionDTO implements Serializable {

    private Long id;

    @NotNull
    private String editionName;

    private String totalCopies;

    private String compensation;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private Integer updateBy;

    private Long bookInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
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

    public Long getBookInfoId() {
        return bookInfoId;
    }

    public void setBookInfoId(Long bookInfoId) {
        this.bookInfoId = bookInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EditionDTO editionDTO = (EditionDTO) o;
        if(editionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), editionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EditionDTO{" +
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
