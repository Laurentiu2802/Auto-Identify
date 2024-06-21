package org.example.controller;

import org.example.business.Like.IsLikedUseCase;
import org.example.business.Like.LikePostUseCase;
import org.example.business.Like.UnlikePostUseCase;
import org.example.business.dto.likeDTO.IsLikedResponse;
import org.example.business.dto.likeDTO.LikePostRequest;
import org.example.business.dto.likeDTO.LikePostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikePostUseCase likePostUseCase;

    @Mock
    private UnlikePostUseCase unlikePostUseCase;

    @Mock
    private IsLikedUseCase isLikedUseCase;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
    }

    @Test
    void likePost_shouldReturnOk_whenRequestIsValid() throws Exception {
        LikePostResponse response = new LikePostResponse();
        response.setSuccess(true);
        when(likePostUseCase.likePost(any(LikePostRequest.class))).thenReturn(response);

        mockMvc.perform(post("/likes/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1, \"postID\": 2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void likePost_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/likes/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": null, \"postID\": null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unlikePost_shouldReturnOk_whenRequestIsValid() throws Exception {
        LikePostResponse response = new LikePostResponse();
        response.setSuccess(true);
        when(unlikePostUseCase.unlikePost(any(LikePostRequest.class))).thenReturn(response);

        mockMvc.perform(post("/likes/unlike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1, \"postID\": 2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void hasUserLikedPost_shouldReturnOk_whenRequestIsValid() throws Exception {
        IsLikedResponse response = new IsLikedResponse();
        response.setLiked(true);
        when(isLikedUseCase.isLiked(any(LikePostRequest.class))).thenReturn(response);

        mockMvc.perform(get("/likes/hasLiked")
                        .param("userID", "1")
                        .param("postID", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liked", is(true)));
    }
}
