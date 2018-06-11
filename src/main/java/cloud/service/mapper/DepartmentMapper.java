package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {InstituteMapper.class})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {

    @Mapping(source = "institute.id", target = "instituteId")
    DepartmentDTO toDto(Department department);

    @Mapping(source = "instituteId", target = "institute")
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
