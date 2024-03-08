package org.example.business.user;

import org.example.business.dto.userDTO.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
