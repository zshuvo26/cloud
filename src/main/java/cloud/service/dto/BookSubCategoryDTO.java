package cloud.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookSubCategory entity.
 */
public class BookSubCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean pStatus;

    private Long bookCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean ispStatus() {
        return pStatus;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public Long getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(Long bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookSubCategoryDTO bookSubCategoryDTO = (BookSubCategoryDTO) o;
        if(bookSubCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookSubCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookSubCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
