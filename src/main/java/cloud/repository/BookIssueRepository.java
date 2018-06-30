package cloud.repository;

import cloud.domain.BookIssue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookIssue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {

}
