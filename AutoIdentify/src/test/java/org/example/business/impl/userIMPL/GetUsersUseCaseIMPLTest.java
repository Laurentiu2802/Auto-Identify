package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.GetUsersRequest;
import org.example.business.dto.userDTO.GetUsersResponse;
import org.example.domain.User;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetUsersUseCaseIMPLTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersUseCaseIMPL getUsersUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_ShouldReturnListOfUsers_WhenRequestIsValid() {
        // Given
        GetUsersRequest request = new GetUsersRequest(); // Assuming this is needed
        UserEntity userEntity = UserEntity.builder()
                .userID(1L)
                .username("testUser")
                .password("testPassword")
                .description("testDescription")
                .build();

        User user = new User(1L, "testUser", "testPassword", "testDescription"); // Assuming you have a corresponding constructor in User

        when(userRepository.getAllUsers()).thenReturn(List.of(userEntity));

        // When
        GetUsersResponse response = getUsersUseCase.getUsers(request);

        // Then
        assertEquals(1, response.getUsers().size());
        assertEquals(user.getUserID(), response.getUsers().get(0).getUserID());
        assertEquals(user.getUsername(), response.getUsers().get(0).getUsername());
        assertEquals(user.getDescription(), response.getUsers().get(0).getDescription());
    }
}