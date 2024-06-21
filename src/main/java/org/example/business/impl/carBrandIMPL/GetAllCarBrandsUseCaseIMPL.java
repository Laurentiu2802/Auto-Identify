package org.example.business.impl.carBrandIMPL;

import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsRequest;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsResponse;
import org.example.business.searchCriteria.GetAllCarBrandsUseCase;
import org.example.domain.CarBrand;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCarBrandsUseCaseIMPL implements GetAllCarBrandsUseCase {
    private final CarBrandRepository carBrandRepository;

    @Override
    public GetAllCarBrandsResponse getBrands(GetAllCarBrandsRequest request){
        List<CarBrandEntity> carBrandsResults = carBrandRepository.findAll();

        final GetAllCarBrandsResponse response = new GetAllCarBrandsResponse();
        List<CarBrand> carBrands = carBrandsResults
                .stream()
                .map(CarBrandConverter::convert)
                .toList();
        response.setCarBrands(carBrands);
        return response;
    }
}
