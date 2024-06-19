package org.example.business.impl.categoryIMPL;

import org.example.business.dto.categoryDTO.GetAllCategoriesRequest;
import org.example.business.dto.categoryDTO.GetAllCategoriesResponse;
import org.example.domain.Category;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
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
class GetAllCategoriesUseCaseIMPLTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GetAllCategoriesUseCaseIMPL getAllCategoriesUseCaseIMPL;

    @Test
    void getCategories_shouldReturnCategories_whenCategoriesExist() {
        // Arrange
        GetAllCategoriesRequest request = new GetAllCategoriesRequest();

        CategoryEntity categoryEntity1 = CategoryEntity.builder()
                .categoryID(1L)
                .categoryName("Category 1")
                .build();

        CategoryEntity categoryEntity2 = CategoryEntity.builder()
                .categoryID(2L)
                .categoryName("Category 2")
                .build();

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        // Act
        GetAllCategoriesResponse response = getAllCategoriesUseCaseIMPL.getCategories(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getCategories().size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategories_shouldReturnEmptyList_whenNoCategoriesExist() {
        // Arrange
        GetAllCategoriesRequest request = new GetAllCategoriesRequest();
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetAllCategoriesResponse response = getAllCategoriesUseCaseIMPL.getCategories(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getCategories().isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategories_shouldHandleRepositoryException() {
        // Arrange
        GetAllCategoriesRequest request = new GetAllCategoriesRequest();
        when(categoryRepository.findAll()).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getAllCategoriesUseCaseIMPL.getCategories(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(categoryRepository, times(1)).findAll();
    }
}