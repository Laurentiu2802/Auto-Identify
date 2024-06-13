package org.example.business.user;

import org.example.domain.User;

import java.util.Optional;

public interface GetUserDetailsUseCase {
    Optional<User> getUser(long userId);
}
