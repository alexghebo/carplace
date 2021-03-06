package ca.verticalidigital.carplace.web.rest;

import ca.verticalidigital.carplace.config.Constants;
import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.security.AuthoritiesConstants;
import ca.verticalidigital.carplace.service.dto.AdminUserDTO;
import ca.verticalidigital.carplace.service.dto.VehicleListingDTO;
import ca.verticalidigital.carplace.service.impl.DealerServiceImpl;
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

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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

    /**
     * {@code POST  /dealer} : Create a new dealer.
     *
     * @param dealerDTO the dealerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealerDTO, or with status {@code 400 (Bad Request)} if the dealerDTO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dealer")
    public ResponseEntity<DealerDTO> createDealer(@RequestBody DealerDTO dealerDTO) throws URISyntaxException{
        log.debug("REST request to save Dealer : {}", dealerDTO);
        if(dealerDTO.getId() != null){
            throw new BadRequestAlertException("A new dealer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealerDTO result = dealerService.save(dealerDTO);
        return ResponseEntity
            .created(new URI("/api/dealer/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dealer/:id} : Updates an existing dealer.
     *
     * @param id the id of the dealerDTO to save.
     * @param dealerDTO the dealerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealerDTO,
     * or with status {@code 400 (Bad Request)} if the dealerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PutMapping("/dealer/{id}")
    public ResponseEntity<DealerDTO> updateDealer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealerDTO dealerDTO
    ) throws URISyntaxException{
        log.debug("REST request to update Dealer : {}, {}", id, dealerDTO);
        if(dealerDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!Objects.equals(id, dealerDTO.getId())){
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if(!dealerRepository.existsById(id)){
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealerDTO result = dealerService.update(dealerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dealerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dealer/:id} : Partial updates given fields of an existing dealer, field will ignore if it is null
     *
     * @param id the id of the dealerDTO to save.
     * @param dealerDTO the dealerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealerDTO,
     * or with status {@code 400 (Bad Request)} if the dealerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dealerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PatchMapping(value = "/dealer/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealerDTO> partialUpdateDealer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealerDTO dealerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dealer partially : {}, {}", id, dealerDTO);
        if (dealerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealerDTO> result = dealerService.partialUpdate(dealerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dealerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dealer/:id} : get the "id" dealer.
     *
     * @param id the id of the DealerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the DealerDTO, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/dealer/{id}")
    public ResponseEntity<DealerDTO> getDealer(@PathVariable Long id){
        Optional<DealerDTO> dealerDTO = dealerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealerDTO);
    }

    /**
     * {@code GET /dealer} : get all dealers with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */

    @GetMapping("/dealer")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<DealerDTO>> getAllDealers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all Dealers");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<DealerDTO> page = dealerService.getAllManagedDealers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code DELETE /dealer/:id} : delete the "id" Dealer.
     *
     * @param id the id of the dealer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dealer/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete dealer with id: {}", id);
        dealerService.deleteDealer(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "dealer.deleted", id.toString())).build();
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }


}
