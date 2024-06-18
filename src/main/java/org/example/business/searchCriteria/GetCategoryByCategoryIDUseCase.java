package org.example.business.searchCriteria;

import org.example.domain.Category;
import org.example.domain.Post;

import java.util.Optional;

public interface GetCategoryByCategoryIDUseCase {
    Optional<Category> getCategory(long categoryID);
}
