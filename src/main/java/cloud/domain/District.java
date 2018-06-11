package cloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "estd_date")
    private LocalDate estdDate;

    @ManyToOne
    private Division division;

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @OneToMany(mappedBy = "district")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Upazila> upazilas = new HashSet<>();

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

    public District name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEstdDate() {
        return estdDate;
    }

    public District estdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
        return this;
    }

    public void setEstdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
    }

    public Division getDivision() {
        return division;
    }

    public District division(Division division) {
        this.division = division;
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Set<Upazila> getUpazilas() {
        return upazilas;
    }

    public District upazilas(Set<Upazila> upazilas) {
        this.upazilas = upazilas;
        return this;
    }

    public District addUpazila(Upazila upazila) {
        this.upazilas.add(upazila);
        upazila.setDistrict(this);
        return this;
    }

    public District removeUpazila(Upazila upazila) {
        this.upazilas.remove(upazila);
        upazila.setDistrict(null);
        return this;
    }

    public void setUpazilas(Set<Upazila> upazilas) {
        this.upazilas = upazilas;
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
        District district = (District) o;
        if (district.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), district.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            "}";
    }
}
