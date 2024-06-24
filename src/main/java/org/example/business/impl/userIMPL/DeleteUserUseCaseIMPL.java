package org.example.business.impl.userIMPL;

import jakarta.transaction.Transactional;
import org.example.business.user.DeleteUserUseCase;
import org.example.persistance.CommentRepository;
import org.example.persistance.LikeRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseIMPL implements DeleteUserUseCase {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    @Transactional
    public void DeleteUser(Long userID) {
        if (userID == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        boolean userExists = userRepository.existsById(userID);
        if (!userExists) {
            throw new RuntimeException("User not found");
        }
        likeRepository.deleteByUser_UserID(userID);
        commentRepository.deleteByUser_UserID(userID);
        postRepository.deleteByUser_UserID(userID);
        userRepository.deleteByUserID(userID);
    }
}
