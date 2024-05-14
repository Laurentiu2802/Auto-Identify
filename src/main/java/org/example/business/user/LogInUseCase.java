package org.example.business.user;

import org.example.business.dto.userDTO.LogInRequest;
import org.example.business.dto.userDTO.LogInResponse;
public interface LogInUseCase {
    LogInResponse loginUser(LogInRequest request);

}
