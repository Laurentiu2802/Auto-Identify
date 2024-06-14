package org.example.business.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostByPostIDResponse {
    private Long postID;
    private Long userID;
    private String description;
    private Long categoryID;
    private Long brandID;
    private Long modelID;
}
