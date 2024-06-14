package org.example.business.impl.postIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Post.GetAllPostsUseCase;
import org.example.business.dto.postDTO.GetAllPostsRequest;
import org.example.business.dto.postDTO.GetAllPostsResponse;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.PostEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPostsUseCaseIMPL implements GetAllPostsUseCase {
    private final PostRepository postRepository;

    @Override
    public GetAllPostsResponse getAllPosts(GetAllPostsRequest request) {

        List<PostEntity> postsResults = postRepository.findAll();

        final GetAllPostsResponse response = new GetAllPostsResponse();
        List<Post> posts = postsResults
                .stream()
                .map(postEntity -> {
                    Post post = PostConverter.convert(postEntity);
                    post.setLikes(postRepository.countLikesForPost(postEntity.getPostID()));
                    return post;
                })
                .toList();
        response.setPosts(posts);
        return response;
    }
}
