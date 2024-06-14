package org.example.business.impl.postIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Post.GetPostByCriteriaUseCase;
import org.example.business.dto.postDTO.GetAllPostsRequest;
import org.example.business.dto.postDTO.GetAllPostsResponse;
import org.example.business.dto.postDTO.GetPostsByCriteriaRequest;
import org.example.business.dto.postDTO.GetPostsByCriteriaResponse;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.PostEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPostsByCriteriaUseCaseIMPL implements GetPostByCriteriaUseCase {
    private final PostRepository postRepository;

    @Override
    public GetPostsByCriteriaResponse getPosts(GetPostsByCriteriaRequest request) {

        List<PostEntity> postsResults = postRepository.findPostsByCriteria(request.getCategoryID(), request.getCarBrandID(), request.getCarModelID());

        final GetPostsByCriteriaResponse response = new GetPostsByCriteriaResponse();
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
