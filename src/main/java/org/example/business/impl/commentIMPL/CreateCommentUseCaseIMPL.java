package org.example.business.impl.commentIMPL;

import jakarta.persistence.Access;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.Comment.CreateCommentUseCase;
import org.example.business.dto.commentDTO.CreateCommentRequest;
import org.example.business.dto.commentDTO.CreateCommentResponse;
import org.example.persistance.CommentRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.CommentEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCommentUseCaseIMPL implements CreateCommentUseCase {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private static final String Error = " is not existent";

    @Transactional
    @Override
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());
        Optional<PostEntity> post = postRepository.findById(request.getPostID());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User ID: " + request.getUserID() + Error);
        } else if ((post.isEmpty())) {
            throw new IllegalArgumentException("Post ID: " + request.getPostID() + Error);
        }

        CommentEntity savedComment = saveNewComment(request);

        return CreateCommentResponse.builder()
                .commentID(savedComment.getCommentID())
                .description(savedComment.getDescription())
                .username(savedComment.getUser().getUsername())
                .build();
    }

    public CommentEntity saveNewComment(CreateCommentRequest request){
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());
        Optional<PostEntity> post = postRepository.findById(request.getPostID());
        if(user.isPresent()){
            if(post.isPresent()){
                CommentEntity newComment = CommentEntity.builder()
                        .description(request.getDescription())
                        .user(user.get())
                        .post(post.get())
                        .build();
                return commentRepository.save(newComment);
            } else {
                throw new IllegalStateException("Post ID: " + request.getPostID() + Error);
            }
        } else {
            throw new IllegalStateException("User ID: " + request.getUserID() + Error);
        }
    }

}
