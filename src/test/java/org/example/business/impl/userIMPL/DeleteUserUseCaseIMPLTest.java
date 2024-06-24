package org.example.business.impl.userIMPL;

import org.example.persistance.CommentRepository;
import org.example.persistance.LikeRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private DeleteUserUseCaseIMPL deleteUserUseCase;


    @Test
    void deleteUser_userExists_deletesUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        deleteUserUseCase.DeleteUser(userId);

        verify(likeRepository, times(1)).deleteByUser_UserID(userId);
        verify(commentRepository, times(1)).deleteByUser_UserID(userId);
        verify(postRepository, times(1)).deleteByUser_UserID(userId);
        verify(userRepository, times(1)).deleteByUserID(userId);
    }
    @Test
    void deleteUser_ShouldThrowException_WhenUserIDIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteUserUseCase.DeleteUser(null);
        });

        String expectedMessage = "User ID cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, never()).deleteByUserID(any(Long.class));
    }


    @Test
    void deleteUser_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteUserUseCase.DeleteUser(userId));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteByUserID(anyLong());
    }
}
