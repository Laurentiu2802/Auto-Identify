package org.example.business.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistance.entity.UserRoleEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String description;

    private UserRoleEntity userRole;
}
