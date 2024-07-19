package ru.practicum.service.category;

import ru.practicum.dto.category.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategoriesPublic(Integer from, Integer size);

    CategoryDto getCategory(Integer catId);
}
