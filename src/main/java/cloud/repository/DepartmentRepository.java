package cloud.repository;

import cloud.domain.Department;
import cloud.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select department from Department department where department.institute.id=:id")
    Page<Department> findDepartmentsByInstitute(Pageable pageable, @Param("id") Long id);


}
