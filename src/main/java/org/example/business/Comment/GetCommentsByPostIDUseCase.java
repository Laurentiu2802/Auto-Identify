package org.example.business.Comment;

import org.example.business.dto.commentDTO.GetCommentsByPostIDRequest;
import org.example.business.dto.commentDTO.GetCommentsByPostIDResponse;

public interface GetCommentsByPostIDUseCase {
    GetCommentsByPostIDResponse getComments(GetCommentsByPostIDRequest request);
}
