package org.example.business.impl.carModelIMPLTest;


import org.example.business.dto.carModelDTO.UpdateCarModelRequest;
import org.example.business.impl.carModeIMPL.UpdateCarModelIMPL;
import org.example.persistance.CarModelRepository;
import org.example.persistance.entity.CarModelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCarModelIMPLTest {

    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private UpdateCarModelIMPL updateCarModelIMPL;

    @Test
    void updateCarModel_shouldUpdate_whenModelExists() {
        // Arrange
        long modelID = 1L;
        UpdateCarModelRequest request = new UpdateCarModelRequest();
        request.setModelID(modelID);
        request.setModelName("Updated Model");

        CarModelEntity carModelEntity = CarModelEntity.builder()
                .modelID(modelID)
                .modelName("Old Model")
                .build();

        when(carModelRepository.findById(modelID)).thenReturn(Optional.of(carModelEntity));

        // Act
        updateCarModelIMPL.updateCarModel(request);

        // Assert
        assertEquals("Updated Model", carModelEntity.getModelName());
        verify(carModelRepository, times(1)).findById(modelID);
        verify(carModelRepository, times(1)).save(carModelEntity);
    }

    @Test
    void updateCarModel_shouldThrowException_whenModelDoesNotExist() {
        // Arrange
        long modelID = 1L;
        UpdateCarModelRequest request = new UpdateCarModelRequest();
        request.setModelID(modelID);
        request.setModelName("Updated Model");

        when(carModelRepository.findById(modelID)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            updateCarModelIMPL.updateCarModel(request);
        });

        assertEquals("Car model not found with ID: " + modelID, exception.getMessage());
        verify(carModelRepository, times(1)).findById(modelID);
        verify(carModelRepository, never()).save(any(CarModelEntity.class));
    }

    @Test
    void updateCarModel_shouldHandleRepositoryException() {
        // Arrange
        long modelID = 1L;
        UpdateCarModelRequest request = new UpdateCarModelRequest();
        request.setModelID(modelID);
        request.setModelName("Updated Model");

        when(carModelRepository.findById(modelID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateCarModelIMPL.updateCarModel(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carModelRepository, times(1)).findById(modelID);
        verify(carModelRepository, never()).save(any(CarModelEntity.class));
    }
}
