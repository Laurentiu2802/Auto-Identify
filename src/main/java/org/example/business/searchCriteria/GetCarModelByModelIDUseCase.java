package org.example.business.searchCriteria;

import org.example.domain.CarModel;
import java.util.Optional;

public interface GetCarModelByModelIDUseCase {
    Optional<CarModel> getModel(long modelID);
}
