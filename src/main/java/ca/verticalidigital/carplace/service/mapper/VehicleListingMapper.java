package ca.verticalidigital.carplace.service.mapper;

import ca.verticalidigital.carplace.domain.CarModel;
import ca.verticalidigital.carplace.domain.VehicleListing;
import ca.verticalidigital.carplace.service.dto.CarModelDTO;
import ca.verticalidigital.carplace.service.dto.VehicleListingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link VehicleListing} and its DTO {@link VehicleListingDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleListingMapper extends EntityMapper<VehicleListingDTO, VehicleListing> {
    @Mapping(target = "carModel", source = "carModel", qualifiedByName = "carModelId")
    VehicleListingDTO toDto(VehicleListing s);

    @Named("carModelId")
    CarModelDTO toDtoCarModelId(CarModel carModel);
}
