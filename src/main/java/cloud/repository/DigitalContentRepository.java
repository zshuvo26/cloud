package cloud.repository;

import cloud.domain.DigitalContent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DigitalContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DigitalContentRepository extends JpaRepository<DigitalContent, Long> {

}
