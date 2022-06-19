package ca.verticalidigital.carplace.web.rest;

import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.service.DealerServiceImpl;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DealerResource {

    private static final String ENTITY_NAME = "dealer";

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList(
            "id",
            "name",
            "city",
            "address",
            "phone"
        )
    );

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealerRepository dealerRepository;

    private final DealerServiceImpl dealerService;

    public DealerResource(
        DealerRepository dealerRepository,
        DealerServiceImpl dealerService
    ){
        this.dealerRepository = dealerRepository;
        this.dealerService = dealerService;
    }

    @GetMapping("/dealer/{id}")
    public ResponseEntity<DealerDTO> getDealer(@PathVariable Long id){
        Optional<DealerDTO> dealerDTO = dealerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealerDTO);
    }


}
