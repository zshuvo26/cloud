package cloud.repository;

import cloud.domain.BookReturn;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookReturn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookReturnRepository extends JpaRepository<BookReturn, Long> {

}
