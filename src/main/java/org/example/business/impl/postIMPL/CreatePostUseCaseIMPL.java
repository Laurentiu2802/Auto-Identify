package org.example.business.impl.postIMPL;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.Post.CreatePostUseCase;
import org.example.business.dto.postDTO.CreatePostRequest;
import org.example.business.dto.postDTO.CreatePostResponse;
import org.example.persistance.*;
import org.example.persistance.entity.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePostUseCaseIMPL implements CreatePostUseCase {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;

    private static final String Error = "is not existent";

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request){
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());
        Optional<CategoryEntity> category = categoryRepository.findByCategoryID(request.getCategoryID());
        Optional<CarBrandEntity> carBrand = carBrandRepository.findByCarBrandID(request.getCarBrandID());
        Optional<CarModelEntity> carModel = carModelRepository.findById(request.getCarModelID());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User ID: " + request.getUserID() + Error);
        } else if (category.isEmpty()) {
            throw new IllegalArgumentException("Category ID: " + request.getCategoryID() + Error);
        } else if (carBrand.isEmpty()) {
            throw new IllegalArgumentException("Car Brand ID: " + request.getCarBrandID() + Error);
        } else if (carModel.isEmpty()) {
            throw new IllegalArgumentException("Car Model ID: " + request.getCarModelID() + Error);
        }

        PostEntity savedPost = saveNewPost(request);

        return CreatePostResponse.builder()
                .postID(savedPost.getPostID())
                .description(savedPost.getDescription())
                .build();
    }

    private PostEntity saveNewPost(CreatePostRequest request) {
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());
        Optional<CategoryEntity> category = categoryRepository.findByCategoryID(request.getCategoryID());
        Optional<CarBrandEntity> carBrand = carBrandRepository.findByCarBrandID(request.getCarBrandID());
        Optional<CarModelEntity> carModel = carModelRepository.findById(request.getCarModelID());
        if(user.isPresent()) {
            if(category.isPresent()) {
                if(carBrand.isPresent()) {
                    if(carModel.isPresent()) {
                        PostEntity newPost = PostEntity.builder()
                                .description(request.getDescription())
                                .user(user.get())
                                .category(category.orElse(null))
                                .carBrand(carBrand.orElse(null))
                                .carModel(carModel.orElse(null))
                                .build();
                        return postRepository.save(newPost);
                    } else {
                        throw new IllegalStateException("Car Model ID: " + request.getCarModelID() + Error);
                    }
                } else {
                    throw new IllegalStateException("Car Brand ID: " + request.getCarBrandID() + Error);
                }
            } else {
                throw new IllegalStateException("Category ID: " + request.getCategoryID() + Error);
            }
        } else {
            throw new IllegalStateException("User ID: " + request.getUserID() + Error);
        }
    }


}
