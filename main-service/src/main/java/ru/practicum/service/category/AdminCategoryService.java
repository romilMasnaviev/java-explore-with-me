package ru.practicum.service.category;

import org.springframework.http.HttpStatus;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;

public interface AdminCategoryService {
    CategoryDto createCategoryByAdmin(NewCategoryDto newCategory);

    CategoryDto updateCategoryByAdmin(Integer catId, UpdateCategoryDto updateCategory);

    HttpStatus deleteCategoryByAdmin(Integer catId);
}
