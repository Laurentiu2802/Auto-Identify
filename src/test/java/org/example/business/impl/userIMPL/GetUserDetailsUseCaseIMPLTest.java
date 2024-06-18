package org.example.business.impl.userIMPL;  // This is the package declaration.

import org.example.domain.User;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.example.persistance.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetUserDetailsUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserDetailsUseCaseIMPL getUserDetailsUseCaseIMPL;

    @Test
    public void testGetUserFound() {
        // Arrange
        long userId = 1L;
        UserRoleEntity role = new UserRoleEntity(); // Assuming a simple entity
        UserEntity userEntity = UserEntity.builder()
                .userID(userId)
                .username("testUser")
                .password("securePassword")
                .description("Test Description")
                .role(role)
                .build();

        User expectedUser = UserConverter.convert(userEntity);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        Optional<User> result = getUserDetailsUseCaseIMPL.getUser(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser.getUserID(), result.get().getUserID());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testGetUserNotFound() {
        // Arrange
        long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUserDetailsUseCaseIMPL.getUser(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
    }
}
