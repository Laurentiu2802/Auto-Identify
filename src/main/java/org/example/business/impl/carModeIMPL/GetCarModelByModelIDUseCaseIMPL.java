package org.example.business.impl.carModeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.searchCriteria.GetCarModelByModelIDUseCase;
import org.example.domain.CarModel;
import org.example.persistance.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCarModelByModelIDUseCaseIMPL implements GetCarModelByModelIDUseCase {
    private final CarModelRepository carModelRepository;

    @Override
    public Optional<CarModel> getModel(long modelID){
        return carModelRepository.findById(modelID).map(CarModelConverter::convert);
    }

}
