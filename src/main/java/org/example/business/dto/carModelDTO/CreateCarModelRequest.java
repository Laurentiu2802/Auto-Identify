package org.example.business.dto.carModelDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarModelRequest {
    private Long brandID;
    @NotBlank
    private String modelName;

}
