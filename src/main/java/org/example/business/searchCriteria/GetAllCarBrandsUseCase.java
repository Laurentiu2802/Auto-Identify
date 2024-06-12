package org.example.business.searchCriteria;

import org.example.business.dto.carBrandDTO.GetAllCarBrandsRequest;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsResponse;

public interface GetAllCarBrandsUseCase {
    GetAllCarBrandsResponse getBrands(GetAllCarBrandsRequest request);
}
