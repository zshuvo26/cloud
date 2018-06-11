package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import cloud.domain.enumeration.InstituteType;

/**
 * A DTO for the Institute entity.
 */
public class InstituteDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate estdDate;

    private String email;

    private String website;

    private String contactNo;

    private InstituteType instituteType;

    private Long upazilaId;

    private Long cityId;

    private Long userId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public InstituteType getInstituteType() {
        return instituteType;
    }

    public void setInstituteType(InstituteType instituteType) {
        this.instituteType = instituteType;
    }

    public Long getUpazilaId() {
        return upazilaId;
    }

    public void setUpazilaId(Long upazilaId) {
        this.upazilaId = upazilaId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteDTO instituteDTO = (InstituteDTO) o;
        if(instituteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instituteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstituteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", instituteType='" + getInstituteType() + "'" +
            "}";
    }
}
