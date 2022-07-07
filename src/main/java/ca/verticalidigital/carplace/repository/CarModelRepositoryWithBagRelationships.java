package ca.verticalidigital.carplace.repository;

import ca.verticalidigital.carplace.domain.CarModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CarModelRepositoryWithBagRelationships {
    Optional<CarModel> fetchBagRelationships(Optional<CarModel> carModel);

    List<CarModel> fetchBagRelationships(List<CarModel> carModels);

    Page<CarModel> fetchBagRelationships(Page<CarModel> carModels);
}
