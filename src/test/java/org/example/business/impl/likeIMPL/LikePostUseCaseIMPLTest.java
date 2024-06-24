package org.example.business.impl.likeIMPL;

import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.example.persistance.LikeRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.LikeEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikePostUseCaseIMPLTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikePostUseCaseImpl likePostUseCase;

    private LikePostRequest likePostRequest;
    private UserEntity user;
    private PostEntity post;

    @BeforeEach
    void setUp() {
        likePostRequest = new LikePostRequest();
        likePostRequest.setUserID(1L);
        likePostRequest.setPostID(2L);

        user = new UserEntity();
        user.setUserID(1L);

        post = new PostEntity();
        post.setPostID(2L);
    }

    @Test
    void likePost_shouldReturnSuccessTrue_whenPostIsNotYetLiked() {
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(false);
        when(userRepository.getById(anyLong())).thenReturn(user);
        when(postRepository.getById(anyLong())).thenReturn(post);

        LikePostResponse response = likePostUseCase.likePost(likePostRequest);

        assertTrue(response.isSuccess());
        verify(likeRepository, times(1)).save(any(LikeEntity.class));
    }

    @Test
    void likePost_shouldReturnSuccessFalse_whenPostIsAlreadyLiked() {
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(true);

        LikePostResponse response = likePostUseCase.likePost(likePostRequest);

        assertFalse(response.isSuccess());
        verify(likeRepository, never()).save(any(LikeEntity.class));
    }

    @Test
    void likePost_shouldReturnSuccessFalse_whenUserIsNull() {
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(false);
        when(userRepository.getById(anyLong())).thenReturn(null);
        when(postRepository.getById(anyLong())).thenReturn(post);

        LikePostResponse response = likePostUseCase.likePost(likePostRequest);

        assertFalse(response.isSuccess());
        verify(likeRepository, never()).save(any(LikeEntity.class));
    }

    @Test
    void likePost_shouldReturnSuccessFalse_whenPostIsNull() {
        when(likeRepository.existsByUserUserIDAndPostPostID(anyLong(), anyLong())).thenReturn(false);
        when(userRepository.getById(anyLong())).thenReturn(user);
        when(postRepository.getById(anyLong())).thenReturn(null);

        LikePostResponse response = likePostUseCase.likePost(likePostRequest);

        assertFalse(response.isSuccess());
        verify(likeRepository, never()).save(any(LikeEntity.class));
    }
}
