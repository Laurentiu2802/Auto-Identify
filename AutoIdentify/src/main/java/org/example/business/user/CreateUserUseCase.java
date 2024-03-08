package org.example.business.user;

import org.example.business.dto.userDTO.CreateUserRequest;
import org.example.business.dto.userDTO.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
