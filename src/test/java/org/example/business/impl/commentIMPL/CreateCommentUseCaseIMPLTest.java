package org.example.business.impl.commentIMPL;

import org.example.business.dto.commentDTO.CreateCommentRequest;
import org.example.business.dto.commentDTO.CreateCommentResponse;
import org.example.persistance.CommentRepository;
import org.example.persistance.PostRepository;
import org.example.persistance.UserRepository;
import org.example.persistance.entity.CommentEntity;
import org.example.persistance.entity.PostEntity;
import org.example.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCommentUseCaseIMPLTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CreateCommentUseCaseIMPL createCommentUseCaseIMPL;

    private UserEntity userEntity;
    private PostEntity postEntity;
    private CommentEntity commentEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder().userID(1L).username("testUser").build();
        postEntity = PostEntity.builder().postID(1L).build();
        commentEntity = CommentEntity.builder().commentID(1L).description("test comment").user(userEntity).post(postEntity).build();
    }

    @Test
    void createComment_shouldReturnResponse_whenRequestIsValid() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(1L)).thenReturn(Optional.of(postEntity));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        CreateCommentResponse response = createCommentUseCaseIMPL.createComment(request);

        assertNotNull(response);
        assertEquals(1L, response.getCommentID());
        assertEquals("test comment", response.getDescription());
        assertEquals("testUser", response.getUsername());
    }

    @Test
    void createComment_shouldThrowException_whenUserDoesNotExist() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCommentUseCaseIMPL.createComment(request);
        });

        assertEquals("User ID: 1 is not existent", exception.getMessage());
    }

    @Test
    void createComment_shouldThrowException_whenPostDoesNotExist() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCommentUseCaseIMPL.createComment(request);
        });

        assertEquals("Post ID: 1 is not existent", exception.getMessage());
    }

    @Test
    void saveNewComment_shouldReturnSavedComment_whenUserAndPostExist() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(1L)).thenReturn(Optional.of(postEntity));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        CommentEntity savedComment = createCommentUseCaseIMPL.saveNewComment(request);

        assertNotNull(savedComment);
        assertEquals(1L, savedComment.getCommentID());
        assertEquals("test comment", savedComment.getDescription());
    }

    @Test
    void saveNewComment_shouldThrowException_whenUserDoesNotExist() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            createCommentUseCaseIMPL.saveNewComment(request);
        });

        assertEquals("User ID: 1 is not existent", exception.getMessage());
    }

    @Test
    void saveNewComment_shouldThrowException_whenPostDoesNotExist() {
        CreateCommentRequest request = CreateCommentRequest.builder().userID(1L).postID(1L).description("test comment").build();

        when(userRepository.findByUserID(1L)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            createCommentUseCaseIMPL.saveNewComment(request);
        });

        assertEquals("Post ID: 1 is not existent", exception.getMessage());
    }
}
