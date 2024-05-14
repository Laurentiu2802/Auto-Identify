package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistance.entity.RoleEnum;
import org.example.persistance.entity.UserRoleEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userID;
    private  String username;
    private String description;

    @JsonIgnore
    private  String password;
    private UserRoleEntity userRole;

}
