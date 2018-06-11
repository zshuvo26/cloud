package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.DesignationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Designation and its DTO DesignationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DesignationMapper extends EntityMapper<DesignationDTO, Designation> {


    @Mapping(target = "employees", ignore = true)
    Designation toEntity(DesignationDTO designationDTO);

    default Designation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Designation designation = new Designation();
        designation.setId(id);
        return designation;
    }
}
