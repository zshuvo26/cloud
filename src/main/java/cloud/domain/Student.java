package cloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import cloud.domain.enumeration.Gender;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

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

    @Column(name = "father_name")
    private String fatherName;

    @NotNull
    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @Column(name = "student_id")
    private String studentId;

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

    @Column(name = "birth_cert_no")
    private String birthCertNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private Upazila upazila;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Session session;

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

    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Student fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public Student motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getStudentId() {
        return studentId;
    }

    public Student studentId(String studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public Student email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Student phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Student photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Student photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoType() {
        return photoType;
    }

    public Student photoType(String photoType) {
        this.photoType = photoType;
        return this;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getPhotoName() {
        return photoName;
    }

    public Student photoName(String photoName) {
        this.photoName = photoName;
        return this;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Student signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public Student signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public Student signatureType(String signatureType) {
        this.signatureType = signatureType;
        return this;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public Student signatureName(String signatureName) {
        this.signatureName = signatureName;
        return this;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Student dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNid() {
        return nid;
    }

    public Student nid(String nid) {
        this.nid = nid;
        return this;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBirthCertNo() {
        return birthCertNo;
    }

    public Student birthCertNo(String birthCertNo) {
        this.birthCertNo = birthCertNo;
        return this;
    }

    public void setBirthCertNo(String birthCertNo) {
        this.birthCertNo = birthCertNo;
    }

    public Gender getGender() {
        return gender;
    }

    public Student gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Student createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Student updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public Student createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public Student updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public Student upazila(Upazila upazila) {
        this.upazila = upazila;
        return this;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public Department getDepartment() {
        return department;
    }

    public Student department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Session getSession() {
        return session;
    }

    public Student session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public Student user(User user) {
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", studentId='" + getStudentId() + "'" +
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
            ", birthCertNo='" + getBirthCertNo() + "'" +
            ", gender='" + getGender() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
