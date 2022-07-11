package ca.verticalidigital.carplace.web.rest;

import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.security.AuthoritiesConstants;
import ca.verticalidigital.carplace.service.DealerService;
import ca.verticalidigital.carplace.service.dto.DealerDTO;
import ca.verticalidigital.carplace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@RestController
@RequestMapping("/api/admin")
public class DealerResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = List.of("id", "name", "city", "address", "phone");

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealerService dealerService;

    private final DealerRepository dealerRepository;

    public DealerResource(DealerService dealerService, DealerRepository dealerRepository) {
        this.dealerService = dealerService;
        this.dealerRepository = dealerRepository;
    }

    @PostMapping("/dealer")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<DealerDTO> createDealer(@Valid @RequestBody DealerDTO dealerDTO) throws URISyntaxException {
        log.debug("REST request to save Dealer : {}", dealerDTO);
        if (dealerDTO.getId() != null) {
            throw new BadRequestAlertException("A new dealer cannot already have an ID", "dealerManagement", "idexists");
        }

        DealerDTO newDealer = dealerService.save(dealerDTO);

        return ResponseEntity
            .created(new URI("/api/admin/dealer/" + newDealer.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "dealerManagement.created", newDealer.getId().toString()))
            .body(newDealer);
    }

    @GetMapping("/dealer")
    public ResponseEntity<List<DealerDTO>> getAllDealers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all Dealers");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<DealerDTO> page = dealerService.getAllManagedDealers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/dealer{id}")
    public ResponseEntity<DealerDTO> getDealer(@PathVariable Long id) {
        log.debug("REST request to get Dealer : {}", id);
        Optional<DealerDTO> dealerDTO = dealerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealerDTO);
    }

    @DeleteMapping("/dealer{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        log.debug("REST request to delete Dealer: {}", id);
        dealerService.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "dealerManagement.deleted", id.toString())).build();
    }

    @PutMapping("/dealer/{id}")
    public ResponseEntity<DealerDTO> updateDealer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealerDTO dealerDTO
    ) {
        log.debug("REST request to update Dealer : {}, {}", id, dealerDTO);
        if(dealerDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id", "dealerManagement", "id_null");
        }
        if(!Objects.equals(id, dealerDTO.getId())){
            throw new BadRequestAlertException("Invalid ID", "dealerManagement", "id_invalid");
        }
        if(!dealerRepository.existsById(id)){
            throw new BadRequestAlertException("Entity not found", "dealerManagement", "id_not_found");
        }

        DealerDTO result = dealerService.update(dealerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, "dealerManagement", dealerDTO.getId().toString()))
            .body(result);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

}
