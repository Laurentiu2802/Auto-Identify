package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.userDTO.GetUsersRequest;
import org.example.business.dto.userDTO.GetUsersResponse;
import org.example.business.user.GetUsersUseCase;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.example.domain.User;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseIMPL implements GetUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public GetUsersResponse getUsers(GetUsersRequest request){
        List<UserEntity> result = userRepository.getAllUsers();

        final GetUsersResponse response = new GetUsersResponse();
        List<User> users = result
                .stream()
                .map(UserConverter::convert)
                .toList();
        response.setUsers(users);
        return response;

    }
}
