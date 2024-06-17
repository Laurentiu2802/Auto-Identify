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

        when(userRepository.getAllUsers()).thenReturn(userEntities);

        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        assertNotNull(response);
        assertEquals(2, response.getUsers().size());
        assertEquals("user1", response.getUsers().get(0).getUsername());
        assertEquals("user2", response.getUsers().get(1).getUsername());

        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersWhenNoUsers() {
        when(userRepository.getAllUsers()).thenReturn(Collections.emptyList());

        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        assertNotNull(response);
        assertEquals(0, response.getUsers().size());

        verify(userRepository, times(1)).getAllUsers();
    }


    @Test
    void testGetUsersWithNullFields() {
        UserRoleEntity adminRole = new UserRoleEntity(1L, RoleEnum.ADMIN);

        UserEntity user1 = new UserEntity();
        user1.setUserID(1L);
        user1.setUsername(null); // Null username
        user1.setPassword("password1");
        user1.setDescription("description1");
        user1.setRole(adminRole);

        List<UserEntity> userEntities = Collections.singletonList(user1);

        when(userRepository.getAllUsers()).thenReturn(userEntities);

        GetUsersRequest request = new GetUsersRequest();
        GetUsersResponse response = getUsersUseCaseIMPL.getUsers(request);

        assertNotNull(response);
        assertEquals(1, response.getUsers().size());
        assertNull(response.getUsers().get(0).getUsername());
        assertEquals("password1", response.getUsers().get(0).getPassword());

        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersWhenRepositoryThrowsException() {
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException("Database error"));

        GetUsersRequest request = new GetUsersRequest();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            getUsersUseCaseIMPL.getUsers(request);
        });

        assertEquals("Database error", exception.getMessage());

        verify(userRepository, times(1)).getAllUsers();
    }
}