package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.UpdateUserRequest;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseIMPLTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseIMPL updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        // Given
        Long userId = 1L;
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userID(userId)
                .username("newUsername")
                .password("newPassword")
                .description("newDescription")
                .build();

        UserEntity existingUserEntity = UserEntity.builder()
                .userID(userId)
                .username("oldUsername")
                .password("oldPassword")
                .description("oldDescription")
                .build();

        when(userRepository.findByID(userId)).thenReturn(Optional.of(existingUserEntity));

        // When
        updateUserUseCase.updateUser(request);

        // Then
        verify(userRepository).findByID(userId);
        verify(userRepository).save(existingUserEntity); // Verify that the repository's save method is called

        // Verify the UserEntity object has been updated
        assertEquals("newUsername", existingUserEntity.getUsername());
        assertEquals("newPassword", existingUserEntity.getPassword());
        assertEquals("newDescription", existingUserEntity.getDescription());
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        // Given
        Long userId = 2L;
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userID(userId)
                .username("newUsername")
                .password("newPassword")
                .description("newDescription")
                .build();

        when(userRepository.findByID(userId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }

}