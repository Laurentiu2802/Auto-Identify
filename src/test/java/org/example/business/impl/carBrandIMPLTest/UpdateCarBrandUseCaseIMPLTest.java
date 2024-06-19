package org.example.business.impl.carBrandIMPLTest;

import org.example.business.dto.carBrandDTO.UpdateCarBrandRequest;
import org.example.business.impl.carBrandIMPL.UpdateCarBrandUseCaseIMPL;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCarBrandUseCaseIMPLTest {

    @Mock
    private CarBrandRepository carBrandRepository;

    @InjectMocks
    private UpdateCarBrandUseCaseIMPL updateCarBrandUseCaseIMPL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCarBrand_shouldUpdate_whenBrandExists() {
        // Arrange
        long brandID = 1L;
        UpdateCarBrandRequest request = new UpdateCarBrandRequest();
        request.setBrandID(brandID);
        request.setBrandName("Updated Brand");

        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(brandID)
                .brandName("Old Brand")
                .build();

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.of(carBrandEntity));

        // Act
        updateCarBrandUseCaseIMPL.updateCarBrand(request);

        // Assert
        assertEquals("Updated Brand", carBrandEntity.getBrandName());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carBrandRepository, times(1)).save(carBrandEntity);
    }

    @Test
    void updateCarBrand_shouldThrowException_whenBrandDoesNotExist() {
        // Arrange
        long brandID = 1L;
        UpdateCarBrandRequest request = new UpdateCarBrandRequest();
        request.setBrandID(brandID);
        request.setBrandName("Updated Brand");

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            updateCarBrandUseCaseIMPL.updateCarBrand(request);
        });

        assertEquals("Car brand not found with ID: " + brandID, exception.getMessage());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carBrandRepository, never()).save(any(CarBrandEntity.class));
    }

    @Test
    void updateCarBrand_shouldThrowException_whenRepositoryFails() {
        // Arrange
        long brandID = 1L;
        UpdateCarBrandRequest request = new UpdateCarBrandRequest();
        request.setBrandID(brandID);
        request.setBrandName("Updated Brand");

        when(carBrandRepository.findByCarBrandID(brandID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateCarBrandUseCaseIMPL.updateCarBrand(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
        verify(carBrandRepository, never()).save(any(CarBrandEntity.class));
    }
}