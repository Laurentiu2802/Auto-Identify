package org.example.business.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsByPostIDRequest {
    private Long postID;
    private Long description;
    private Long userID;
}
