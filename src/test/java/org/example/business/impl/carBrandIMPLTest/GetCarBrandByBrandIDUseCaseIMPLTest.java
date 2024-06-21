package org.example.business.impl.carBrandIMPLTest;

import org.example.business.impl.carBrandIMPL.GetCarBrandByBrandIDUseCaseIMPL;
import org.example.domain.CarBrand;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCarBrandByBrandIDUseCaseIMPLTest {

    @Mock
    private CarBrandRepository carBrandRepository;

    @InjectMocks
    private GetCarBrandByBrandIDUseCaseIMPL getCarBrandByBrandIDUseCaseIMPL;

    @Test
    void getBrand_shouldReturnBrand_whenBrandExists() {
        // Arrange
        long brandID = 1L;
        CarBrandEntity carBrandEntity = CarBrandEntity.builder()
                .carBrandID(brandID)
                .brandName("Brand 1")
                .build();

        CarBrand expectedCarBrand = CarBrand.builder()
                .brandID(brandID)
                .brandName("Brand 1")
                .build();

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.of(carBrandEntity));

        // Act
        Optional<CarBrand> result = getCarBrandByBrandIDUseCaseIMPL.getBrand(brandID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedCarBrand, result.get());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
    }

    @Test
    void getBrand_shouldReturnEmpty_whenBrandDoesNotExist() {
        // Arrange
        long brandID = 1L;

        when(carBrandRepository.findByCarBrandID(brandID)).thenReturn(Optional.empty());

        // Act
        Optional<CarBrand> result = getCarBrandByBrandIDUseCaseIMPL.getBrand(brandID);

        // Assert
        assertFalse(result.isPresent());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
    }

    @Test
    void getBrand_shouldThrowException_whenRepositoryFails() {
        // Arrange
        long brandID = 1L;
        when(carBrandRepository.findByCarBrandID(brandID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getCarBrandByBrandIDUseCaseIMPL.getBrand(brandID);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carBrandRepository, times(1)).findByCarBrandID(brandID);
    }
}
