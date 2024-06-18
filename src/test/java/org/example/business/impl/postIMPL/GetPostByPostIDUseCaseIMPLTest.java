
//    @Test
//    void getPost_PostDoesNotExist_ReturnsEmptyOptional() {
//        // Arrange
//        long postId = 1L;
//        when(postRepository.findPostById(postId)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Post> result = getPostByPostIDUseCase.getPost(postId);
//
//        // Assert
//        assertFalse(result.isPresent(), "The result should be empty as the post does not exist");
//    }
//
//    @Test
//    void getPost_RepositoryThrowsException_ThrowsException() {
//        // Arrange
//        long postId = 1L;
//        when(postRepository.findPostById(postId)).thenThrow(new RuntimeException("Database error"));
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> getPostByPostIDUseCase.getPost(postId), "Should throw an exception when the repository fails");
//    }
//}


package org.example.business.impl.postIMPL;

import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GetPostByPostIDUseCaseIMPLTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetPostByPostIDUseCaseIMPL getPostByPostIDUseCaseIMPL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getPost_withValidID_shouldReturnPost() {
        // Arrange
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

        // Act
        Optional<Post> result = getPostByPostIDUseCaseIMPL.getPost(postID);

        // Assert
        assertEquals(true, result.isPresent());
        Post post = result.get();
        assertEquals(postID, post.getPostID());
        assertEquals("Post description", post.getDescription());
    }

    @Test
    void getPost_withInvalidID_shouldReturnEmpty() {
        // Arrange
        long postID = 1L;
        when(postRepository.findPostById(postID)).thenReturn(Optional.empty());

        // Act
        Optional<Post> result = getPostByPostIDUseCaseIMPL.getPost(postID);

        // Assert
        assertFalse(result.isPresent());
    }


}

