package org.example.business.searchCriteria;

import org.example.domain.CarBrand;
import org.example.domain.Post;

import java.util.Optional;

public interface GetCarBrandByBrandIDUseCase {
    Optional<CarBrand> getBrand(long brandID);
}
