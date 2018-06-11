package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Session entity.
 */
public class SessionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate estdDate;

    private Boolean status;

    private Long departmentId;

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

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if(sessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
