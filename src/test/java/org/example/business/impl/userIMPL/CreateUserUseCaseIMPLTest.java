package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.CreateUserRequest;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.persistance.RoleRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.RoleEnum;
import org.example.persistance.entity.UserEntity;
import org.example.persistance.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseIMPLTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseIMPL createUserUseCase;

    private CreateUserRequest request;
    private UserRoleEntity mockUserRole;
    private UserEntity mockUserEntity;

    @BeforeEach
    public void setUp() {
        request = CreateUserRequest.builder()
                .username("testUser")
                .password("testPassword")
                .description("testDescription")
                .build();

        mockUserRole = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.USER)
                .build();

        mockUserEntity = UserEntity.builder()
                .userID(1L)
                .username(request.getUsername())
                .password("encodedPassword")
                .description(request.getDescription())
                .role(mockUserRole)
                .build();
    }

    @Test
    public void createUser_ShouldReturnUser_WhenRequestIsValid() {
        // Mocking the role repository to return the correct role entity
        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(mockUserRole);

        // Mocking the password encoder
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Mocking the user repository save method
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assertions
        assertNotNull(response);
        assertEquals(1L, response.getUserID());

        // Verify interactions
        verify(roleRepository, times(1)).findByRole(RoleEnum.USER);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenRoleNotFound() {
        // Mock setup for roleRepository.findByRole(RoleEnum.USER)
        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(null);

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.createUser(request);
        });

        assertEquals("Role not found: USER", exception.getMessage());

        // Verify interactions
        verify(roleRepository, times(1)).findByRole(RoleEnum.USER);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(passwordEncoder, never()).encode(anyString());
    }


    @Test
    public void createUser_ShouldThrowException_WhenPasswordEncodingFails() {
        // Mocking the role repository to return the correct role entity
        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(mockUserRole);

        // Mocking the password encoder to throw an exception
        when(passwordEncoder.encode(request.getPassword())).thenThrow(new RuntimeException("Password encoding failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createUserUseCase.createUser(request);
        });

        assertEquals("Password encoding failed", exception.getMessage());

        verify(roleRepository, times(1)).findByRole(RoleEnum.USER);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void createUser_ShouldThrowException_WhenUserSaveFails() {
        // Mocking the role repository to return the correct role entity
        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(mockUserRole);

        // Mocking the password encoder
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Mocking the user repository save method to throw an exception
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("User save failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createUserUseCase.createUser(request);
        });

        assertEquals("User save failed", exception.getMessage());

        verify(roleRepository, times(1)).findByRole(RoleEnum.USER);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenUsernameIsNull() {
        // Arrange
        CreateUserRequest request = CreateUserRequest.builder()
                .username(null)
                .password("password")
                .description("description")
                .build();

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.createUser(request);
        });

        String expectedMessage = "Invalid request: username is null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
