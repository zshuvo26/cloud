package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.InstituteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Institute and its DTO InstituteDTO.
 */
@Mapper(componentModel = "spring", uses = {UpazilaMapper.class, CityMapper.class, UserMapper.class})
public interface InstituteMapper extends EntityMapper<InstituteDTO, Institute> {

    @Mapping(source = "upazila.id", target = "upazilaId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "user.id", target = "userId")
    InstituteDTO toDto(Institute institute);

    @Mapping(source = "upazilaId", target = "upazila")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "departments", ignore = true)
    Institute toEntity(InstituteDTO instituteDTO);

    default Institute fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institute institute = new Institute();
        institute.setId(id);
        return institute;
    }
}
