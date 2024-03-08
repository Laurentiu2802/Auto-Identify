package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.userDTO.CreateUserRequest;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.business.user.CreateUserUseCase;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseIMPL implements CreateUserUseCase{
    private final UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .userID(savedUser.getUserID())
                .build();
    }

    public UserEntity saveNewUser(CreateUserRequest request){
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .description(request.getDescription())
                .build();
        return userRepository.save(newUser);
    }
}
