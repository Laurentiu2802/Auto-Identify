package org.example.business.user;

import org.example.business.dto.userDTO.GetUsersRequest;
import org.example.business.dto.userDTO.GetUsersResponse;

public interface GetUsersUseCase
{
    GetUsersResponse getUsers(GetUsersRequest request);
}
