package cloud.repository;

import cloud.domain.BookType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Long> {

}
