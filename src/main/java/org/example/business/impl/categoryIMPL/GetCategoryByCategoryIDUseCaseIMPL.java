package org.example.business.impl.categoryIMPL;

import lombok.AllArgsConstructor;
import org.example.business.searchCriteria.GetCategoryByCategoryIDUseCase;
import org.example.domain.Category;
import org.example.persistance.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCategoryByCategoryIDUseCaseIMPL implements GetCategoryByCategoryIDUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategory(long categoryID){
        return categoryRepository.findByCategoryID(categoryID).map(CategoryConverter::convert);
    }
}
