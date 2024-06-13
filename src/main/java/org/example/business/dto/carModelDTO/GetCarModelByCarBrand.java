package org.example.business.dto.carModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCarModelByCarBrand {
//    private Long carModelID;
    private Long carBrandID;
}
