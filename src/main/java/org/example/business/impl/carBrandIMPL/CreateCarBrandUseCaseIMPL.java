package org.example.business.impl.carBrandIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.CreateCarBrandRequest;
import org.example.business.dto.carBrandDTO.CreateCarBrandResponse;
import org.example.business.searchCriteria.CreateCarBrandUseCase;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCarBrandUseCaseIMPL implements CreateCarBrandUseCase {
    private final CarBrandRepository carBrandRepository;

    @Override
    public CreateCarBrandResponse createCarBrand(CreateCarBrandRequest request) {
        if (request.getBrandName() == null || request.getBrandName().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty");
        }
        CarBrandEntity savedCarBrand = saveNewCarBrand(request);
        return CreateCarBrandResponse.builder()
                .brandID(savedCarBrand.getCarBrandID())
                .build();
    }

    public CarBrandEntity saveNewCarBrand(CreateCarBrandRequest request){
        CarBrandEntity newCarBrand = CarBrandEntity.builder()
                .brandName(request.getBrandName())
                .build();
        return carBrandRepository.save(newCarBrand);
    }
}
