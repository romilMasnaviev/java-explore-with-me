package ru.practicum.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("categoryConverter")
public interface CategoryConverter {

    Category newCategoryDtoConvertToCategory(NewCategoryDto newCategory);

    CategoryDto categoryConvertToCategoryDto(Category category);

    Category changeCategoryDtoConvertToCategory(UpdateCategoryDto changeDirectory);

    List<CategoryDto> categoryConvertToCategoryDto(List<Category> category);
}
