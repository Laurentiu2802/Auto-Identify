package org.example.business.searchCriteria;

import org.example.business.dto.carBrandDTO.CreateCarBrandRequest;
import org.example.business.dto.carBrandDTO.CreateCarBrandResponse;
import org.example.business.dto.carModelDTO.CreateCarModelRequest;
import org.example.business.dto.carModelDTO.CreateCarModelResponse;

public interface CreateCarBrandUseCase {
    CreateCarBrandResponse createCarBrand(CreateCarBrandRequest request);
}
