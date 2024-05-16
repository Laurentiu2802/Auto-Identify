package org.example.business.impl.userIMPL;

import org.example.business.dto.userDTO.GetUsersRequest;
import org.example.business.dto.userDTO.GetUsersResponse;
import org.example.domain.User;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsersUseCaseIMPLTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersUseCaseIMPL getUsersUseCaseIMPL;

    @Test
    void testGetUsers() {
        // Set up test data
        UserRoleEntity adminRole = new UserRoleEntity(1L, RoleEnum.ADMIN);
        UserRoleEntity userRole = new UserRoleEntity(2L, RoleEnum.USER);

        UserEntity user1 = new UserEntity();
        user1.setUserID(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setDescription("description1");
        user1.setRole(adminRole);

        UserEntity user2 = new UserEntity();
        user2.setUserID(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setDescription("description2");
        user2.setRole(userRole);

        List<UserEntity> userEntities = Arrays.asList(user1, user2);

        // Stub the repository method
        when(userRepository.getAllUsers()).thenReturn(userEntities);

        // Create the request and call the service method
        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        // Assertions
        assertNotNull(response);
        assertEquals(2, response.getUsers().size());
        assertEquals("user1", response.getUsers().get(0).getUsername());
        assertEquals("user2", response.getUsers().get(1).getUsername());

        // Verify interactions
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersWhenNoUsers() {
        // Stub the repository method to return an empty list
        when(userRepository.getAllUsers()).thenReturn(Collections.emptyList());

        // Create the request and call the service method
        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        // Assertions
        assertNotNull(response);
        assertEquals(0, response.getUsers().size());

        // Verify interactions
        verify(userRepository, times(1)).getAllUsers();
    }


    @Test
    void testGetUsersWithNullFields() {
        // Set up test data with null fields
        UserRoleEntity adminRole = new UserRoleEntity(1L, RoleEnum.ADMIN);

        UserEntity user1 = new UserEntity();
        user1.setUserID(1L);
        user1.setUsername(null); // Null username
        user1.setPassword("password1");
        user1.setDescription("description1");
        user1.setRole(adminRole);

        List<UserEntity> userEntities = Collections.singletonList(user1);

        // Stub the repository method
        when(userRepository.getAllUsers()).thenReturn(userEntities);

        // Create the request and call the service method
        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        // Assertions
        assertNotNull(response);
        assertEquals(1, response.getUsers().size());
        assertNull(response.getUsers().get(0).getUsername());
        assertEquals("password1", response.getUsers().get(0).getPassword());

        // Verify interactions
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersWhenRepositoryThrowsException() {
        // Stub the repository method to throw an exception
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException("Database error"));

        // Create the request and call the service method
        GetUsersRequest request = new GetUsersRequest();

        // Assertions
        Exception exception = assertThrows(RuntimeException.class, () -> {
            getUsersUseCaseIMPL.getUsers(request);
        });

        assertEquals("Database error", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).getAllUsers();
    }
}