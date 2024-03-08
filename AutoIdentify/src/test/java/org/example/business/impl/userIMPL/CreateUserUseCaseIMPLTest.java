package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.CreateUserRequest;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseIMPLTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks // This will inject the mocked UserRepository into CreateUserUseCaseImpl
    private CreateUserUseCaseIMPL createUserUseCase;

//    @BeforeEach
//    public void setUp() {
//        // Initializes mocks annotated with @Mock, and injects them into the fields annotated with @InjectMocks
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void createUser_ShouldReturnUser_WhenRequestIsValid() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testUser")
                .password("testPassword")
                .description("testDescription")
                .build();

        UserEntity mockUserEntity = UserEntity.builder()
                .userID(1L)
                .username(request.getUsername())
                .password(request.getPassword())
                .description(request.getDescription())
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        // When
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Then
        assertEquals(1L, response.getUserID());
    }
}