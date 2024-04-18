package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.user.DeleteUserUseCase;
import org.example.persistance.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseIMPL implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void DeleteUser(Long userID){
        this.userRepository.deleteByUserID(userID);
    }
}
