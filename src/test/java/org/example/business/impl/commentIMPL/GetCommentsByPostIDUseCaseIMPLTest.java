package org.example.business.impl.commentIMPL;

import org.example.business.dto.commentDTO.GetCommentsByPostIDRequest;
import org.example.business.dto.commentDTO.GetCommentsByPostIDResponse;
import org.example.domain.Comment;
import org.example.domain.Post;
import org.example.domain.User;
import org.example.persistance.CommentRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.example.persistance.entity.CarModelEntity;
import org.example.persistance.entity.CommentEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCommentsByPostIDUseCaseIMPLTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private GetCommentsByPostIDUseCaseIMPL getCommentsByPostIDUseCaseIMPL;

    private List<CommentEntity> commentEntities;
    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        CarBrandEntity carBrand = new CarBrandEntity();
        carBrand.setCarBrandID(1L);
        carBrand.setBrandName("Test Brand");

        CarModelEntity carModel = new CarModelEntity();
        carModel.setModelID(1L);
        carModel.setModelName("Test Model");
        carModel.setCarBrand(carBrand);

        UserEntity userEntity = UserEntity.builder()
                .userID(1L)
                .username("testuser")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .postID(1L)
                .user(userEntity)
                .carModel(carModel)
                .build();

        CommentEntity commentEntity1 = CommentEntity.builder()
                .commentID(1L)
                .description("Test comment 1")
                .post(postEntity)
                .user(userEntity)
                .build();

        CommentEntity commentEntity2 = CommentEntity.builder()
                .commentID(2L)
                .description("Test comment 2")
                .post(postEntity)
                .user(userEntity)
                .build();

        commentEntities = List.of(commentEntity1, commentEntity2);

        User user = User.builder()
                .userID(1L)
                .username("testuser")
                .build();

        Post post = Post.builder()
                .postID(1L)
                .user(user)
                .build();

        Comment comment1 = Comment.builder()
                .commentID(1L)
                .description("Test comment 1")
                .user(user)
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .commentID(2L)
                .description("Test comment 2")
                .user(user)
                .post(post)
                .build();

        comments = List.of(comment1, comment2);
    }

    @Test
    void getComments_shouldReturnComments_whenCommentsExistForPostID() {
        GetCommentsByPostIDRequest request = GetCommentsByPostIDRequest.builder().postID(1L).build();

        when(commentRepository.findAllByPostId(1L)).thenReturn(commentEntities);

        GetCommentsByPostIDResponse response = getCommentsByPostIDUseCaseIMPL.getComments(request);

        assertNotNull(response);
        assertEquals(2, response.getComments().size());
        assertEquals(comments.get(0).getCommentID(), response.getComments().get(0).getCommentID());
        assertEquals(comments.get(1).getCommentID(), response.getComments().get(1).getCommentID());
    }

    @Test
    void getComments_shouldReturnEmptyList_whenNoCommentsExistForPostID() {
        GetCommentsByPostIDRequest request = GetCommentsByPostIDRequest.builder().postID(1L).build();

        when(commentRepository.findAllByPostId(1L)).thenReturn(Collections.emptyList());

        GetCommentsByPostIDResponse response = getCommentsByPostIDUseCaseIMPL.getComments(request);

        assertNotNull(response);
        assertTrue(response.getComments().isEmpty());
    }

    @Test
    void getComments_shouldReturnEmptyList_whenPostIDIsNull() {
        GetCommentsByPostIDRequest request = GetCommentsByPostIDRequest.builder().postID(null).build();

        when(commentRepository.findAllByPostId(null)).thenReturn(Collections.emptyList());

        GetCommentsByPostIDResponse response = getCommentsByPostIDUseCaseIMPL.getComments(request);

        assertNotNull(response);
        assertTrue(response.getComments().isEmpty());
    }
}
