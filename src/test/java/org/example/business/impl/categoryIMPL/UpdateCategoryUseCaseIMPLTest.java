package org.example.business.impl.categoryIMPL;

import org.example.business.dto.categoryDTO.UpdateCategoryRequest;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
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
class UpdateCategoryUseCaseIMPLTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UpdateCategoryUseCaseIMPL updateCategoryUseCaseIMPL;

    @Test
    void updateCategory_shouldUpdate_whenCategoryExists() {
        long categoryID = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setCategoryID(categoryID);
        request.setCategoryName("Updated Category");

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryID(categoryID)
                .categoryName("Old Category")
                .build();

        when(categoryRepository.findByCategoryID(categoryID)).thenReturn(Optional.of(categoryEntity));

        updateCategoryUseCaseIMPL.updateCategory(request);

        assertEquals("Updated Category", categoryEntity.getCategoryName());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    void updateCategory_shouldThrowException_whenCategoryDoesNotExist() {
        long categoryID = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setCategoryID(categoryID);
        request.setCategoryName("Updated Category");

        when(categoryRepository.findByCategoryID(categoryID)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            updateCategoryUseCaseIMPL.updateCategory(request);
        });

        assertEquals("Category not found with ID: " + categoryID, exception.getMessage());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void updateCategory_shouldHandleRepositoryException() {
        long categoryID = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setCategoryID(categoryID);
        request.setCategoryName("Updated Category");

        when(categoryRepository.findByCategoryID(categoryID)).thenThrow(new RuntimeException("Database Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateCategoryUseCaseIMPL.updateCategory(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(categoryRepository, times(1)).findByCategoryID(categoryID);
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }
}