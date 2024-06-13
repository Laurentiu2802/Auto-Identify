package org.example.business.impl.commentIMPL;

import lombok.AllArgsConstructor;
import org.example.business.Comment.GetCommentsByPostIDUseCase;
import org.example.business.dto.commentDTO.GetCommentsByPostIDRequest;
import org.example.business.dto.commentDTO.GetCommentsByPostIDResponse;
import org.example.controller.CommentController;
import org.example.domain.Comment;
import org.example.persistance.CommentRepository;
import org.example.persistance.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetCommentsByPostIDUseCaseIMPL implements GetCommentsByPostIDUseCase {
    private final CommentRepository commentRepository;

    @Override
    public GetCommentsByPostIDResponse getComments(GetCommentsByPostIDRequest request){
        List<CommentEntity> commentsResult = commentRepository.findAllByPostId(request.getPostID());

        final GetCommentsByPostIDResponse response = new GetCommentsByPostIDResponse();
        List<Comment> comments = commentsResult
                .stream()
                .map(CommentConverter::convert)
                .toList();
        response.setComments(comments);
        return response;
    }
}
