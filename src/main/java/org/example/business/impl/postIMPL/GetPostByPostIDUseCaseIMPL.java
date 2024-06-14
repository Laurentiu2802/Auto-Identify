package org.example.business.impl.postIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Post.GetPostByIDUseCase;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPostByPostIDUseCaseIMPL implements GetPostByIDUseCase {
    private final PostRepository postRepository;

    @Override
    public Optional<Post> getPost(long postID) {
        return postRepository.findPostById(postID).map(PostConverter::convert);
    }
}
