package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.userDTO.CreateUserRequest;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.business.user.CreateUserUseCase;
import org.example.persistance.RoleRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.RoleEnum;
import org.example.persistance.entity.UserEntity;
import org.example.persistance.entity.UserRoleEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseIMPL implements CreateUserUseCase{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .userID(savedUser.getUserID())
                .build();
    }

    public UserEntity saveNewUser(CreateUserRequest request){
        UserRoleEntity role = roleRepository.findByRole(RoleEnum.USER);
        request.setUserRole(role);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .description(request.getDescription())
                .role(role)
                .build();
        return userRepository.save(newUser);
    }
}
