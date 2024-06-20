package org.example.business.impl.carModeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carModelDTO.CreateCarModelRequest;
import org.example.business.dto.carModelDTO.CreateCarModelResponse;
import org.example.business.searchCriteria.CreateCarModelUseCase;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.example.persistance.entity.CarModelEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCarModelUseCaseIMPL implements CreateCarModelUseCase {
    private final CarModelRepository carModelRepository;
    private final CarBrandRepository carBrandRepository;

    @Override
    public CreateCarModelResponse createCarModel(CreateCarModelRequest request){
        CarModelEntity savedCarModel = saveNewCarModel(request);

        return CreateCarModelResponse.builder()
                .modelID(savedCarModel.getModelID())
                .build();
    }

    public CarModelEntity saveNewCarModel(CreateCarModelRequest request){
        Optional<CarBrandEntity> carBrand = carBrandRepository.findByCarBrandID(request.getBrandID());

        CarModelEntity newCarModel = CarModelEntity.builder()
                .carBrand(carBrand.orElse(null))
                .modelName(request.getModelName())
                .build();
        return carModelRepository.save(newCarModel);
    }
}
