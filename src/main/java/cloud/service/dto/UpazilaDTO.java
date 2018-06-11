package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Upazila entity.
 */
public class UpazilaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate estdDate;

    private Long districtId;

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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpazilaDTO upazilaDTO = (UpazilaDTO) o;
        if(upazilaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), upazilaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UpazilaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            "}";
    }
}
