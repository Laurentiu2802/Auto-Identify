package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsRequest;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsResponse;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrand;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrandResponse;
import org.example.business.dto.categoryDTO.GetAllCategoriesRequest;
import org.example.business.dto.categoryDTO.GetAllCategoriesResponse;
import org.example.business.searchCriteria.GetAllCarBrandsUseCase;
import org.example.business.searchCriteria.GetAllCategoriesUseCase;
import org.example.business.searchCriteria.GetCarModelsUseCase;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.CarModelRepository;
import org.example.persistance.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SearchCriteriaController {
    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;
    private final CategoryRepository categoryRepository;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetAllCarBrandsUseCase getAllCarBrandsUseCase;
    private final GetCarModelsUseCase getCarModelsUseCase;

    //test for pipeline

    @GetMapping("/category")
    public ResponseEntity<GetAllCategoriesResponse> getAllCategories(){
        GetAllCategoriesRequest request = GetAllCategoriesRequest.builder().build();
        GetAllCategoriesResponse response = getAllCategoriesUseCase.getCategories(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/brands")
    public ResponseEntity<GetAllCarBrandsResponse> getAllCarBrands(){
        GetAllCarBrandsRequest request = GetAllCarBrandsRequest.builder().build();
        GetAllCarBrandsResponse response = getAllCarBrandsUseCase.getBrands(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "models/{carBrandID}")
    public ResponseEntity<GetCarModelByCarBrandResponse> getCarModels(@PathVariable(value = "carBrandID") long carBrandID){
        GetCarModelByCarBrand request = GetCarModelByCarBrand.builder().carBrandID(carBrandID).build();
        GetCarModelByCarBrandResponse response = getCarModelsUseCase.getCarModels(request);
        return ResponseEntity.ok(response);
    }
}
