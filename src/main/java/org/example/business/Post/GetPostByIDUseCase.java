package org.example.business.Post;

import org.example.domain.Post;

import java.util.Optional;

public interface GetPostByIDUseCase {
    Optional<Post> getPost(long postID);
}
