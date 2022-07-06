package ca.verticalidigital.carplace.service;

import ca.verticalidigital.carplace.IntegrationTest;
import ca.verticalidigital.carplace.domain.Dealer;
import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import ca.verticalidigital.carplace.service.mapper.DealerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link DealerService}.
 */

@IntegrationTest
@Transactional
public class DealerServiceIT {

    private static final String DEFAULT_NAME = "Audi";

    private static final String DEFAULT_CITY = "Oradea";

    private static final String DEFAULT_ADDRESS = "STR. Calugarului 45";

    private static final String DEFAULT_PHONE = "05165489165156";

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private DealerMapper dealerMapper;

    private Dealer dealer;

    @BeforeEach
    public void init(){
        dealer = new Dealer();
        dealer.setName(DEFAULT_NAME);
        dealer.setCity(DEFAULT_CITY);
        dealer.setAddress(DEFAULT_ADDRESS);
        dealer.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void testSaveDealer(){
        dealerRepository.save(dealer);
        Optional<DealerDTO> result = dealerService.findOne(dealer.getId());
        DealerDTO verify = dealerMapper.toDto(dealer);
        assertThat(result).contains(verify);

    }
}
