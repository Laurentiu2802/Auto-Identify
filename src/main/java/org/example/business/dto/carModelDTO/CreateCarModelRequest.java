package org.example.business.dto.carModelDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistance.entity.CarBrandEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarModelRequest {
    private CarBrandEntity brand;
    @NotBlank
    private String modelName;

}
