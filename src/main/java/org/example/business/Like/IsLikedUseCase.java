package org.example.business.Like;

import org.example.business.dto.likeDTO.IsLikedResponse;
import org.example.business.dto.likeDTO.LikePostRequest;

public interface IsLikedUseCase {
    IsLikedResponse isLiked(LikePostRequest request);
}
