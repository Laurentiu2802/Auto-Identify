package org.example.business.impl.userIMPL;

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
        long userId = 1L;
        UserRoleEntity role = new UserRoleEntity();
        UserEntity userEntity = UserEntity.builder()
                .userID(userId)
                .username("testUser")
                .password("securePassword")
                .description("Test Description")
                .role(role)
                .build();

        User expectedUser = UserConverter.convert(userEntity);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<User> result = getUserDetailsUseCaseIMPL.getUser(userId);

        assertTrue(result.isPresent());
        assertEquals(expectedUser.getUserID(), result.get().getUserID());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testGetUserNotFound() {
        long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = getUserDetailsUseCaseIMPL.getUser(userId);

        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
    }
}
