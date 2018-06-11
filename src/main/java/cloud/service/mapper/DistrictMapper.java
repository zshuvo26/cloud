package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.DistrictDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity District and its DTO DistrictDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class})
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {

    @Mapping(source = "division.id", target = "divisionId")
    DistrictDTO toDto(District district);

    @Mapping(source = "divisionId", target = "division")
    @Mapping(target = "upazilas", ignore = true)
    District toEntity(DistrictDTO districtDTO);

    default District fromId(Long id) {
        if (id == null) {
            return null;
        }
        District district = new District();
        district.setId(id);
        return district;
    }
}
