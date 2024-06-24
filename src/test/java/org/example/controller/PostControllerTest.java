package org.example.controller;

import org.example.Configuration.security.token.AccessToken;
import org.example.business.Post.*;
import org.example.business.dto.postDTO.*;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@Import(TestSecurityConfig.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CreatePostUseCase createPostUseCase;

    @MockBean
    private GetAllPostsUseCase getAllPostsUseCase;

    @MockBean
    private GetPostByCriteriaUseCase getPostByCriteriaUseCase;

    @MockBean
    private CountPostsUseCase countPostsUseCase;

    @MockBean
    private GetPostByIDUseCase getPostByIDUseCase;

    @MockBean
    private AccessToken authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_shouldReturnCreatedPost() throws Exception {
        CreatePostRequest request = new CreatePostRequest();
        request.setUserID(4L);
        CreatePostResponse response = new CreatePostResponse();
        when(createPostUseCase.createPost(any(CreatePostRequest.class))).thenReturn(response);
        when(authUser.getStudentId()).thenReturn(4L);

        String requestBody = """
        {
            "description": "Test post",
            "categoryID": 1,
            "carModelID": 2,
            "carBrandID": 3,
            "userID": 4
        }
    """;

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(createPostUseCase, times(1)).createPost(any(CreatePostRequest.class));
    }

    @Test
    void createPost_shouldReturnForbidden() throws Exception {
        CreatePostRequest request = new CreatePostRequest();
        request.setUserID(4L);
        when(authUser.getStudentId()).thenReturn(5L);  // Different user ID

        String requestBody = """
        {
            "description": "Test post",
            "categoryID": 1,
            "carModelID": 2,
            "carBrandID": 3,
            "userID": 4
        }
    """;

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());

        verify(createPostUseCase, times(0)).createPost(any(CreatePostRequest.class));
    }

    @Test
    void getAllPosts_shouldReturnAllPosts() throws Exception {
        GetAllPostsResponse response = new GetAllPostsResponse();
        when(getAllPostsUseCase.getAllPosts(any(GetAllPostsRequest.class))).thenReturn(response);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(getAllPostsUseCase, times(1)).getAllPosts(any(GetAllPostsRequest.class));
    }

    @Test
    void getPost_shouldReturnPost() throws Exception {
        Post post = new Post();
        when(getPostByIDUseCase.getPost(anyLong())).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(getPostByIDUseCase, times(1)).getPost(1L);
    }

    @Test
    void getPost_shouldReturnNotFound() throws Exception {
        when(getPostByIDUseCase.getPost(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(getPostByIDUseCase, times(1)).getPost(1L);
    }

    @Test
    void getPostsByCriteria_shouldReturnPosts() throws Exception {
        CarBrandEntity carBrand = new CarBrandEntity();
        carBrand.setCarBrandID(2L);
        carBrand.setBrandName("BrandName");

        CarModelEntity carModel = new CarModelEntity();
        carModel.setModelID(3L);
        carModel.setModelName("ModelName");
        carModel.setCarBrand(carBrand);

        CategoryEntity category = new CategoryEntity();
        category.setCategoryID(1L);
        category.setCategoryName("CategoryName");

        UserEntity user = new UserEntity();
        user.setUserID(4L);
        user.setUsername("UserName");

        PostEntity postEntity1 = new PostEntity();
        postEntity1.setPostID(1L);
        postEntity1.setDescription("Description1");
        postEntity1.setCarModel(carModel);
        postEntity1.setCarBrand(carBrand);
        postEntity1.setCategory(category);
        postEntity1.setUser(user);

        PostEntity postEntity2 = new PostEntity();
        postEntity2.setPostID(2L);
        postEntity2.setDescription("Description2");
        postEntity2.setCarModel(carModel);
        postEntity2.setCarBrand(carBrand);
        postEntity2.setCategory(category);
        postEntity2.setUser(user);

        List<PostEntity> postEntities = Arrays.asList(postEntity1, postEntity2);
        List<Post> posts = Arrays.asList(new Post(), new Post());

        when(postRepository.findPostsByCriteria(any(), any(), any())).thenReturn(postEntities);

        mockMvc.perform(get("/posts/search")
                        .param("categoryID", "1")
                        .param("carBrandID", "2")
                        .param("carModelID", "3"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"posts\":[{},{}]}"));

        verify(postRepository, times(1)).findPostsByCriteria(any(), any(), any());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getUserPostCount_shouldReturnStatistics() throws Exception {
        GetUserPostCountResponse response = new GetUserPostCountResponse();
        when(countPostsUseCase.getUserPostCount()).thenReturn(response);

        mockMvc.perform(get("/posts/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(countPostsUseCase, times(1)).getUserPostCount();
    }

    @WithMockUser(roles = "USER")
    @Test
    void getUserPostCount_withUserRole_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/posts/statistics"))
                .andExpect(status().isForbidden());

        verify(countPostsUseCase, times(0)).getUserPostCount();
    }
}
