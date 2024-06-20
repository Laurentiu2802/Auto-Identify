package org.example.business.impl.carBrandIMPLTest;

import org.example.business.dto.carBrandDTO.GetAllCarBrandsRequest;
import org.example.business.dto.carBrandDTO.GetAllCarBrandsResponse;
import org.example.business.impl.carBrandIMPL.GetAllCarBrandsUseCaseIMPL;
import org.example.domain.CarBrand;
import org.example.persistance.CarBrandRepository;
import org.example.persistance.entity.CarBrandEntity;
import org.junit.jupiter.api.BeforeEach;
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
class GetAllCarBrandsUseCaseIMPLTest {

    @Mock
    private CarBrandRepository carBrandRepository;

    @InjectMocks
    private GetAllCarBrandsUseCaseIMPL getAllCarBrandsUseCaseIMPL;

    @Test
    void getBrands_shouldReturnAllBrands_whenBrandsExist() {
        // Arrange
        CarBrandEntity carBrandEntity1 = CarBrandEntity.builder()
                .carBrandID(1L)
                .brandName("Brand 1")
                .build();

        CarBrandEntity carBrandEntity2 = CarBrandEntity.builder()
                .carBrandID(2L)
                .brandName("Brand 2")
                .build();

        List<CarBrandEntity> carBrandEntities = List.of(carBrandEntity1, carBrandEntity2);

        when(carBrandRepository.findAll()).thenReturn(carBrandEntities);

        GetAllCarBrandsRequest request = new GetAllCarBrandsRequest();

        // Act
        GetAllCarBrandsResponse response = getAllCarBrandsUseCaseIMPL.getBrands(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getCarBrands().size());
        assertEquals("Brand 1", response.getCarBrands().get(0).getBrandName());
        assertEquals("Brand 2", response.getCarBrands().get(1).getBrandName());
        verify(carBrandRepository, times(1)).findAll();
    }

    @Test
    void getBrands_shouldReturnEmptyList_whenNoBrandsExist() {
        // Arrange
        when(carBrandRepository.findAll()).thenReturn(Collections.emptyList());

        GetAllCarBrandsRequest request = new GetAllCarBrandsRequest();

        // Act
        GetAllCarBrandsResponse response = getAllCarBrandsUseCaseIMPL.getBrands(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getCarBrands().isEmpty());
        verify(carBrandRepository, times(1)).findAll();
    }

    @Test
    void getBrands_shouldThrowException_whenRepositoryFails() {
        // Arrange
        when(carBrandRepository.findAll()).thenThrow(new RuntimeException("Database Error"));

        GetAllCarBrandsRequest request = new GetAllCarBrandsRequest();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getAllCarBrandsUseCaseIMPL.getBrands(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(carBrandRepository, times(1)).findAll();
    }
}
