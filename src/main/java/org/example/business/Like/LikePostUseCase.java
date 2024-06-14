package org.example.business.Like;

import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;

public interface LikePostUseCase {
    LikePostResponse likePost(LikePostRequest request);
}
