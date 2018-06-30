package cloud.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BookReturn.
 */
@Entity
@Table(name = "book_return")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "received_status")
    private String receivedStatus;

    @Column(name = "total_fine")
    private String totalFine;

    @Column(name = "fine_deposit")
    private String fineDeposit;

    @Column(name = "remission_status")
    private Boolean remissionStatus;

    @Column(name = "compensation")
    private String compensation;

    @Column(name = "compensation_deposit")
    private String compensationDeposit;

    @Column(name = "compensation_fine_deposit")
    private String compensationFineDeposit;

    @Column(name = "remission_compensation_status")
    private Boolean remissionCompensationStatus;

    @Column(name = "cf_fine_status")
    private Boolean cfFineStatus;

    @Column(name = "cf_compensation_status")
    private Boolean cfCompensationStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne
    private BookIssue bookIssue;

    @ManyToOne
    private BookFineSetting bookFineSetting;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceivedStatus() {
        return receivedStatus;
    }

    public BookReturn receivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
        return this;
    }

    public void setReceivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public String getTotalFine() {
        return totalFine;
    }

    public BookReturn totalFine(String totalFine) {
        this.totalFine = totalFine;
        return this;
    }

    public void setTotalFine(String totalFine) {
        this.totalFine = totalFine;
    }

    public String getFineDeposit() {
        return fineDeposit;
    }

    public BookReturn fineDeposit(String fineDeposit) {
        this.fineDeposit = fineDeposit;
        return this;
    }

    public void setFineDeposit(String fineDeposit) {
        this.fineDeposit = fineDeposit;
    }

    public Boolean isRemissionStatus() {
        return remissionStatus;
    }

    public BookReturn remissionStatus(Boolean remissionStatus) {
        this.remissionStatus = remissionStatus;
        return this;
    }

    public void setRemissionStatus(Boolean remissionStatus) {
        this.remissionStatus = remissionStatus;
    }

    public String getCompensation() {
        return compensation;
    }

    public BookReturn compensation(String compensation) {
        this.compensation = compensation;
        return this;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getCompensationDeposit() {
        return compensationDeposit;
    }

    public BookReturn compensationDeposit(String compensationDeposit) {
        this.compensationDeposit = compensationDeposit;
        return this;
    }

    public void setCompensationDeposit(String compensationDeposit) {
        this.compensationDeposit = compensationDeposit;
    }

    public String getCompensationFineDeposit() {
        return compensationFineDeposit;
    }

    public BookReturn compensationFineDeposit(String compensationFineDeposit) {
        this.compensationFineDeposit = compensationFineDeposit;
        return this;
    }

    public void setCompensationFineDeposit(String compensationFineDeposit) {
        this.compensationFineDeposit = compensationFineDeposit;
    }

    public Boolean isRemissionCompensationStatus() {
        return remissionCompensationStatus;
    }

    public BookReturn remissionCompensationStatus(Boolean remissionCompensationStatus) {
        this.remissionCompensationStatus = remissionCompensationStatus;
        return this;
    }

    public void setRemissionCompensationStatus(Boolean remissionCompensationStatus) {
        this.remissionCompensationStatus = remissionCompensationStatus;
    }

    public Boolean isCfFineStatus() {
        return cfFineStatus;
    }

    public BookReturn cfFineStatus(Boolean cfFineStatus) {
        this.cfFineStatus = cfFineStatus;
        return this;
    }

    public void setCfFineStatus(Boolean cfFineStatus) {
        this.cfFineStatus = cfFineStatus;
    }

    public Boolean isCfCompensationStatus() {
        return cfCompensationStatus;
    }

    public BookReturn cfCompensationStatus(Boolean cfCompensationStatus) {
        this.cfCompensationStatus = cfCompensationStatus;
        return this;
    }

    public void setCfCompensationStatus(Boolean cfCompensationStatus) {
        this.cfCompensationStatus = cfCompensationStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public BookReturn createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public BookReturn updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public BookReturn createBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public BookReturn updateBy(Integer updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public BookIssue getBookIssue() {
        return bookIssue;
    }

    public BookReturn bookIssue(BookIssue bookIssue) {
        this.bookIssue = bookIssue;
        return this;
    }

    public void setBookIssue(BookIssue bookIssue) {
        this.bookIssue = bookIssue;
    }

    public BookFineSetting getBookFineSetting() {
        return bookFineSetting;
    }

    public BookReturn bookFineSetting(BookFineSetting bookFineSetting) {
        this.bookFineSetting = bookFineSetting;
        return this;
    }

    public void setBookFineSetting(BookFineSetting bookFineSetting) {
        this.bookFineSetting = bookFineSetting;
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
        BookReturn bookReturn = (BookReturn) o;
        if (bookReturn.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookReturn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookReturn{" +
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
