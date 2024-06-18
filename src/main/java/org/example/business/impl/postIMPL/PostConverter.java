package org.example.business.impl.postIMPL;

import org.example.business.impl.carBrandIMPL.CarBrandConverter;
import org.example.business.impl.carModeIMPL.CarModelConverter;
import org.example.business.impl.categoryIMPL.CategoryConverter;
import org.example.business.impl.userIMPL.UserConverter;
import org.example.domain.*;
import org.example.persistance.entity.PostEntity;

public class PostConverter {

    private PostConverter(){

    }

    public static Post convert(PostEntity post){
        User user = UserConverter.convert(post.getUser());
        Category category = CategoryConverter.convert(post.getCategory());
        CarBrand carBrand = CarBrandConverter.convert(post.getCarBrand());
        CarModel carModel = CarModelConverter.convert(post.getCarModel());



    return Post.builder()
            .postID(post.getPostID())
            .description(post.getDescription())
            .user(user)
            .category(category)
            .carBrand(carBrand)
            .carModel(carModel)
            .build();
    }
}
