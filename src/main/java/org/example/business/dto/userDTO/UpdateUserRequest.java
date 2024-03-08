package org.example.business.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    private Long userID;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String description;
}
