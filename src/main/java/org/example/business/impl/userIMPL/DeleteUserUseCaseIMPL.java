package org.example.business.impl.userIMPL;

import org.example.business.user.DeleteUserUseCase;
import org.example.persistance.UserRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseIMPL implements DeleteUserUseCase {

    private final UserRepository userRepository;

    public void DeleteUser(Long userID) {
        if (userID == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        boolean userExists = userRepository.existsById(userID);
        if (!userExists) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteByUserID(userID);
    }
}
