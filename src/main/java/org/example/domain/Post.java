package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistance.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long postID;
    private String description;
    private User user;
}
