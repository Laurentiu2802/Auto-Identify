package org.example.controller;

import org.example.business.dto.userDTO.*;
import org.example.business.user.*;
import org.example.domain.User;
import org.example.persistance.entity.UserEntity;
import org.example.persistance.entity.UserRoleEntity;
import org.example.persistance.entity.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetUsersUseCase getUsersUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private LogInUseCase logInUseCase;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUsers_ShouldReturnUsers() throws Exception {
        GetUsersResponse response = new GetUsersResponse();
        UserRoleEntity userRole = UserRoleEntity.builder().id(1L).role(RoleEnum.USER).build();
        User user = User.builder()
                .userID(1L)
                .username("user1")
                .description("description1")
                .userRole(userRole)
                .build();
        response.setUsers(List.of(user));

        when(getUsersUseCase.getUsers(any(GetUsersRequest.class))).thenReturn(response);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.users[0].username").value("user1"));
    }


    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        doNothing().when(deleteUserUseCase).DeleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_ShouldReturnNoContent() throws Exception {
        // Mocking the update behavior
        doNothing().when(updateUserUseCase).updateUser(any(UpdateUserRequest.class));

        // Creating the request object
        UpdateUserRequest request = UpdateUserRequest.builder()
                .userID(1L)
                .username("updatedUser")
                .password("updatedPassword")
                .description("updatedDescription")
                .build();

        // Performing the request and expecting a 204 No Content response
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("newUser")
                .password("password")
                .description("newDescription")
                .build();

        CreateUserResponse response = CreateUserResponse.builder()
                .userID(1L)
                .build();

        when(createUserUseCase.createUser(any(CreateUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userID").value(1L));
    }

    @Test
    void login_ShouldReturnToken() throws Exception {
        LogInRequest request = LogInRequest.builder()
                .username("user")
                .password("password")
                .build();

        LogInResponse response = LogInResponse.builder()
                .accessToken("token")
                .build();

        when(logInUseCase.loginUser(any(LogInRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users/tokens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token")); // Use $.token to match the mapped property
    }
}
