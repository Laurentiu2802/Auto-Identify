package org.example.business.impl.carModelIMPLTest;

import org.example.business.impl.carModeIMPL.CarModelConverter;
import org.example.business.impl.carModeIMPL.GetCarModelByModelIDUseCaseIMPL;
import org.example.domain.CarModel;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarModelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCarModelByModelIDUseCaseIMPLTest {

    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private GetCarModelByModelIDUseCaseIMPL getCarModelByModelIDUseCaseIMPL;

    @Test
    void getModel_shouldReturnModel_whenModelExists() {
        // Arrange
        long modelID = 1L;
        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(modelID)
                .modelName("Model 1")
                .build();
        CarModel carModel = CarModelConverter.convert(carModelEntity);

        when(carModelRepository.findById(modelID)).thenReturn(Optional.of(carModelEntity));

        // Act
        Optional<CarModel> result = getCarModelByModelIDUseCaseIMPL.getModel(modelID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(carModel, result.get());
        verify(carModelRepository, times(1)).findById(modelID);
    }

    @Test
    void getModel_shouldReturnEmpty_whenModelDoesNotExist() {
        // Arrange
        long modelID = 1L;
        when(carModelRepository.findById(modelID)).thenReturn(Optional.empty());

        // Act
        Optional<CarModel> result = getCarModelByModelIDUseCaseIMPL.getModel(modelID);

        // Assert
        assertFalse(result.isPresent());
        verify(carModelRepository, times(1)).findById(modelID);
    }

    @Test
    void getModel_shouldHandleRepositoryException() {
        // Arrange
        long modelID = 1L;
        when(carModelRepository.findById(modelID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getCarModelByModelIDUseCaseIMPL.getModel(modelID);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carModelRepository, times(1)).findById(modelID);
    }
}