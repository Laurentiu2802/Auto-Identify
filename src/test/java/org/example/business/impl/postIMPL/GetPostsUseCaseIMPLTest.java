package org.example.business.impl.postIMPL;

import org.example.business.dto.postDTO.GetAllPostsRequest;
import org.example.business.dto.postDTO.GetAllPostsResponse;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPostsUseCaseIMPLTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetPostsUseCaseIMPL getPostsUseCaseIMPL;

    @Test
    void getAllPosts_shouldReturnAllPosts() {
        // Arrange
        GetAllPostsRequest request = new GetAllPostsRequest();

        // Create PostEntity 1
        PostEntity postEntity1 = new PostEntity();
        postEntity1.setPostID(1L);
        postEntity1.setDescription("Post 1 description");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserID(1L);
        postEntity1.setUser(userEntity1);

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setCategoryID(1L);
        postEntity1.setCategory(categoryEntity1);

        CarBrandEntity carBrandEntity1 = new CarBrandEntity();
        carBrandEntity1.setCarBrandID(2L);
        postEntity1.setCarBrand(carBrandEntity1);

        CarModelEntity carModelEntity1 = new CarModelEntity();
        carModelEntity1.setModelID(3L);
        carModelEntity1.setCarBrand(carBrandEntity1);
        postEntity1.setCarModel(carModelEntity1);

        // Create PostEntity 2
        PostEntity postEntity2 = new PostEntity();
        postEntity2.setPostID(2L);
        postEntity2.setDescription("Post 2 description");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserID(2L);
        postEntity2.setUser(userEntity2);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setCategoryID(1L);
        postEntity2.setCategory(categoryEntity2);

        CarBrandEntity carBrandEntity2 = new CarBrandEntity();
        carBrandEntity2.setCarBrandID(2L);
        postEntity2.setCarBrand(carBrandEntity2);

        CarModelEntity carModelEntity2 = new CarModelEntity();
        carModelEntity2.setModelID(3L);
        carModelEntity2.setCarBrand(carBrandEntity2);
        postEntity2.setCarModel(carModelEntity2);

        List<PostEntity> postEntities = Arrays.asList(postEntity1, postEntity2);

        when(postRepository.findAll(Sort.by("postID").descending())).thenReturn(postEntities);
        when(postRepository.countLikesForPost(1L)).thenReturn(10L);
        when(postRepository.countLikesForPost(2L)).thenReturn(20L);

        // Act
        GetAllPostsResponse response = getPostsUseCaseIMPL.getAllPosts(request);

        // Assert
        assertEquals(2, response.getPosts().size());

        Post post1 = response.getPosts().get(0);
        assertEquals(1L, post1.getPostID());
        assertEquals("Post 1 description", post1.getDescription());
        assertEquals(10L, post1.getLikes());

        Post post2 = response.getPosts().get(1);
        assertEquals(2L, post2.getPostID());
        assertEquals("Post 2 description", post2.getDescription());
        assertEquals(20L, post2.getLikes());
    }

    @Test
    void getAllPosts_withNoPosts_shouldReturnEmptyList() {
        // Arrange
        GetAllPostsRequest request = new GetAllPostsRequest();

        when(postRepository.findAll(Sort.by("postID").descending())).thenReturn(Collections.emptyList());

        // Actx1
        GetAllPostsResponse response = getPostsUseCaseIMPL.getAllPosts(request);

        // Assert
        assertEquals(0, response.getPosts().size());
    }
}
