package cloud.repository;

import cloud.domain.BookRequisition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookRequisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRequisitionRepository extends JpaRepository<BookRequisition, Long> {

}
