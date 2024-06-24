package org.example.business.impl.postIMPL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.persistance.PostRepository;
import org.example.business.dto.postDTO.GetUserPostCountResponse;
import org.example.domain.CountPosts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CountPostsUseCaseIMPLTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CountPostsUseCaseIMPL countPostsUseCase;

    @Test
    void getUserPostCount_WhenNoPosts_ShouldReturnEmptyList() {
        when(postRepository.getUserPostCounts()).thenReturn(Collections.emptyList());

        GetUserPostCountResponse response = countPostsUseCase.getUserPostCount();

        assertTrue(response.getUserPostCount().isEmpty(), "The list of post counts should be empty");
    }

    @Test
    void getUserPostCount_WhenPostsExist_ShouldReturnCorrectCounts() {
        List<CountPosts> mockPostCounts = Arrays.asList(
                new CountPosts(1L, "user1", 10L),
                new CountPosts(2L, "user2", 15L)
        );
        when(postRepository.getUserPostCounts()).thenReturn(mockPostCounts);

        GetUserPostCountResponse response = countPostsUseCase.getUserPostCount();

        assertNotNull(response.getUserPostCount(), "The response should not be null");
        assertEquals(2, response.getUserPostCount().size(), "There should be counts for two users");
        assertEquals(10, response.getUserPostCount().get(0).getPostCount(), "The post count for user1 should be 10");
        assertEquals(15, response.getUserPostCount().get(1).getPostCount(), "The post count for user2 should be 15");
    }

    @Test
    void getUserPostCount_WhenRepositoryThrowsException() {
        when(postRepository.getUserPostCounts()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            countPostsUseCase.getUserPostCount();
        });

        assertEquals("Database error", exception.getMessage(), "The exception message should match the expected one");
    }
}
