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
    private Category category;
    private CarBrand carBrand;
    private CarModel carModel;
    private User user;
    private Long likes;
}
