package org.example.business.Post;

import org.example.business.dto.postDTO.GetAllPostsRequest;
import org.example.business.dto.postDTO.GetAllPostsResponse;

public interface GetAllPostsUseCase {
    GetAllPostsResponse getAllPosts(GetAllPostsRequest request);
}
