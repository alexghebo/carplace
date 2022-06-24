package ca.verticalidigital.carplace.service.impl;

import ca.verticalidigital.carplace.domain.CarModel;
import ca.verticalidigital.carplace.domain.Category;
import ca.verticalidigital.carplace.repository.CarModelRepository;
import ca.verticalidigital.carplace.service.CarModelService;
import ca.verticalidigital.carplace.service.CategoryService;
import ca.verticalidigital.carplace.service.dto.CarModelDTO;
import ca.verticalidigital.carplace.service.dto.CategoryDTO;
import ca.verticalidigital.carplace.service.mapper.CarModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ca.verticalidigital.carplace.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarModel}.
 */
@Service
@Transactional
public class CarModelServiceImpl implements CarModelService {

    private final Logger log = LoggerFactory.getLogger(CarModelServiceImpl.class);

    private final CarModelRepository carModelRepository;

    private final CarModelMapper carModelMapper;

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CarModelServiceImpl(
        CarModelRepository carModelRepository,
        CarModelMapper carModelMapper,
        CategoryService categoryService,
        CategoryMapper categoryMapper){
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CarModelDTO save(CarModelDTO carModelDTO) {
        log.debug("Request to save CarModel : {}", carModelDTO);
        CarModel carModel = carModelMapper.toEntity(carModelDTO);
        Set<CategoryDTO> categoryDTOS = categoryService.save(carModelDTO.getCategories());
        carModel.setCategories(categoryMapper.toEntity(categoryDTOS));
        Optional<CarModel> unique = carModelRepository.findByMakeAndModelAndLaunchYear(carModel.getMake(), carModel.getModel(), carModel.getLaunchYear());
        if(unique.isPresent()){
            carModel.setId(unique.get().getId());
        }
        carModel = carModelRepository.save(carModel);
        return carModelMapper.toDto(carModel);
    }

    @Override
    public CarModelDTO update(CarModelDTO carModelDTO) {
        log.debug("Request to save CarModel : {}", carModelDTO);
        CarModel carModel = carModelMapper.toEntity(carModelDTO);
        carModel = carModelRepository.save(carModel);
        return carModelMapper.toDto(carModel);
    }

    @Override
    public Optional<CarModelDTO> partialUpdate(CarModelDTO carModelDTO) {
        log.debug("Request to partially update CarModel : {}", carModelDTO);

        return carModelRepository
            .findById(carModelDTO.getId())
            .map(existingCarModel -> {
                carModelMapper.partialUpdate(existingCarModel, carModelDTO);

                return existingCarModel;
            })
            .map(carModelRepository::save)
            .map(carModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarModels");
        return carModelRepository.findAll(pageable).map(carModelMapper::toDto);
    }

    public Page<CarModelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return carModelRepository.findAllWithEagerRelationships(pageable).map(carModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarModelDTO> findOne(Long id) {
        log.debug("Request to get CarModel : {}", id);
        return carModelRepository.findOneWithEagerRelationships(id).map(carModelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarModel : {}", id);
        carModelRepository.deleteById(id);
    }
}
