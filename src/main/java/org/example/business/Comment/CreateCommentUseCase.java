package org.example.business.Comment;

import org.example.business.dto.commentDTO.CreateCommentRequest;
import org.example.business.dto.commentDTO.CreateCommentResponse;

public interface CreateCommentUseCase {
    CreateCommentResponse createComment(CreateCommentRequest request);
}
