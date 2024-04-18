package org.example.business.impl.userIMPL;

import org.example.business.user.DeleteUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;

import org.example.business.impl.userIMPL.DeleteUserUseCaseIMPL;
import org.example.persistance.UserRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseIMPLTest {
        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private DeleteUserUseCaseIMPL deleteUserUseCase;

        @Test
        public void deleteUser_ShouldCallDeleteByID_WhenUserIDIsProvided() {
            // Given
            Long userId = 1L;

            // When
            deleteUserUseCase.DeleteUser(userId);

            // Then
            verify(userRepository).deleteByUserID(anyLong());
        }
    }