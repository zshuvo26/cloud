package cloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FileType.
 */
@Entity
@Table(name = "file_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size_limit")
    private String sizeLimit;

    @OneToMany(mappedBy = "fileType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DigitalContent> digitalContents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public FileType fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSizeLimit() {
        return sizeLimit;
    }

    public FileType sizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    public void setSizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Set<DigitalContent> getDigitalContents() {
        return digitalContents;
    }

    public FileType digitalContents(Set<DigitalContent> digitalContents) {
        this.digitalContents = digitalContents;
        return this;
    }

    public FileType addDigitalContent(DigitalContent digitalContent) {
        this.digitalContents.add(digitalContent);
        digitalContent.setFileType(this);
        return this;
    }

    public FileType removeDigitalContent(DigitalContent digitalContent) {
        this.digitalContents.remove(digitalContent);
        digitalContent.setFileType(null);
        return this;
    }

    public void setDigitalContents(Set<DigitalContent> digitalContents) {
        this.digitalContents = digitalContents;
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
        FileType fileType = (FileType) o;
        if (fileType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileType{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", sizeLimit='" + getSizeLimit() + "'" +
            "}";
    }
}
