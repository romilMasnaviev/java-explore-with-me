package ru.practicum.service.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;

public interface AdminCategoryService {
    ResponseEntity<CategoryDto> createCategoryByAdmin(NewCategoryDto newCategory);

    CategoryDto updateCategoryByAdmin(Integer catId, UpdateCategoryDto updateCategory);

    ResponseEntity<HttpStatus> deleteCategoryByAdmin(Integer catId);
}
