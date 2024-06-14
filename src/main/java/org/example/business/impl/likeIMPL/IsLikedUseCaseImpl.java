package org.example.business.impl.likeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Like.IsLikedUseCase;
import org.example.business.dto.likeDTO.IsLikedResponse;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.persistance.LikeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IsLikedUseCaseImpl implements IsLikedUseCase {
    private final LikeRepository likeRepository;
    @Override
    public IsLikedResponse isLiked(LikePostRequest request) {
        IsLikedResponse response = new IsLikedResponse();
        response.setLiked(likeRepository.existsByUserUserIDAndPostPostID(request.getUserID(), request.getPostID()));
        return response;
    }
}
