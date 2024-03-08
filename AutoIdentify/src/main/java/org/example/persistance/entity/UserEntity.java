package org.example.persistance.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserEntity {
    private Long userID;
    private  String username;
    private  String password;
    private String description;
}
