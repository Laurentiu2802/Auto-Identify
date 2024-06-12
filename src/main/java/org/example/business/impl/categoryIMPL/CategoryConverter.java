package org.example.business.impl.categoryIMPL;

import org.example.domain.Category;
import org.example.persistance.entity.CategoryEntity;

public class CategoryConverter {
    private CategoryConverter(){

    }

    public static Category convert(CategoryEntity category){
        if(category == null){
            return null;
        }
        return Category.builder()
                .categoryID(category.getCategoryID())
                .categoryName(category.getCategoryName())
                .build();
    }

}
