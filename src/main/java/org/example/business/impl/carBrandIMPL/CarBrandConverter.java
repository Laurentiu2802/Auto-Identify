package org.example.business.impl.carBrandIMPL;

import org.example.business.impl.categoryIMPL.CategoryConverter;
import org.example.domain.CarBrand;
import org.example.persistance.entity.CarBrandEntity;

public class CarBrandConverter {
    private CarBrandConverter() {

    }

    public static CarBrand convert(CarBrandEntity carBrand){
        if(carBrand == null){
            return null;
        }
        return CarBrand.builder()
                .brandID(carBrand.getBrandID())
                .brandName(carBrand.getBrandName())
                .build();
    }
}
