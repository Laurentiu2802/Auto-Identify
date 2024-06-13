package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.business.user.GetUserDetailsUseCase;
import org.example.domain.User;
import org.example.persistance.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserDetailsUseCaseIMPL implements GetUserDetailsUseCase {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(long userId) {
        return userRepository.findById(userId).map(UserConverter::convert);
    }
}
