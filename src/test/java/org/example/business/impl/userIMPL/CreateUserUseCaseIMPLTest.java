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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_ShouldReturnUser_WhenRequestIsValid() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testUser")
                .password("testPassword")
                .description("testDescription")
                .build();

        UserRoleEntity mockUserRole = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.ADMIN)
                .build();

        UserEntity mockUserEntity = UserEntity.builder()
                .userID(1L)
                .username(request.getUsername())
                .password("encodedPassword")
                .description(request.getDescription())
                .role(mockUserRole)
                .build();

        when(roleRepository.findByRole(RoleEnum.ADMIN)).thenReturn(mockUserRole);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        // When
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getUserID());

        // Verify interactions
        verify(roleRepository, times(1)).findByRole(RoleEnum.ADMIN);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
