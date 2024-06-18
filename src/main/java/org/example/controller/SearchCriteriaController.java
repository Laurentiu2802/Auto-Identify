package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.dto.carBrandDTO.*;
import org.example.business.dto.carModelDTO.*;
import org.example.business.dto.categoryDTO.*;
import org.example.business.searchCriteria.*;
import org.example.domain.CarBrand;
import org.example.domain.CarModel;
import org.example.domain.Category;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.CarModelRepository;
import org.example.persistance.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/search1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SearchCriteriaController {
    @Autowired
    private UpdateCarBrandUseCase updateCarBrandUseCase;


    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;
    private final CategoryRepository categoryRepository;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetAllCarBrandsUseCase getAllCarBrandsUseCase;
    private final GetCarModelsUseCase getCarModelsUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final CreateCarBrandUseCase createCarBrandUseCase;
    private final CreateCarModelUseCase createCarModelUseCase;
    private  final UpdateCategoryUseCase updateCategoryUseCase;
    private final UpdateCarModelUseCase updateCarModelUseCase;
    //private final UpdateCarBrandUseCase updateCarBrandUseCase;
    private final GetCategoryByCategoryIDUseCase getCategoryByCategoryIDUseCase;
    private final GetCarBrandByBrandIDUseCase getCarBrandByBrandIDUseCase;
    private final GetCarModelByModelIDUseCase getCarModelByModelIDUseCase;

    //test for pipeline 2
    @PostMapping("/categoryCreate")
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request){
        CreateCategoryResponse response = createCategoryUseCase.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/brandCreate")
    public ResponseEntity<CreateCarBrandResponse> createCarbrand(@RequestBody @Valid CreateCarBrandRequest request){
        CreateCarBrandResponse response = createCarBrandUseCase.createCarBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/modelCreate")
    public ResponseEntity<CreateCarModelResponse> createCarModel(@RequestBody @Valid CreateCarModelRequest request){
        CreateCarModelResponse response = createCarModelUseCase.createCarModel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{categoryID}")
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryID") long categoryID, @RequestBody @Valid UpdateCategoryRequest request){
        request.setCategoryID(categoryID);
        updateCategoryUseCase.updateCategory(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/brand1/{brandID}")
    public ResponseEntity<Void> updateBrand(@PathVariable("brandID") long brandID, @RequestBody @Valid UpdateCarBrandRequest request){
        request.setBrandID(brandID);
        updateCarBrandUseCase.updateCarBrand(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/model/{modelID}")
    public ResponseEntity<Void> updateModel(@PathVariable("modelID") long modelID, @RequestBody @Valid UpdateCarModelRequest request){
        request.setModelID((modelID));
        updateCarModelUseCase.updateCarModel(request);
        return ResponseEntity.noContent().build();
    }



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

    @GetMapping(path = "category/getID/{categoryID}")
    public ResponseEntity<Category> getCategory(@PathVariable(value = "categoryID") final long categoryID){
        final Optional<Category> categoryOptional = getCategoryByCategoryIDUseCase.getCategory(categoryID);
        if(categoryOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryOptional.get());
    }

    @GetMapping(path = "brand/getID/{brandID}")
    public ResponseEntity<CarBrand> getBrand(@PathVariable(value = "brandID") final long brandID){
        final Optional<CarBrand> brandOptional = getCarBrandByBrandIDUseCase.getBrand(brandID);
        if(brandOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(brandOptional.get());
    }

    @GetMapping(path = "model/getID/{modelID}")
    public ResponseEntity<CarModel> getModel(@PathVariable(value = "modelID") final long modelID){
        final Optional<CarModel> modelOptional = getCarModelByModelIDUseCase.getModel(modelID);
        if(modelOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(modelOptional.get());
    }
}
