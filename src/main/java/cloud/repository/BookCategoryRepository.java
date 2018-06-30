package cloud.repository;

import cloud.domain.BookCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

}
