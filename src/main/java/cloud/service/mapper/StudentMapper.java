package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {UpazilaMapper.class, DepartmentMapper.class, SessionMapper.class, UserMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "upazila.id", target = "upazilaId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "user.id", target = "userId")
    StudentDTO toDto(Student student);

    @Mapping(source = "upazilaId", target = "upazila")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "sessionId", target = "session")
    @Mapping(source = "userId", target = "user")
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
