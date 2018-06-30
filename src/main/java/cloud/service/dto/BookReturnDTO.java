package cloud.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BookReturn entity.
 */
public class BookReturnDTO implements Serializable {

    private Long id;

    private String receivedStatus;

    private String totalFine;

    private String fineDeposit;

    private Boolean remissionStatus;

    private String compensation;

    private String compensationDeposit;

    private String compensationFineDeposit;

    private Boolean remissionCompensationStatus;

    private Boolean cfFineStatus;

    private Boolean cfCompensationStatus;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Integer createBy;

    private Integer updateBy;

    private Long bookIssueId;

    private Long bookFineSettingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public String getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(String totalFine) {
        this.totalFine = totalFine;
    }

    public String getFineDeposit() {
        return fineDeposit;
    }

    public void setFineDeposit(String fineDeposit) {
        this.fineDeposit = fineDeposit;
    }

    public Boolean isRemissionStatus() {
        return remissionStatus;
    }

    public void setRemissionStatus(Boolean remissionStatus) {
        this.remissionStatus = remissionStatus;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getCompensationDeposit() {
        return compensationDeposit;
    }

    public void setCompensationDeposit(String compensationDeposit) {
        this.compensationDeposit = compensationDeposit;
    }

    public String getCompensationFineDeposit() {
        return compensationFineDeposit;
    }

    public void setCompensationFineDeposit(String compensationFineDeposit) {
        this.compensationFineDeposit = compensationFineDeposit;
    }

    public Boolean isRemissionCompensationStatus() {
        return remissionCompensationStatus;
    }

    public void setRemissionCompensationStatus(Boolean remissionCompensationStatus) {
        this.remissionCompensationStatus = remissionCompensationStatus;
    }

    public Boolean isCfFineStatus() {
        return cfFineStatus;
    }

    public void setCfFineStatus(Boolean cfFineStatus) {
        this.cfFineStatus = cfFineStatus;
    }

    public Boolean isCfCompensationStatus() {
        return cfCompensationStatus;
    }

    public void setCfCompensationStatus(Boolean cfCompensationStatus) {
        this.cfCompensationStatus = cfCompensationStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Long getBookIssueId() {
        return bookIssueId;
    }

    public void setBookIssueId(Long bookIssueId) {
        this.bookIssueId = bookIssueId;
    }

    public Long getBookFineSettingId() {
        return bookFineSettingId;
    }

    public void setBookFineSettingId(Long bookFineSettingId) {
        this.bookFineSettingId = bookFineSettingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookReturnDTO bookReturnDTO = (BookReturnDTO) o;
        if(bookReturnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookReturnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookReturnDTO{" +
            "id=" + getId() +
            ", receivedStatus='" + getReceivedStatus() + "'" +
            ", totalFine='" + getTotalFine() + "'" +
            ", fineDeposit='" + getFineDeposit() + "'" +
            ", remissionStatus='" + isRemissionStatus() + "'" +
            ", compensation='" + getCompensation() + "'" +
            ", compensationDeposit='" + getCompensationDeposit() + "'" +
            ", compensationFineDeposit='" + getCompensationFineDeposit() + "'" +
            ", remissionCompensationStatus='" + isRemissionCompensationStatus() + "'" +
            ", cfFineStatus='" + isCfFineStatus() + "'" +
            ", cfCompensationStatus='" + isCfCompensationStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
