package org.example.business.impl.likeIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Like.UnlikePostUseCase;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.example.persistance.LikeRepository;
import org.example.persistance.entity.LikeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UnlikePostUseCaseImpl implements UnlikePostUseCase {
    private final LikeRepository likeRepository;

    @Override
    public LikePostResponse unlikePost(LikePostRequest request) {
        LikePostResponse response = new LikePostResponse();

        Optional<LikeEntity> likeOptional = likeRepository.findByUserUserIDAndPostPostID(request.getUserID(), request.getPostID());
        if (likeOptional.isPresent()) {
            LikeEntity like = likeOptional.get();
            likeRepository.delete(like);
            response.setSuccess(true);
        }

        return response;
    }
}
