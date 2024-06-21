package org.example.business.searchCriteria;

import org.example.domain.CarBrand;

import java.util.Optional;

public interface GetCarBrandByBrandIDUseCase {
    Optional<CarBrand> getBrand(long brandID);
}
