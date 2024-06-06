package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.Post.CreatePostUseCase;
import org.example.business.Post.GetAllPostsUseCase;
import org.example.business.dto.postDTO.CreatePostRequest;
import org.example.business.dto.postDTO.CreatePostResponse;
import org.example.business.dto.postDTO.GetAllPostsRequest;
import org.example.business.dto.postDTO.GetAllPostsResponse;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.business.dto.userDTO.GetUsersResponse;
import org.example.persistance.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PostController {
    private final PostRepository postRepository;
    private final CreatePostUseCase createPostUseCase;
    private final GetAllPostsUseCase getAllPostsUseCase;

    @PostMapping()
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request){
        CreatePostResponse response = createPostUseCase.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllPostsResponse> getAllPosts(){
        GetAllPostsRequest request = GetAllPostsRequest.builder().build();
        GetAllPostsResponse response = getAllPostsUseCase.getAllPosts(request);
        return ResponseEntity.ok(response);
    }


}
