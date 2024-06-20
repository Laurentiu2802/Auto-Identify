package org.example.business.impl.carModelIMPLTest;

import org.example.business.dto.carModelDTO.CreateCarModelRequest;
import org.example.business.dto.carModelDTO.CreateCarModelResponse;
import org.example.business.impl.carModeIMPL.CreateCarModelUseCaseIMPL;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.example.persistance.entity.CarModelEntity;
import org.junit.jupiter.api.BeforeEach;
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
    void createCarModel_shouldReturnResponse_whenCarBrandExists() {
        // Arrange
        CreateCarModelRequest request = CreateCarModelRequest.builder()
                .modelName("Test Model")
                .brandID(1L)
                .build();

        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Test Brand")
                .build();

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Test Model")
                .carBrand(carBrandEntity)
                .build();

        when(carBrandRepository.findByCarBrandID(1L)).thenReturn(Optional.of(carBrandEntity));
        when(carModelRepository.save(any(CarModelEntity.class))).thenReturn(carModelEntity);

        // Act
        CreateCarModelResponse response = createCarModelUseCaseIMPL.createCarModel(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getModelID());
        verify(carBrandRepository, times(1)).findByCarBrandID(1L);
        verify(carModelRepository, times(1)).save(any(CarModelEntity.class));
    }

    @Test
    void createCarModel_shouldReturnResponse_whenCarBrandDoesNotExist() {
        // Arrange
        CreateCarModelRequest request = CreateCarModelRequest.builder()
                .modelName("Test Model")
                .brandID(1L)
                .build();

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Test Model")
                .carBrand(null)
                .build();

        when(carBrandRepository.findByCarBrandID(1L)).thenReturn(Optional.empty());
        when(carModelRepository.save(any(CarModelEntity.class))).thenReturn(carModelEntity);

        // Act
        CreateCarModelResponse response = createCarModelUseCaseIMPL.createCarModel(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getModelID());
        verify(carBrandRepository, times(1)).findByCarBrandID(1L);
        verify(carModelRepository, times(1)).save(any(CarModelEntity.class));
    }

    @Test
    void createCarModel_shouldThrowException_whenSaveFails() {
        // Arrange
        CreateCarModelRequest request = CreateCarModelRequest.builder()
                .modelName("Test Model")
                .brandID(1L)
                .build();

        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Test Brand")
                .build();

        when(carBrandRepository.findByCarBrandID(1L)).thenReturn(Optional.of(carBrandEntity));
        when(carModelRepository.save(any(CarModelEntity.class))).thenThrow(new RuntimeException("Save failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> createCarModelUseCaseIMPL.createCarModel(request));
        verify(carBrandRepository, times(1)).findByCarBrandID(1L);
        verify(carModelRepository, times(1)).save(any(CarModelEntity.class));
    }
}
