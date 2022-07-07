package ca.verticalidigital.carplace.service;

import ca.verticalidigital.carplace.domain.Dealer;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DealerService {

    void createDealer(Dealer dealer);

    DealerDTO save(DealerDTO dealerDTO);

    DealerDTO update(DealerDTO dealerDTO);

    void deleteById(Long id);


    Optional<DealerDTO> findOne(Long id);


    Page<DealerDTO> getAllManagedDealers(Pageable pageable);

    Optional<Dealer> getOneByName(String name);
}
