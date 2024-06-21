package org.example.business.impl.postIMPL;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.business.dto.postDTO.CreatePostRequest;
import org.example.business.dto.postDTO.CreatePostResponse;
import org.example.persistance.entity.*;
import org.example.persistance.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CarBrandRepository carBrandRepository;
    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private CreatePostUseCaseIMPL createPostUseCase;

    @Test
    void createPost_AllEntitiesExist_ShouldCreatePost() {
        CreatePostRequest request = new CreatePostRequest("Post description", 1L, 2L, 3L,4L );
        when(userRepository.findByUserID(1L)).thenReturn(Optional.of(new UserEntity()));
        when(categoryRepository.findByCategoryID(2L)).thenReturn(Optional.of(new CategoryEntity()));
        when(carBrandRepository.findByCarBrandID(3L)).thenReturn(Optional.of(new CarBrandEntity()));
        when(carModelRepository.findById(4L)).thenReturn(Optional.of(new CarModelEntity()));
        when(postRepository.save(any(PostEntity.class))).thenReturn(new PostEntity());

        CreatePostResponse response = createPostUseCase.createPost(request);

        assertNotNull(response);
        verify(postRepository).save(any(PostEntity.class));
    }

    @Test
    void createPost_UserDoesNotExist_ShouldThrowException() {
        // Arrange
        CreatePostRequest request = new CreatePostRequest();
        request.setUserID(1L); // Assuming the user ID is a Long and set to 1 for this test case

        // Mock the behavior of userRepository to return an empty Optional when findByUserID is called
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createPostUseCase.createPost(request);
        });

        // Verify that the error message is correct
        assertEquals("User ID: 1 is not existent", exception.getMessage());
    }
}
