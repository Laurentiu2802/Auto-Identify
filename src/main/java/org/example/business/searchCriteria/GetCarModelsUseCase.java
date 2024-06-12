package org.example.business.searchCriteria;

import org.example.business.dto.carModelDTO.GetCarModelByCarBrand;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrandResponse;

public interface GetCarModelsUseCase {
    GetCarModelByCarBrandResponse getCarModels(GetCarModelByCarBrand request);
}
