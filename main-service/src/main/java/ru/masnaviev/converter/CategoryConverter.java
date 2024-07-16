package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.dto.category.CategoryDto;
import ru.masnaviev.dto.category.NewCategoryDto;
import ru.masnaviev.dto.category.UpdateCategoryDto;
import ru.masnaviev.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("categoryConverter")
public interface CategoryConverter {

    Category newCategoryDtoConvertToCategory(NewCategoryDto newCategory);

    CategoryDto categoryConvertToCategoryDto(Category category);

    Category changeCategoryDtoConvertToCategory(UpdateCategoryDto changeDirectory);

    List<CategoryDto> categoryConvertToCategoryDto(List<Category> category);
}
