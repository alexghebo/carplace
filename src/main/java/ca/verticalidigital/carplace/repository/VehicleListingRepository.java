package ca.verticalidigital.carplace.repository;

import ca.verticalidigital.carplace.domain.VehicleListing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the VehicleListing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long>, JpaSpecificationExecutor<VehicleListing> {
    List<VehicleListing> findByInternalNumberIn(List<String> list);
}
