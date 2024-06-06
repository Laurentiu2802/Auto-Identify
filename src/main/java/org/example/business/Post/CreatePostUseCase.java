package org.example.business.Post;

import org.example.business.dto.postDTO.CreatePostRequest;
import org.example.business.dto.postDTO.CreatePostResponse;

public interface CreatePostUseCase {
    CreatePostResponse createPost(CreatePostRequest request);
}
