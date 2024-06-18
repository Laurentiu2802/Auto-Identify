package org.example.business.impl.likeIMPL;

import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.example.persistance.LikeRepository;
import org.example.persistance.entity.LikeEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnlikePostUseCaseIMPLTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private UnlikePostUseCaseImpl unlikePostUseCase;

    private LikePostRequest likePostRequest;
    private LikeEntity likeEntity;

    @BeforeEach
    void setUp() {
        likePostRequest = new LikePostRequest();
        likePostRequest.setUserID(1L);
        likePostRequest.setPostID(2L);

        likeEntity = new LikeEntity();
        likeEntity.setUser(new UserEntity());
        likeEntity.getUser().setUserID(1L);
        likeEntity.setPost(new PostEntity());
        likeEntity.getPost().setPostID(2L);
    }

    @Test
    void unlikePost_shouldReturnSuccessTrue_whenLikeExists() {
        // Arrange
        when(likeRepository.findByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(Optional.of(likeEntity));

        // Act
        LikePostResponse response = unlikePostUseCase.unlikePost(likePostRequest);

        // Assert
        assertTrue(response.isSuccess());
        verify(likeRepository, times(1)).delete(likeEntity);
    }

    @Test
    void unlikePost_shouldReturnSuccessFalse_whenLikeDoesNotExist() {
        // Arrange
        when(likeRepository.findByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(Optional.empty());

        // Act
        LikePostResponse response = unlikePostUseCase.unlikePost(likePostRequest);

        // Assert
        assertFalse(response.isSuccess());
        verify(likeRepository, never()).delete(any(LikeEntity.class));
    }
}
