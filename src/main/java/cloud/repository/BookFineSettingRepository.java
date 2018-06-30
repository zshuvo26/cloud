package cloud.repository;

import cloud.domain.BookFineSetting;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookFineSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookFineSettingRepository extends JpaRepository<BookFineSetting, Long> {

}
