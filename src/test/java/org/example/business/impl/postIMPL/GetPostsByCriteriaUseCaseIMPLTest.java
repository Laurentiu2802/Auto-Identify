package org.example.business.impl.postIMPL;

import org.example.business.dto.postDTO.GetPostsByCriteriaRequest;
import org.example.business.dto.postDTO.GetPostsByCriteriaResponse;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPostsByCriteriaUseCaseIMPLTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetPostsByCriteriaUseCaseIMPL getPostsByCriteriaUseCaseIMPL;

    @Test
    void getPosts_withValidCriteria_shouldReturnMatchingPosts() {

        GetPostsByCriteriaRequest request = new GetPostsByCriteriaRequest();
        request.setCategoryID(1L);
        request.setCarBrandID(2L);
        request.setCarModelID(3L);

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

        when(postRepository.findPostsByCriteria(1L, 2L, 3L)).thenReturn(postEntities);
        when(postRepository.countLikesForPost(1L)).thenReturn(10L);
        when(postRepository.countLikesForPost(2L)).thenReturn(20L);

        GetPostsByCriteriaResponse response = getPostsByCriteriaUseCaseIMPL.getPosts(request);

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
    void getPosts_withNoMatchingCriteria_shouldReturnEmptyList() {
        GetPostsByCriteriaRequest request = new GetPostsByCriteriaRequest();
        request.setCategoryID(1L);
        request.setCarBrandID(2L);
        request.setCarModelID(3L);

        when(postRepository.findPostsByCriteria(1L, 2L, 3L)).thenReturn(Collections.emptyList());

        GetPostsByCriteriaResponse response = getPostsByCriteriaUseCaseIMPL.getPosts(request);

        assertEquals(0, response.getPosts().size());
    }

    @Test
    void getPosts_withNullCriteria_shouldHandleGracefully() {
        GetPostsByCriteriaRequest request = new GetPostsByCriteriaRequest();
        request.setCategoryID(null);
        request.setCarBrandID(null);
        request.setCarModelID(null);

        when(postRepository.findPostsByCriteria(null, null, null)).thenReturn(Collections.emptyList());

        GetPostsByCriteriaResponse response = getPostsByCriteriaUseCaseIMPL.getPosts(request);

        assertEquals(0, response.getPosts().size());
    }
}
