package cloud.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FileType entity.
 */
public class FileTypeDTO implements Serializable {

    private Long id;

    private String fileType;

    private String sizeLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileTypeDTO fileTypeDTO = (FileTypeDTO) o;
        if(fileTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileTypeDTO{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", sizeLimit='" + getSizeLimit() + "'" +
            "}";
    }
}
