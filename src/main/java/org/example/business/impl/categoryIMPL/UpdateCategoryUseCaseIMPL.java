package org.example.business.impl.categoryIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.categoryDTO.UpdateCategoryRequest;
import org.example.business.searchCriteria.UpdateCategoryUseCase;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCategoryUseCaseIMPL implements UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public void updateCategory(UpdateCategoryRequest request){
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByCategoryID(request.getCategoryID());

        if(categoryEntityOptional.isPresent()) {
            CategoryEntity category = categoryEntityOptional.get();
            updateFields(request, category);
            categoryRepository.save(category);
        } else {
            throw new NoSuchElementException("Category not found with ID: " + request.getCategoryID());
        }
    }


    private void updateFields(UpdateCategoryRequest request, CategoryEntity category){
        category.setCategoryName(request.getCategoryName());
    }
}
