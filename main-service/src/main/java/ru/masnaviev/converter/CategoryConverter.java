package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.dto.category.CategoryDto;
import ru.masnaviev.dto.category.NewCategoryDto;
import ru.masnaviev.dto.category.UpdateCategoryDto;
import ru.masnaviev.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryConverter {

    Category newCategoryDtoConvertToCategory(NewCategoryDto newCategory);

    CategoryDto categoryConvertToCategoryDto(Category category);

    Category changeCategoryDtoConvertToCategory(UpdateCategoryDto changeDirectory);

    List<CategoryDto> categoryConvertToCategoryDto(List<Category> category);
}
