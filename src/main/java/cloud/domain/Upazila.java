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
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "upazila")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Upazila implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "estd_date")
    private LocalDate estdDate;

    @ManyToOne
    private District district;

    @OneToMany(mappedBy = "upazila")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "upazila")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Institute> institutes = new HashSet<>();

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

    public Upazila name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEstdDate() {
        return estdDate;
    }

    public Upazila estdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
        return this;
    }

    public void setEstdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
    }

    public District getDistrict() {
        return district;
    }

    public Upazila district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Upazila students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Upazila addStudent(Student student) {
        this.students.add(student);
        student.setUpazila(this);
        return this;
    }

    public Upazila removeStudent(Student student) {
        this.students.remove(student);
        student.setUpazila(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Institute> getInstitutes() {
        return institutes;
    }

    public Upazila institutes(Set<Institute> institutes) {
        this.institutes = institutes;
        return this;
    }

    public Upazila addInstitute(Institute institute) {
        this.institutes.add(institute);
        institute.setUpazila(this);
        return this;
    }

    public Upazila removeInstitute(Institute institute) {
        this.institutes.remove(institute);
        institute.setUpazila(null);
        return this;
    }

    public void setInstitutes(Set<Institute> institutes) {
        this.institutes = institutes;
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
        Upazila upazila = (Upazila) o;
        if (upazila.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), upazila.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Upazila{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estdDate='" + getEstdDate() + "'" +
            "}";
    }
}
