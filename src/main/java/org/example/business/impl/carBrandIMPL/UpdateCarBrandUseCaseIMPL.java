package org.example.business.impl.carBrandIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.UpdateCarBrandRequest;
import org.example.business.searchCriteria.UpdateCarBrandUseCase;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCarBrandUseCaseIMPL implements UpdateCarBrandUseCase {
    private final CarBrandRepository carBrandRepository;

    @Override
    public void updateCarBrand(UpdateCarBrandRequest request){
        Optional<CarBrandEntity> carBrandEntityOptional = carBrandRepository.findByCarBrandID(request.getBrandID());

        if(carBrandEntityOptional.isPresent()) {
            CarBrandEntity carBrand = carBrandEntityOptional.get();
            updateFields(request, carBrand);
            carBrandRepository.save(carBrand);
        } else {
            throw new NoSuchElementException("Car brand not found with ID: " + request.getBrandID());
        }
    }

    private void updateFields(UpdateCarBrandRequest request, CarBrandEntity carBrand){
        carBrand.setBrandName(request.getBrandName());
    }
}
