package org.example.business.impl.userIMPL;

import org.example.persistance.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCaseIMPL deleteUserUseCase;

    @Test
    void deleteUser_userExists_deletesUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        deleteUserUseCase.DeleteUser(userId);

        verify(userRepository, times(1)).deleteByUserID(userId);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserIDIsNull() {
        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteUserUseCase.DeleteUser(null);
        });

        String expectedMessage = "User ID cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Verify that deleteByUserID() method was never called on userRepository
        verify(userRepository, never()).deleteByUserID(any(Long.class)); // Ensure to specify Long.class or appropriate type
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
