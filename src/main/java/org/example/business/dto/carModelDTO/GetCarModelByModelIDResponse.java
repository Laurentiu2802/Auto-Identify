package org.example.business.dto.carModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCarModelByModelIDResponse {
    private Long modelID;
    private String modelName;
    private Long brandID;
}
