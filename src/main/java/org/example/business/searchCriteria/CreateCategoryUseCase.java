package org.example.business.searchCriteria;


import org.example.business.dto.categoryDTO.CreateCategoryRequest;
import org.example.business.dto.categoryDTO.CreateCategoryResponse;

public interface CreateCategoryUseCase{
    CreateCategoryResponse createCategory(CreateCategoryRequest request);
}
