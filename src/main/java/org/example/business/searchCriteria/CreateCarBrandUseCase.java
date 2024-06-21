package org.example.business.searchCriteria;

import org.example.business.dto.carBrandDTO.CreateCarBrandRequest;
import org.example.business.dto.carBrandDTO.CreateCarBrandResponse;


public interface CreateCarBrandUseCase {
    CreateCarBrandResponse createCarBrand(CreateCarBrandRequest request);
}
