package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.dto.category.NewCategoryDto;
import ru.masnaviev.explore.dto.category.UpdateCategoryDto;
import ru.masnaviev.explore.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryConverter {

    Category newCategoryDtoConvertToCategory(NewCategoryDto newCategory);

    CategoryDto categoryConvertToCategoryDto(Category category);

    Category changeCategoryDtoConvertToCategory(UpdateCategoryDto changeDirectory);

    List<CategoryDto> categoryConvertToCategoryDto(List<Category> category);
}
