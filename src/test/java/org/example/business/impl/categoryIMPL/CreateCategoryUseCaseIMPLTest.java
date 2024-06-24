package org.example.business.impl.categoryIMPL;

import org.example.business.dto.categoryDTO.CreateCategoryRequest;
import org.example.business.dto.categoryDTO.CreateCategoryResponse;
import org.example.persistance.CategoryRepository;
import org.example.persistance.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseIMPLTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateCategoryUseCaseIMPL createCategoryUseCaseIMPL;

    @Test
    void createCategory_shouldCreate_whenRequestIsValid() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setCategoryName("Test Category");

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryID(1L)
                .categoryName("Test Category")
                .build();

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        CreateCategoryResponse response = createCategoryUseCaseIMPL.createCategory(request);

        assertNotNull(response);
        assertEquals(1L, response.getCategoryID());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void createCategory_shouldHandleRepositoryException() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setCategoryName("Test Category");

        when(categoryRepository.save(any(CategoryEntity.class))).thenThrow(new RuntimeException("Database Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createCategoryUseCaseIMPL.createCategory(request);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }
    @Test
    void createCategory_shouldReturnNull_whenCategoryNameIsEmpty() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setCategoryName("");

        CreateCategoryResponse response = createCategoryUseCaseIMPL.createCategory(request);

        assertNull(response);
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void createCategory_shouldHandleNullSavedCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setCategoryName("Test Category");

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(null);

        CreateCategoryResponse response = createCategoryUseCaseIMPL.createCategory(request);

        assertNull(response);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

}
