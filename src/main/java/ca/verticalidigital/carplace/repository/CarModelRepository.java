package ca.verticalidigital.carplace.repository;

import ca.verticalidigital.carplace.domain.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CarModel entity.
 */
@Repository
public interface CarModelRepository
    extends CarModelRepositoryWithBagRelationships, JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel> {
    default Optional<CarModel> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CarModel> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CarModel> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    Optional<CarModel> findByMakeAndModelAndLaunchYear(String make, String model, Integer launchYear);
}
