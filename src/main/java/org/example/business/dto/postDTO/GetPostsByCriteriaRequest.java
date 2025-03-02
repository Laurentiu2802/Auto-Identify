package org.example.business.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsByCriteriaRequest {
    private Long postID;
    private Long categoryID;
    private Long carBrandID;
    private Long carModelID;
}
