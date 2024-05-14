package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.dto.userDTO.*;
import org.example.business.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@AllArgsConstructor
public class UserController {
    private final GetUsersUseCase getUsersUseCase;
    private  final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final LogInUseCase logInUseCase;

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers(){
        GetUsersRequest request = GetUsersRequest.builder().build();
        GetUsersResponse response = getUsersUseCase.getUsers(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userID){
        deleteUserUseCase.DeleteUser(userID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id, @RequestBody @Valid UpdateUserRequest request){
        request.setUserID(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/tokens")
    public ResponseEntity<LogInResponse> login(@RequestBody @Valid LogInRequest loginRequest) {
        LogInResponse loginResponse = logInUseCase.loginUser(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
