package org.example.business.impl.postIMPL;

import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPostByPostIDUseCaseIMPLTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetPostByPostIDUseCaseIMPL getPostByPostIDUseCaseIMPL;

    @Test
    void getPost_withValidID_shouldReturnPost() {
        long postID = 1L;

        PostEntity postEntity = new PostEntity();
        postEntity.setPostID(postID);
        postEntity.setDescription("Post description");

        UserEntity userEntity = new UserEntity();
        userEntity.setUserID(1L);
        postEntity.setUser(userEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryID(1L);
        postEntity.setCategory(categoryEntity);

        CarBrandEntity carBrandEntity = new CarBrandEntity();
        carBrandEntity.setCarBrandID(2L);
        postEntity.setCarBrand(carBrandEntity);

        CarModelEntity carModelEntity = new CarModelEntity();
        carModelEntity.setModelID(3L);
        carModelEntity.setCarBrand(carBrandEntity);
        postEntity.setCarModel(carModelEntity);

        when(postRepository.findPostById(postID)).thenReturn(Optional.of(postEntity));

        Optional<Post> result = getPostByPostIDUseCaseIMPL.getPost(postID);

        assertTrue(result.isPresent());
        Post post = result.get();
        assertEquals(postID, post.getPostID());
        assertEquals("Post description", post.getDescription());
    }

    @Test
    void getPost_withInvalidID_shouldReturnEmpty() {
        long postID = 1L;
        when(postRepository.findPostById(postID)).thenReturn(Optional.empty());

        Optional<Post> result = getPostByPostIDUseCaseIMPL.getPost(postID);

        assertFalse(result.isPresent());
    }

    @Test
    void getPost_PostDoesNotExist_ReturnsEmptyOptional() {
        long postId = 1L;
        when(postRepository.findPostById(postId)).thenReturn(Optional.empty());

        Optional<Post> result = getPostByPostIDUseCaseIMPL.getPost(postId);

        assertFalse(result.isPresent(), "The result should be empty as the post does not exist");
    }

    @Test
    void getPost_RepositoryThrowsException_ThrowsException() {
        long postId = 1L;
        when(postRepository.findPostById(postId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> getPostByPostIDUseCaseIMPL.getPost(postId), "Should throw an exception when the repository fails");
    }
}
