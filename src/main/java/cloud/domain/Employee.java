package cloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import cloud.domain.enumeration.Gender;

import cloud.domain.enumeration.EmployeeType;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "photo_type")
    private String photoType;

    @Column(name = "photo_name")
    private String photoName;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @Column(name = "signature_type")
    private String signatureType;

    @Column(name = "signature_name")
    private String signatureName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "nid")
    private String nid;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Designation designation;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Employee employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Employee photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Employee photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoType() {
        return photoType;
    }

    public Employee photoType(String photoType) {
        this.photoType = photoType;
        return this;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getPhotoName() {
        return photoName;
    }

    public Employee photoName(String photoName) {
        this.photoName = photoName;
        return this;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Employee signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public Employee signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public Employee signatureType(String signatureType) {
        this.signatureType = signatureType;
        return this;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public Employee signatureName(String signatureName) {
        this.signatureName = signatureName;
        return this;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Employee dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNid() {
        return nid;
    }

    public Employee nid(String nid) {
        this.nid = nid;
        return this;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public Employee employeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Employee createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Employee updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public Employee createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public Employee updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Employee designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public User getUser() {
        return user;
    }

    public Employee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", photoType='" + getPhotoType() + "'" +
            ", photoName='" + getPhotoName() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            ", signatureType='" + getSignatureType() + "'" +
            ", signatureName='" + getSignatureName() + "'" +
            ", dob='" + getDob() + "'" +
            ", nid='" + getNid() + "'" +
            ", gender='" + getGender() + "'" +
            ", employeeType='" + getEmployeeType() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
