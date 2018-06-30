package cloud.repository;

import cloud.domain.BookInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {

}
