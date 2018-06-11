package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Division entity.
 */
public class DivisionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate estdDate;

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

    public LocalDate getEstdDate() {
        return estdDate;
    }

    public void setEstdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DivisionDTO divisionDTO = (DivisionDTO) o;
        if(divisionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), divisionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DivisionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            "}";
    }
}
