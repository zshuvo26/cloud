package cloud.service.mapper;

import cloud.domain.*;
import cloud.service.dto.UpazilaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Upazila and its DTO UpazilaDTO.
 */
@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface UpazilaMapper extends EntityMapper<UpazilaDTO, Upazila> {

    @Mapping(source = "district.id", target = "districtId")
    UpazilaDTO toDto(Upazila upazila);

    @Mapping(source = "districtId", target = "district")
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "institutes", ignore = true)
    Upazila toEntity(UpazilaDTO upazilaDTO);

    default Upazila fromId(Long id) {
        if (id == null) {
            return null;
        }
        Upazila upazila = new Upazila();
        upazila.setId(id);
        return upazila;
    }
}
