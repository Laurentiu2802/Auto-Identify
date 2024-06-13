package org.example.business.dto.commentDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    @NotBlank
    private String description;

    @NotNull
    private Long userID;

    @NotNull
    private Long postID;

}
