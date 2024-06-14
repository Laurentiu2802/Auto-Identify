package org.example.business.impl.likeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Like.LikePostUseCase;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.example.persistance.LikeRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.LikeEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikePostUseCaseImpl implements LikePostUseCase {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public LikePostResponse likePost(LikePostRequest request) {
        LikePostResponse response = new LikePostResponse();

        if (!likeRepository.existsByUserUserIDAndPostPostID(request.getUserID(), request.getPostID())) {
            UserEntity user = userRepository.getById(request.getUserID());
            PostEntity post = postRepository.getById(request.getPostID());

            if (user != null && post != null) {
                LikeEntity like = new LikeEntity();
                like.setUser(user);
                like.setPost(post);
                likeRepository.save(like);
                response.setSuccess(true);
            }
        }

        return response;
    }
}
