package org.example.business.impl.carModelIMPLTest;


import org.example.business.dto.carModelDTO.CreateCarModelRequest;
import org.example.business.dto.carModelDTO.CreateCarModelResponse;
import org.example.business.impl.carModeIMPL.CreateCarModelUseCaseIMPL;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.example.persistance.entity.CarModelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCarModelUseCaseIMPLTest {

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarBrandRepository carBrandRepository;

    @InjectMocks
    private CreateCarModelUseCaseIMPL createCarModelUseCaseIMPL;

    @Test
    void createCarModel_shouldCreate_whenBrandExists() {
        // Arrange
        long brandID = 1L;
        CreateCarModelRequest request = new CreateCarModelRequest();
        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(brandID)
                .brandName("Test Brand")
                .build();
        request.setModelName("Test Model");
        request.setBrand(carBrandEntity);

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Test Model")
                .carBrand(carBrandEntity)
                .build();

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.of(carBrandEntity));
        when(carModelRepository.save(any(CarModelEntity.class))).thenReturn(carModelEntity);

        // Act
        CreateCarModelResponse response = createCarModelUseCaseIMPL.createCarModel(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getModelID());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carModelRepository, times(1)).save(any(CarModelEntity.class));
    }

    @Test
    void createCarModel_shouldReturnNullBrand_whenBrandDoesNotExist() {
        // Arrange
        long brandID = 1L;
        CreateCarModelRequest request = new CreateCarModelRequest();
        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(brandID)
                .brandName("Test Brand")
                .build();
        request.setModelName("Test Model");
        request.setBrand(carBrandEntity);

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Test Model")
                .carBrand(null)
                .build();

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.empty());
        when(carModelRepository.save(any(CarModelEntity.class))).thenReturn(carModelEntity);

        // Act
        CreateCarModelResponse response = createCarModelUseCaseIMPL.createCarModel(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getModelID());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carModelRepository, times(1)).save(any(CarModelEntity.class));
    }

    @Test
    void createCarModel_shouldThrowException_whenRepositoryFails() {
        // Arrange
        long brandID = 1L;
        CreateCarModelRequest request = new CreateCarModelRequest();
        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(brandID)
                .brandName("Test Brand")
                .build();
        request.setModelName("Test Model");
        request.setBrand(carBrandEntity);

        when(carBrandRepository.findByCarBrandID(brandID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createCarModelUseCaseIMPL.createCarModel(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carModelRepository, never()).save(any(CarModelEntity.class));
    }
}