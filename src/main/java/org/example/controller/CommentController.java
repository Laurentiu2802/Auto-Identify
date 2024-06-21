package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.Comment.CreateCommentUseCase;
import org.example.business.Comment.GetCommentsByPostIDUseCase;
import org.example.business.dto.commentDTO.CreateCommentRequest;
import org.example.business.dto.commentDTO.CreateCommentResponse;
import org.example.business.dto.commentDTO.GetCommentsByPostIDResponse;
import org.example.business.impl.commentIMPL.CommentConverter;
import org.example.domain.Comment;
import org.example.persistance.CommentRepository;
import org.example.persistance.entity.CommentEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CreateCommentUseCase createCommentUseCase;
    private final GetCommentsByPostIDUseCase getComments;


    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody @Valid CreateCommentRequest request){
        CreateCommentResponse response = createCommentUseCase.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetCommentsByPostIDResponse> getComments(
            @RequestParam Long postID){
        List<CommentEntity> commentEntities = commentRepository.findAllByPostId(postID);

        List<Comment> comments = commentEntities.stream()
                .map(CommentConverter::convert)
                .collect(Collectors.toList());

        GetCommentsByPostIDResponse response = new GetCommentsByPostIDResponse();
        response.setComments(comments);

        return  ResponseEntity.ok(response);
    }

}
