package org.example.business.impl.postIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Post.CountPostsUseCase;
import org.example.business.dto.postDTO.GetUserPostCountResponse;
import org.example.domain.CountPosts;
import org.example.persistance.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountPostsUseCaseImpl implements CountPostsUseCase {
    private final PostRepository postRepository;

    @Override
    public GetUserPostCountResponse getUserPostCount(){
        List<org.example.domain.CountPosts> getUserPostCount = postRepository.getUserPostCounts();

        final GetUserPostCountResponse response = new GetUserPostCountResponse();
        response.setUserPostCount(getUserPostCount);
        return response;
    }
}
