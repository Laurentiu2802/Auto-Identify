package org.example.business.searchCriteria;

import org.example.business.dto.carModelDTO.CreateCarModelRequest;
import org.example.business.dto.carModelDTO.CreateCarModelResponse;

public interface CreateCarModelUseCase {
    CreateCarModelResponse createCarModel(CreateCarModelRequest request);
}
