package org.example.business.Like;

import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;

public interface UnlikePostUseCase {
    LikePostResponse unlikePost(LikePostRequest request);
}
