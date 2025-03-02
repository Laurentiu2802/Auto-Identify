package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.UpdateUserRequest;
import org.example.business.userException.InvalidUserIdException;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseIMPLTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseIMPL updateUserUseCase;

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
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

        when(userRepository.findByUserID(userId)).thenReturn(Optional.of(existingUserEntity));

        updateUserUseCase.updateUser(request);

        verify(userRepository).findByUserID(userId);
        verify(userRepository).save(existingUserEntity);

        assertEquals("newUsername", existingUserEntity.getUsername());
        assertEquals("newPassword", existingUserEntity.getPassword());
        assertEquals("newDescription", existingUserEntity.getDescription());
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 2L;
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userID(userId)
                .username("newUsername")
                .password("newPassword")
                .description("newDescription")
                .build();

        when(userRepository.findByUserID(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidUserIdException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        assertEquals("400 BAD_REQUEST \"USER_ID_INVALID\"", exception.getMessage());
    }


}
