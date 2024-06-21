package org.example.business.impl.carModelIMPLTest;

import org.example.business.dto.carModelDTO.GetCarModelByCarBrand;
import org.example.business.dto.carModelDTO.GetCarModelByCarBrandResponse;
import org.example.business.impl.carModeIMPL.GetAllCarModelUseCaseIMPL;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarModelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCarModelUseCaseIMPLTest {

    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private GetAllCarModelUseCaseIMPL getAllCarModelUseCaseIMPL;

    @Test
    void getCarModels_shouldReturnModels_whenModelsExist() {
        // Arrange
        long carBrandID = 1L;
        GetCarModelByCarBrand request = GetCarModelByCarBrand.builder()
                .brandID(carBrandID)
                .build();

        CarModelEntity carModelEntity1 = CarModelEntity.builder()
                .modelID(1L)
                .modelName("Model 1")
                .build();
        CarModelEntity carModelEntity2 = CarModelEntity.builder()
                .modelID(2L)
                .modelName("Model 2")
                .build();

        when(carModelRepository.findAllByCarBrandId(carBrandID)).thenReturn(List.of(carModelEntity1, carModelEntity2));

        // Act
        GetCarModelByCarBrandResponse response = getAllCarModelUseCaseIMPL.getCarModels(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getCarModels().size());
        verify(carModelRepository, times(1)).findAllByCarBrandId(carBrandID);
    }

    @Test
    void getCarModels_shouldReturnEmptyList_whenNoModelsExist() {
        // Arrange
        long carBrandID = 1L;
        GetCarModelByCarBrand request = GetCarModelByCarBrand.builder()
                .brandID(carBrandID)
                .build();

        when(carModelRepository.findAllByCarBrandId(carBrandID)).thenReturn(Collections.emptyList());

        // Act
        GetCarModelByCarBrandResponse response = getAllCarModelUseCaseIMPL.getCarModels(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getCarModels().isEmpty());
        verify(carModelRepository, times(1)).findAllByCarBrandId(carBrandID);
    }

    @Test
    void getCarModels_shouldHandleRepositoryException() {
        // Arrange
        long carBrandID = 1L;
        GetCarModelByCarBrand request = GetCarModelByCarBrand.builder()
                .brandID(carBrandID)
                .build();

        when(carModelRepository.findAllByCarBrandId(carBrandID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getAllCarModelUseCaseIMPL.getCarModels(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carModelRepository, times(1)).findAllByCarBrandId(carBrandID);
    }
}