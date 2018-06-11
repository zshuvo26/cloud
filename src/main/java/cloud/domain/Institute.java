package cloud.domain;

import cloud.domain.enumeration.InstituteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Institute.
 */
@Entity
@Table(name = "institute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Institute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "estd_date")
    private LocalDate estdDate;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "contact_no")
    private String contactNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "institute_type")
    private InstituteType instituteType;

    @ManyToOne
    private Upazila upazila;

    @OneToOne
    @JoinColumn(unique = false)
    private City city;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "institute")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Department> departments = new HashSet<>();

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

    public Institute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEstdDate() {
        return estdDate;
    }

    public Institute estdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
        return this;
    }

    public void setEstdDate(LocalDate estdDate) {
        this.estdDate = estdDate;
    }

    public String getEmail() {
        return email;
    }

    public Institute email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public Institute website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Institute contactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public InstituteType getInstituteType() {
        return instituteType;
    }

    public Institute instituteType(InstituteType instituteType) {
        this.instituteType = instituteType;
        return this;
    }

    public void setInstituteType(InstituteType instituteType) {
        this.instituteType = instituteType;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public Institute upazila(Upazila upazila) {
        this.upazila = upazila;
        return this;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public City getCity() {
        return city;
    }

    public Institute city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public Institute user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Institute departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Institute addDepartment(Department department) {
        this.departments.add(department);
        department.setInstitute(this);
        return this;
    }

    public Institute removeDepartment(Department department) {
        this.departments.remove(department);
        department.setInstitute(null);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
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
        Institute institute = (Institute) o;
        if (institute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Institute{" +
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
