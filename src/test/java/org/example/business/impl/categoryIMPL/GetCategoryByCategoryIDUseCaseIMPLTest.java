package org.example.business.impl.categoryIMPL;

import org.example.domain.Category;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCategoryByCategoryIDUseCaseIMPLTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GetCategoryByCategoryIDUseCaseIMPL getCategoryByCategoryIDUseCaseIMPL;

    @Test
    void getCategory_shouldReturnCategory_whenCategoryExists() {
        // Arrange
        long categoryID = 1L;
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryID(categoryID)
                .categoryName("Test Category")
                .build();
        when(categoryRepository.findByCategoryID(categoryID)).thenReturn(Optional.of(categoryEntity));

        // Act
        Optional<Category> result = getCategoryByCategoryIDUseCaseIMPL.getCategory(categoryID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Category", result.get().getCategoryName());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
    }

    @Test
    void getCategory_shouldReturnEmptyOptional_whenCategoryDoesNotExist() {
        // Arrange
        long categoryID = 1L;
        when(categoryRepository.findByCategoryID(categoryID)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = getCategoryByCategoryIDUseCaseIMPL.getCategory(categoryID);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
    }

    @Test
    void getCategory_shouldHandleRepositoryException() {
        // Arrange
        long categoryID = 1L;
        when(categoryRepository.findByCategoryID(categoryID)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getCategoryByCategoryIDUseCaseIMPL.getCategory(categoryID);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
    }
}