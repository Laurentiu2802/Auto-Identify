package org.example.business.impl.carModeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsResponse;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrand;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrandResponse;
import org.example.business.searchCriteria.GetAllCarBrandsUseCase;
import org.example.business.searchCriteria.GetCarModelsUseCase;
import org.example.domain.CarModel;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarModelEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCarModelUseCaseIMPL implements GetCarModelsUseCase {
    private final CarModelRepository carModelRepository;

    @Override
    public GetCarModelByCarBrandResponse getCarModels(GetCarModelByCarBrand request){
        List<CarModelEntity> carModelResults = carModelRepository.findAllByCarBrandId(request.getBrandID());

        final GetCarModelByCarBrandResponse response = new GetCarModelByCarBrandResponse();
        List<CarModel> carModels = carModelResults
                .stream()
                .map(CarModelConverter::convert)
                .toList();
        response.setCarModels(carModels);
        return response;
    }
}
