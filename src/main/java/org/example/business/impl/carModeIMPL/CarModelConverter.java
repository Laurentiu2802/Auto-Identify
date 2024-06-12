package org.example.business.impl.carModeIMPL;

import org.example.business.impl.carBrandIMPL.CarBrandConverter;
import org.example.domain.CarBrand;
import org.example.domain.CarModel;
import org.example.persistance.entity.CarModelEntity;

public class CarModelConverter {
    private CarModelConverter(){

    }

    public static CarModel convert(CarModelEntity carModel){
        CarBrand carBrand = CarBrandConverter.convert(carModel.getCarBrand());

        return CarModel.builder()
                .modelID(carModel.getModelID())
                .carBrand(carBrand)
                .modelName(carModel.getModelName())
                .build();
    }
}
