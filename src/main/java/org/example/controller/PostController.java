package org.example.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.Post.*;
import org.example.business.dto.postDTO.*;
import org.example.business.dto.userDTO.CreateUserResponse;
import org.example.business.dto.userDTO.GetUsersResponse;
import org.example.business.impl.postIMPL.PostConverter;
import org.example.domain.Post;
import org.example.persistance.PostRepository;
import org.example.persistance.entity.PostEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PostController {
    private final PostRepository postRepository;
    private final CreatePostUseCase createPostUseCase;
    private final GetAllPostsUseCase getAllPostsUseCase;
    private final GetPostByCriteriaUseCase getPostByCriteriaUseCase;
    private final CountPostsUseCase countPostsUseCase;
    private final GetPostByIDUseCase getPostByIDUseCase;

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

    @GetMapping("{id}")
    public ResponseEntity<Post> getPost(@PathVariable(value = "id") final long id){
        final Optional<Post> postOptional = getPostByIDUseCase.getPost(id);
        if(postOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(postOptional.get());
    }

    @GetMapping("/search")
    public ResponseEntity<GetPostsByCriteriaResponse> getPostsByCriteria(
            @RequestParam(required = false) Long postID,
            @RequestParam(required = false) Long categoryID,
            @RequestParam(required = false) Long carBrandID,
            @RequestParam(required = false) Long carModelID) {

        List<PostEntity> postEntities = postRepository.findPostsByCriteria(categoryID, carBrandID, carModelID);

        List<Post> posts = postEntities.stream()
                .map(PostConverter::convert)
                .collect(Collectors.toList());

        GetPostsByCriteriaResponse response = new GetPostsByCriteriaResponse();
        response.setPosts(posts);

        return ResponseEntity.ok(response);
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping("statistics")
    public ResponseEntity<GetUserPostCountResponse> getUserPostCount(){
        GetUserPostCountResponse response = countPostsUseCase.getUserPostCount();
        return ResponseEntity.ok(response);
    }


}
