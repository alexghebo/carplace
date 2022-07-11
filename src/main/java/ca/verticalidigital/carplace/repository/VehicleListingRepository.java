package ca.verticalidigital.carplace.repository;

import ca.verticalidigital.carplace.domain.VehicleListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VehicleListing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long>, JpaSpecificationExecutor<VehicleListing> {
    List<VehicleListing> findByInternalNumberIn(List<String> list);
    Optional<VehicleListing> findOneByInternalNumber(String internalNumber);
}
