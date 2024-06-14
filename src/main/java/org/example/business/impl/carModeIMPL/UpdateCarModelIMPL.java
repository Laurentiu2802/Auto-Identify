package org.example.business.impl.carModeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carModelDTO.UpdateCarModelRequest;
import org.example.business.searchCriteria.UpdateCarModelUseCase;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarModelEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCarModelIMPL implements UpdateCarModelUseCase {
    private final CarModelRepository carModelRepository;

    @Override
    public void updateCarModel(UpdateCarModelRequest request){
        Optional<CarModelEntity> carModelEntityOptional = carModelRepository.findById(request.getModelID());

        if(carModelEntityOptional.isPresent()) {
            CarModelEntity carModel = carModelEntityOptional.get();
            updateFields(request, carModel);
            carModelRepository.save(carModel);
        } else {
            throw new NoSuchElementException("Car model not found with ID: " + request.getModelID());

        }
    }

    private void updateFields(UpdateCarModelRequest request, CarModelEntity  carModel){
        carModel.setModelName(request.getModelName());
    }
}
