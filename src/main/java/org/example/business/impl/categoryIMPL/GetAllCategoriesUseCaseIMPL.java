package org.example.business.impl.categoryIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.categoryDTO.GetAllCategoriesRequest;
import org.example.business.dto.categoryDTO.GetAllCategoriesResponse;
import org.example.business.searchCriteria.GetAllCategoriesUseCase;
import org.example.domain.Category;
import org.example.domain.Post;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCategoriesUseCaseIMPL implements GetAllCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public GetAllCategoriesResponse getCategories(GetAllCategoriesRequest request){

        List<CategoryEntity> categoriesResults = categoryRepository.findAll();

        final GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        List<Category> categories = categoriesResults
                .stream()
                .map(CategoryConverter::convert)
                .toList();
        response.setCategories(categories);
        return response;
    }
}
