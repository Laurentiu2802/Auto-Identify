package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.userDTO.UpdateUserRequest;
import org.example.business.user.UpdateUserUseCase;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseIMPL implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserRequest request){
        Optional<UserEntity> userOptional = userRepository.findByUserID(request.getUserID());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            updateFields(request, user);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("User not found with ID: " + request.getUserID());
        }
    }

    private void updateFields(UpdateUserRequest request, UserEntity user){
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setDescription(request.getDescription());
    }
}
