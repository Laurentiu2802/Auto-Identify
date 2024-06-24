package org.example.controller;

import org.example.business.Comment.CreateCommentUseCase;
import org.example.business.Comment.GetCommentsByPostIDUseCase;
import org.example.business.dto.commentDTO.CreateCommentRequest;
import org.example.business.dto.commentDTO.CreateCommentResponse;
import org.example.business.dto.commentDTO.GetCommentsByPostIDResponse;
import org.example.domain.Comment;
import org.example.persistance.CommentRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@Import(TestSecurityConfig.class)
public class CommentControllerTest {
//test for cd
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCommentUseCase createCommentUseCase;

    @MockBean
    private GetCommentsByPostIDUseCase getComments;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void createComment_shouldReturnCreatedComment() throws Exception {
        CreateCommentRequest request = CreateCommentRequest.builder()
                .postID(1L)
                .userID(1L)
                .description("Test comment")
                .build();

        CreateCommentResponse response = CreateCommentResponse.builder()
                .commentID(1L)
                .description("Test comment")
                .username("testuser")
                .build();

        when(createCommentUseCase.createComment(ArgumentMatchers.any(CreateCommentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postID\":1,\"userID\":1,\"description\":\"Test comment\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentID").value(1L))
                .andExpect(jsonPath("$.description").value("Test comment"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getComments_shouldReturnCommentsForPostID() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userID(1L)
                .username("testuser")
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryID(1L)
                .categoryName("Test Category")
                .build();

        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Test Brand")
                .build();

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Test Model")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .postID(1L)
                .user(userEntity)
                .description("Test post")
                .category(categoryEntity)
                .carBrand(carBrandEntity)
                .carModel(carModelEntity)
                .build();

        CommentEntity commentEntity1 = CommentEntity.builder()
                .commentID(1L)
                .description("Test comment 1")
                .post(postEntity)
                .build();

        CommentEntity commentEntity2 = CommentEntity.builder()
                .commentID(2L)
                .description("Test comment 2")
                .post(postEntity)
                .build();

        when(commentRepository.findAllByPostId(1L)).thenReturn(List.of(commentEntity1, commentEntity2));

        Comment comment1 = Comment.builder()
                .commentID(1L)
                .description("Test comment 1")
                .build();

        Comment comment2 = Comment.builder()
                .commentID(2L)
                .description("Test comment 2")
                .build();

        GetCommentsByPostIDResponse response = new GetCommentsByPostIDResponse();
        response.setComments(List.of(comment1, comment2));

        when(getComments.getComments(ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(get("/comments")
                        .param("postID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[0].commentID").value(1L))
                .andExpect(jsonPath("$.comments[0].description").value("Test comment 1"))
                .andExpect(jsonPath("$.comments[1].commentID").value(2L))
                .andExpect(jsonPath("$.comments[1].description").value("Test comment 2"));
    }

}
