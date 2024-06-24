package org.example.business.impl.userIMPL;

import lombok.AllArgsConstructor;
import org.example.Configuration.security.token.impl.AccessTokenImpl;
import org.example.business.userException.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.business.user.LogInUseCase;
import org.example.Configuration.security.token.AccessTokenEncoder;
import org.example.business.dto.userDTO.LogInRequest;
import org.example.business.dto.userDTO.LogInResponse;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.UserEntity;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LogInUseCaseImpl implements LogInUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    @Override
    public LogInResponse loginUser(LogInRequest request) {

        Optional<UserEntity> user = userRepository.findByUsername(request.getUsername());

        if(user == null){
            throw new InvalidCredentialsException();
        }

        if(!matchesPassword(request.getPassword(), user.get().getPassword())){
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user.get());
        return LogInResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getUserID(), List.of(user.getRole().getRole().toString())));
    }
}