package cloud.repository;

import cloud.domain.FileType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {

}
