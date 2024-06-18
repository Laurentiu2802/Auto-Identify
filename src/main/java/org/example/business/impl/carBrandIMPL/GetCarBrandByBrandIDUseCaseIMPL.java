package org.example.business.impl.carBrandIMPL;

import lombok.AllArgsConstructor;
import org.example.business.searchCriteria.GetCarBrandByBrandIDUseCase;
import org.example.domain.CarBrand;
import org.example.persistance.CarBrandRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCarBrandByBrandIDUseCaseIMPL implements GetCarBrandByBrandIDUseCase {
    private final CarBrandRepository carBrandRepository;

    @Override
    public Optional<CarBrand> getBrand(long brandID){
        return carBrandRepository.findByCarBrandID(brandID).map(CarBrandConverter::convert);
    }
}
