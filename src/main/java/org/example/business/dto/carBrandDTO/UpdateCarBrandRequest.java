package org.example.business.dto.carBrandDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarBrandRequest {
    private Long brandID;
    @NotBlank(message = "Category name is mandatory!")
    private String brandName;
}
