package cloud.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookType entity.
 */
public class BookTypeDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean pStatus;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookTypeDTO bookTypeDTO = (BookTypeDTO) o;
        if(bookTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pStatus='" + ispStatus() + "'" +
            "}";
    }
}
