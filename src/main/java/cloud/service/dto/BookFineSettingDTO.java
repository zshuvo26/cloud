package cloud.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookFineSetting entity.
 */
public class BookFineSettingDTO implements Serializable {

    private Long id;

    private String maxDayForStaff;

    private String maxDayForStudent;

    private String finePerDayForSatff;

    private String finePerDayForStudent;

    private String maxBooksForStaff;

    private String maxBooksForStudnt;

    private Long bookTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaxDayForStaff() {
        return maxDayForStaff;
    }

    public void setMaxDayForStaff(String maxDayForStaff) {
        this.maxDayForStaff = maxDayForStaff;
    }

    public String getMaxDayForStudent() {
        return maxDayForStudent;
    }

    public void setMaxDayForStudent(String maxDayForStudent) {
        this.maxDayForStudent = maxDayForStudent;
    }

    public String getFinePerDayForSatff() {
        return finePerDayForSatff;
    }

    public void setFinePerDayForSatff(String finePerDayForSatff) {
        this.finePerDayForSatff = finePerDayForSatff;
    }

    public String getFinePerDayForStudent() {
        return finePerDayForStudent;
    }

    public void setFinePerDayForStudent(String finePerDayForStudent) {
        this.finePerDayForStudent = finePerDayForStudent;
    }

    public String getMaxBooksForStaff() {
        return maxBooksForStaff;
    }

    public void setMaxBooksForStaff(String maxBooksForStaff) {
        this.maxBooksForStaff = maxBooksForStaff;
    }

    public String getMaxBooksForStudnt() {
        return maxBooksForStudnt;
    }

    public void setMaxBooksForStudnt(String maxBooksForStudnt) {
        this.maxBooksForStudnt = maxBooksForStudnt;
    }

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookFineSettingDTO bookFineSettingDTO = (BookFineSettingDTO) o;
        if(bookFineSettingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookFineSettingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookFineSettingDTO{" +
            "id=" + getId() +
            ", maxDayForStaff='" + getMaxDayForStaff() + "'" +
            ", maxDayForStudent='" + getMaxDayForStudent() + "'" +
            ", finePerDayForSatff='" + getFinePerDayForSatff() + "'" +
            ", finePerDayForStudent='" + getFinePerDayForStudent() + "'" +
            ", maxBooksForStaff='" + getMaxBooksForStaff() + "'" +
            ", maxBooksForStudnt='" + getMaxBooksForStudnt() + "'" +
            "}";
    }
}
