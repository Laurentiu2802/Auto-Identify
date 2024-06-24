package org.example.business.impl.likeIMPL;

import org.example.business.dto.likeDTO.IsLikedResponse;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.persistance.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsLikedUseCaseIMPLTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private IsLikedUseCaseImpl isLikedUseCase;

    @Test
    void isLiked_shouldReturnTrue_whenPostIsLikedByUser() {
        LikePostRequest request = new LikePostRequest();
        request.setUserID(1L);
        request.setPostID(2L);
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(true);

        IsLikedResponse response = isLikedUseCase.isLiked(request);

        assertTrue(response.isLiked());
    }

    @Test
    void isLiked_shouldReturnFalse_whenPostIsNotLikedByUser() {
        LikePostRequest request = new LikePostRequest();
        request.setUserID(1L);
        request.setPostID(2L);
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(false);

        IsLikedResponse response = isLikedUseCase.isLiked(request);

        assertFalse(response.isLiked());
    }

    @Test
    void isLiked_shouldReturnFalse_whenUserOrPostIsNull() {
        LikePostRequest nullUserRequest = new LikePostRequest();
        nullUserRequest.setUserID(null);
        nullUserRequest.setPostID(2L);
        when(likeRepository.existsByUserUserIDAndPostPostID(null, 2L)).thenReturn(false);

        LikePostRequest nullPostRequest = new LikePostRequest();
        nullPostRequest.setUserID(1L);
        nullPostRequest.setPostID(null);
        when(likeRepository.existsByUserUserIDAndPostPostID(1L, null)).thenReturn(false);

        assertFalse(isLikedUseCase.isLiked(nullUserRequest).isLiked());
        assertFalse(isLikedUseCase.isLiked(nullPostRequest).isLiked());
    }
}
