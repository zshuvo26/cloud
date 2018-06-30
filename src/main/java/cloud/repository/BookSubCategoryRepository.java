package cloud.repository;

import cloud.domain.BookSubCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookSubCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookSubCategoryRepository extends JpaRepository<BookSubCategory, Long> {

}
