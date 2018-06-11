package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.SessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Session and its DTO SessionDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {

    @Mapping(source = "department.id", target = "departmentId")
    SessionDTO toDto(Session session);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(target = "students", ignore = true)
    Session toEntity(SessionDTO sessionDTO);

    default Session fromId(Long id) {
        if (id == null) {
            return null;
        }
        Session session = new Session();
        session.setId(id);
        return session;
    }
}
