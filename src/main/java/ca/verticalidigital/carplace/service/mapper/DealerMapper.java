package ca.verticalidigital.carplace.service.mapper;

import ca.verticalidigital.carplace.domain.Dealer;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DealerMapper extends EntityMapper<DealerDTO, Dealer> {

}
