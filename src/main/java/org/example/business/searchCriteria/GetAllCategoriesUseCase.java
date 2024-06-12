package org.example.business.searchCriteria;

import org.example.business.dto.categoryDTO.GetAllCategoriesRequest;
import org.example.business.dto.categoryDTO.GetAllCategoriesResponse;

public interface GetAllCategoriesUseCase {
    GetAllCategoriesResponse getCategories(GetAllCategoriesRequest request);
}
