package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.business.Like.IsLikedUseCase;
import org.example.business.Like.LikePostUseCase;
import org.example.business.Like.UnlikePostUseCase;
import org.example.business.dto.likeDTO.IsLikedResponse;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:*", allowCredentials = "true")
public class LikeController {
    private final LikePostUseCase likePostUseCase;
    private final UnlikePostUseCase unlikePostUseCase;
    private final IsLikedUseCase isLikedUseCase;

    @PostMapping("/like")
    public ResponseEntity<LikePostResponse> likePost(@RequestBody LikePostRequest request) {
        if (request == null || request.getUserID() == null || request.getPostID() == null) {
            return ResponseEntity.badRequest().build();
        }

        LikePostResponse response = likePostUseCase.likePost(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unlike")
    public ResponseEntity<LikePostResponse> unlikePost(@RequestBody LikePostRequest request) {
        LikePostResponse response = unlikePostUseCase.unlikePost(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hasLiked")
    public ResponseEntity<IsLikedResponse> hasUserLikedPost(@RequestParam Long userID,
                                                            @RequestParam Long postID) {
        LikePostRequest likePostRequest = LikePostRequest.builder().userID(userID).postID(postID).build();
        IsLikedResponse response = isLikedUseCase.isLiked(likePostRequest);
        return ResponseEntity.ok(response);
    }

}
