package cloud.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookCategory entity.
 */
public class BookCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean pStatus;

    private Long bookTypeId;

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

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookCategoryDTO bookCategoryDTO = (BookCategoryDTO) o;
        if(bookCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
