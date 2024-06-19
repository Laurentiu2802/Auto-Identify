package org.example.business.impl.carBrandIMPLTest;

import org.example.business.dto.carBrandDTO.CreateCarBrandRequest;
import org.example.business.dto.carBrandDTO.CreateCarBrandResponse;
import org.example.business.impl.carBrandIMPL.CreateCarBrandUseCaseIMPL;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class CreateCarBrandUseCaseIMPLTest {

    @Mock
    private CarBrandRepository carBrandRepository;

    @InjectMocks
    private CreateCarBrandUseCaseIMPL createCarBrandUseCaseIMPL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCarBrand_shouldReturnResponse_whenCarBrandIsCreatedSuccessfully() {
        // Arrange
        CreateCarBrandRequest request = CreateCarBrandRequest.builder()
                .brandName("Test Brand")
                .build();

        CarBrandEntity savedEntity = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Test Brand")
                .build();

        when(carBrandRepository.save(any(CarBrandEntity.class))).thenReturn(savedEntity);

        // Act
        CreateCarBrandResponse response = createCarBrandUseCaseIMPL.createCarBrand(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getBrandID());
        verify(carBrandRepository, times(1)).save(any(CarBrandEntity.class));
    }

    @Test
    void createCarBrand_shouldThrowException_whenRepositoryFails() {
        // Arrange
        CreateCarBrandRequest request = CreateCarBrandRequest.builder()
                .brandName("Test Brand")
                .build();

        when(carBrandRepository.save(any(CarBrandEntity.class))).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createCarBrandUseCaseIMPL.createCarBrand(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carBrandRepository, times(1)).save(any(CarBrandEntity.class));
    }

    @Test
    void createCarBrand_shouldThrowException_whenBrandNameIsNull() {
        // Arrange
        CreateCarBrandRequest request = CreateCarBrandRequest.builder()
                .brandName(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCarBrandUseCaseIMPL.createCarBrand(request);
        });

        assertEquals("Brand name cannot be null or empty", exception.getMessage());
        verify(carBrandRepository, never()).save(any(CarBrandEntity.class));
    }

    @Test
    void createCarBrand_shouldThrowException_whenBrandNameIsEmpty() {
        // Arrange
        CreateCarBrandRequest request = CreateCarBrandRequest.builder()
                .brandName("")
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCarBrandUseCaseIMPL.createCarBrand(request);
        });

        assertEquals("Brand name cannot be null or empty", exception.getMessage());
        verify(carBrandRepository, never()).save(any(CarBrandEntity.class));
    }
}
