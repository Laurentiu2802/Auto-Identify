package org.example.business.impl.userIMPL;

import org.example.Configuration.security.token.AccessTokenEncoder;
import org.example.business.dto.userDTO.LogInRequest;
import org.example.business.dto.userDTO.LogInResponse;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.RoleEnum;
import org.example.persistance.entity.UserEntity;
import org.example.persistance.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogInUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LogInUseCaseImpl logInUseCase;

    private final String username = "user";
    private final String password = "pass";
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        // Create a user entity to use in tests
        UserRoleEntity role = new UserRoleEntity(); // Simulate the role setup
        role.setRole(RoleEnum.USER); // Sample role

        userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setUserID(1L);
        userEntity.setRole(role);
    }

    @Test
    void testLoginUserSuccessful() {
        // Arrange
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("token");

        LogInRequest request = new LogInRequest();
        request.setUsername(username);
        request.setPassword(password);

        // Act
        LogInResponse response = logInUseCase.loginUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("token", response.getAccessToken());
    }

    @Test
    void testLoginUserFailedPasswordIncorrect() {
        // Arrange
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        LogInRequest request = new LogInRequest();
        request.setUsername(username);
        request.setPassword("wrongPassword");

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInUseCase.loginUser(request));
    }

    @Test
    void testLoginUserUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        LogInRequest request = new LogInRequest();
        request.setUsername("unknown");
        request.setPassword(password);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInUseCase.loginUser(request));
    }
}
