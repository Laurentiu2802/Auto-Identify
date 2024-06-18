package org.example.business.dto.carBrandDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCarBrandByBrandIDRequest {
    private Long brandID;
}
