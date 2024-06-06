package org.example.business.impl.postIMPL;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.Post.CreatePostUseCase;
import org.example.business.dto.postDTO.CreatePostRequest;
import org.example.business.dto.postDTO.CreatePostResponse;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePostUseCaseIMPL implements CreatePostUseCase {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private static final String Error = "is not existent";

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request){
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User ID: " + request.getUserID() + Error);
        }

        PostEntity savedPost = saveNewPost(request);

        return CreatePostResponse.builder()
                .postID(savedPost.getPostID())
                .description(savedPost.getDescription())
                .build();
    }

    private PostEntity saveNewPost(CreatePostRequest request) {
        Optional<UserEntity> user = userRepository.findByUserID(request.getUserID());

        if(user.isPresent()) {
            PostEntity newPost = PostEntity.builder()
                    .description(request.getDescription())
                    .user(user.get())
                    .build();
            return postRepository.save(newPost);
        } else {
            throw new IllegalStateException("User ID: " + request.getUserID() + Error);
        }
    }


}
