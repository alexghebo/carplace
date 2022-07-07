package ca.verticalidigital.carplace.service.impl;

import ca.verticalidigital.carplace.domain.Dealer;
import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.service.DealerService;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import ca.verticalidigital.carplace.service.mapper.DealerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class DealerServiceImpl implements DealerService {


    private final DealerMapper dealerMapper;
    private final DealerRepository dealerRepository;

    public DealerServiceImpl(DealerMapper dealerMapper, DealerRepository dealerRepository) {
        this.dealerMapper = dealerMapper;
        this.dealerRepository = dealerRepository;
    }

    @Override
    public void createDealer(Dealer dealer) {
        dealerRepository.save(dealer);
    }

    @Override
    public DealerDTO save(DealerDTO dealerDTO) {
        Dealer dealer = dealerMapper.toEntity(dealerDTO);
        dealer = dealerRepository.save(dealer);
        return dealerMapper.toDto(dealer);
    }

    @Override
    public DealerDTO update(DealerDTO dealerDTO) {
        Dealer dealer = dealerMapper.toEntity(dealerDTO);
        dealer = dealerRepository.save(dealer);
        return dealerMapper.toDto(dealer);
    }

    @Override
    public void deleteById(Long id) {
        dealerRepository.deleteById(id);
    }

    @Override
    public Page<DealerDTO> getAllManagedDealers(Pageable pageable) {
        return dealerRepository.findAll(pageable).map(DealerDTO::new);

    }

    @Override
    public Optional<Dealer> getOneByName(String name) {
        return dealerRepository.findByDealerName(name);
    }

    @Override
    @Transactional()
    public Optional<DealerDTO> findOne(Long id) {
        return dealerRepository.findById(id).map(dealerMapper::toDto);
    }

}
