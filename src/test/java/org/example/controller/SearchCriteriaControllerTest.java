package org.example.controller;

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
import org.example.persistance.entity.CarBrandEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SearchCriteriaController.class)
@Import({TestSecurityConfig.class})
public class SearchCriteriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarBrandRepository carBrandRepository;

    @MockBean
    private CarModelRepository carModelRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private GetAllCategoriesUseCase getAllCategoriesUseCase;

    @MockBean
    private GetAllCarBrandsUseCase getAllCarBrandsUseCase;

    @MockBean
    private GetCarModelsUseCase getCarModelsUseCase;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private CreateCarBrandUseCase createCarBrandUseCase;

    @MockBean
    private CreateCarModelUseCase createCarModelUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private UpdateCarModelUseCase updateCarModelUseCase;

    @MockBean
    private UpdateCarBrandUseCase updateCarBrandUseCase;

    @MockBean
    private GetCategoryByCategoryIDUseCase getCategoryByCategoryIDUseCase;

    @MockBean
    private GetCarBrandByBrandIDUseCase getCarBrandByBrandIDUseCase;

    @MockBean
    private GetCarModelByModelIDUseCase getCarModelByModelIDUseCase;


    @Test
    void createCategory_shouldReturnCreatedCategory() throws Exception {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .categoryName("Test Category")
                .build();

        CreateCategoryResponse response = CreateCategoryResponse.builder()
                .categoryID(1L)
                .build();

        when(createCategoryUseCase.createCategory(ArgumentMatchers.any(CreateCategoryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/search1/categoryCreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"categoryName\":\"Test Category\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryID").value(1L));
    }

    @Test
    void createCarBrand_shouldReturnCreatedCarBrand() throws Exception {
        CreateCarBrandRequest request = CreateCarBrandRequest.builder()
                .brandName("Test Brand")
                .build();

        CreateCarBrandResponse response = CreateCarBrandResponse.builder()
                .brandID(1L)
                .build();

        when(createCarBrandUseCase.createCarBrand(ArgumentMatchers.any(CreateCarBrandRequest.class))).thenReturn(response);

        mockMvc.perform(post("/search1/brandCreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brandName\":\"Test Brand\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brandID").value(1L));
    }

    @Test
    void createCarModel_shouldReturnCreatedCarModel() throws Exception {
        // Mock CarBrandEntity
        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Test Brand")
                .build();

        // CreateCarModelRequest expects a CarBrandEntity
        CreateCarModelRequest request = CreateCarModelRequest.builder()
                .modelName("Test Model")
                .brandID(carBrandEntity.getCarBrandID())
                .build();

        CreateCarModelResponse response = CreateCarModelResponse.builder()
                .modelID(1L)
                .build();

        when(createCarModelUseCase.createCarModel(ArgumentMatchers.any(CreateCarModelRequest.class))).thenReturn(response);

        mockMvc.perform(post("/search1/modelCreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"modelName\":\"Test Model\",\"brand\":{\"carBrandID\":1,\"brandName\":\"Test Brand\"}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modelID").value(1L));
    }

    @Test
    void getAllCategories_shouldReturnCategories() throws Exception {
        GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        response.setCategories(List.of(
                Category.builder().categoryID(1L).categoryName("Category1").build(),
                Category.builder().categoryID(2L).categoryName("Category2").build()
        ));

        when(getAllCategoriesUseCase.getCategories(ArgumentMatchers.any(GetAllCategoriesRequest.class))).thenReturn(response);

        mockMvc.perform(get("/search1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andExpect(jsonPath("$.categories[0].categoryID").value(1L))
                .andExpect(jsonPath("$.categories[0].categoryName").value("Category1"))
                .andExpect(jsonPath("$.categories[1].categoryID").value(2L))
                .andExpect(jsonPath("$.categories[1].categoryName").value("Category2"));
    }

    @Test
    void getAllCarBrands_shouldReturnCarBrands() throws Exception {
        GetAllCarBrandsResponse response = new GetAllCarBrandsResponse();
        response.setCarBrands(List.of(
                CarBrand.builder().brandID(1L).brandName("Brand1").build(),
                CarBrand.builder().brandID(2L).brandName("Brand2").build()
        ));

        when(getAllCarBrandsUseCase.getBrands(ArgumentMatchers.any(GetAllCarBrandsRequest.class))).thenReturn(response);

        mockMvc.perform(get("/search1/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrands", hasSize(2)))
                .andExpect(jsonPath("$.carBrands[0].brandID").value(1L))
                .andExpect(jsonPath("$.carBrands[0].brandName").value("Brand1"))
                .andExpect(jsonPath("$.carBrands[1].brandID").value(2L))
                .andExpect(jsonPath("$.carBrands[1].brandName").value("Brand2"));
    }

    @Test
    void getCarModels_shouldReturnCarModels() throws Exception {
        GetCarModelByCarBrandResponse response = new GetCarModelByCarBrandResponse();
        response.setCarModels(List.of(
                CarModel.builder().modelID(1L).modelName("Model1").build(),
                CarModel.builder().modelID(2L).modelName("Model2").build()
        ));

        when(getCarModelsUseCase.getCarModels(ArgumentMatchers.any(GetCarModelByCarBrand.class))).thenReturn(response);

        mockMvc.perform(get("/search1/models/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carModels", hasSize(2)))
                .andExpect(jsonPath("$.carModels[0].modelID").value(1L))
                .andExpect(jsonPath("$.carModels[0].modelName").value("Model1"))
                .andExpect(jsonPath("$.carModels[1].modelID").value(2L))
                .andExpect(jsonPath("$.carModels[1].modelName").value("Model2"));
    }

    @Test
    void updateCategory_shouldUpdateCategory() throws Exception {
        mockMvc.perform(put("/search1/updatecategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"categoryName\":\"Updated Category\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCarBrand_shouldUpdateCarBrand() throws Exception {
        mockMvc.perform(put("/search1/brand1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brandName\":\"Updated Brand\"}"))
                .andExpect(status().isOk());
        //verify(updateCarBrandUseCase, times(1)).updateCarBrand(ArgumentMatchers.any(UpdateCarBrandRequest.class));
    }

    @Test
    void updateCarModel_shouldUpdateCarModel() throws Exception {
        mockMvc.perform(put("/search1/model/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"modelName\":\"Updated Model\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getCategory_WhenCategoryFound_ShouldReturnCategory() throws Exception {
        long categoryID = 1L;
        Category mockCategory = new Category(categoryID, "Fun facts");
        Mockito.when(getCategoryByCategoryIDUseCase.getCategory(categoryID))
                .thenReturn(Optional.of(mockCategory));

        mockMvc.perform(get("/search1/category/getID/{categoryID}", categoryID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryID").value(categoryID))
                .andExpect(jsonPath("$.categoryName").value("Fun facts"));
    }

    @Test
    void getCategory_WhenCategoryNotFound_ShouldReturnNotFound() throws Exception {
        long categoryID = 1L;
        Mockito.when(getCategoryByCategoryIDUseCase.getCategory(categoryID))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/search1/category/getID/{categoryID}", categoryID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBrand_WhenBrandFound_ShouldReturnBrand() throws Exception {
        long brandID = 1L;
        CarBrand mockBrand = new CarBrand(brandID, "Toyota");
        Mockito.when(getCarBrandByBrandIDUseCase.getBrand(brandID))
                .thenReturn(Optional.of(mockBrand));

        mockMvc.perform(get("/search1/brand/getID/{brandID}", brandID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandID").value(brandID))
                .andExpect(jsonPath("$.brandName").value("Toyota"));
    }

    @Test
    void getBrand_WhenBrandNotFound_ShouldReturnNotFound() throws Exception {
        long brandID = 1L;
        Mockito.when(getCarBrandByBrandIDUseCase.getBrand(brandID))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/search1/brand/getID/{brandID}", brandID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getModel_WhenModelFound_ShouldReturnModel() throws Exception {
        long modelID = 1L;
        long brandID = 1L;
        CarBrand mockBrand = new CarBrand(brandID, "Toyota");
        CarModel mockModel = new CarModel(modelID, "Corolla", mockBrand);

        Mockito.when(getCarModelByModelIDUseCase.getModel(modelID))
                .thenReturn(Optional.of(mockModel));

        mockMvc.perform(get("/search1/model/getID/{modelID}", modelID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelID").value(modelID))
                .andExpect(jsonPath("$.modelName").value("Corolla"));
                //.andExpect(jsonPath("$.brandName").value("Toyota"));  // Assuming there is a brandName field you want to test
    }

    @Test
    void getModel_WhenModelNotFound_ShouldReturnNotFound() throws Exception {
        long modelID = 1L;
        Mockito.when(getCarModelByModelIDUseCase.getModel(modelID))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/search1/model/getID/{modelID}", modelID))
                .andExpect(status().isNotFound());
    }

}
