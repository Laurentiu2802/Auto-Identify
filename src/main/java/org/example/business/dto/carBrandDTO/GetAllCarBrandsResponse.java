package org.example.business.dto.carBrandDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.CarBrand;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarBrandsResponse {
    private List<CarBrand> carBrands;
}
