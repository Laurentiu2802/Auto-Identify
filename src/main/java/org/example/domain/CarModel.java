package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.persistance.entity.CarBrandEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {
    private Long modelID;
    private String modelName;
    private CarBrand carBrand;
}
