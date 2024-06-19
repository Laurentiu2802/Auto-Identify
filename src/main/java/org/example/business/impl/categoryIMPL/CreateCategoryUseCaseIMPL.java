package org.example.business.impl.categoryIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.categoryDTO.CreateCategoryRequest;
import org.example.business.dto.categoryDTO.CreateCategoryResponse;
import org.example.business.searchCriteria.CreateCategoryUseCase;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCategoryUseCaseIMPL implements CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest request){
        CategoryEntity savedCategory = saveNewCategory(request);
        if (savedCategory == null) {
            return null;
        }
        return CreateCategoryResponse.builder()
                .categoryID(savedCategory.getCategoryID())
                .build();
    }

    public CategoryEntity saveNewCategory(CreateCategoryRequest request){
        if (request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
            return null;
        }
        CategoryEntity newCategory = CategoryEntity.builder()
                .categoryName(request.getCategoryName())
                .build();
        return categoryRepository.save(newCategory);
    }
}