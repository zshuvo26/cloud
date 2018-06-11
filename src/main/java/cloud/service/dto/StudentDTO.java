package cloud.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import cloud.domain.enumeration.Gender;

/**
 * A DTO for the Student entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String fatherName;

    @NotNull
    private String motherName;

    private String studentId;

    private String email;

    private String phoneNumber;

    @Lob
    private byte[] photo;
    private String photoContentType;

    private String photoType;

    private String photoName;

    @Lob
    private byte[] signature;
    private String signatureContentType;

    private String signatureType;

    private String signatureName;

    private LocalDate dob;

    private String nid;

    private String birthCertNo;

    private Gender gender;

    private Long upazilaId;

    private Long departmentId;

    private Long sessionId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBirthCertNo() {
        return birthCertNo;
    }

    public void setBirthCertNo(String birthCertNo) {
        this.birthCertNo = birthCertNo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getUpazilaId() {
        return upazilaId;
    }

    public void setUpazilaId(Long upazilaId) {
        this.upazilaId = upazilaId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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

        StudentDTO studentDTO = (StudentDTO) o;
        if(studentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", studentId='" + getStudentId() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoType='" + getPhotoType() + "'" +
            ", photoName='" + getPhotoName() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signatureType='" + getSignatureType() + "'" +
            ", signatureName='" + getSignatureName() + "'" +
            ", dob='" + getDob() + "'" +
            ", nid='" + getNid() + "'" +
            ", birthCertNo='" + getBirthCertNo() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
