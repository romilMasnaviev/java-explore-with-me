package ru.masnaviev.explore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.CategoryConverter;
import ru.masnaviev.explore.dao.CategoryRepository;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.dto.category.NewCategoryDto;
import ru.masnaviev.explore.dto.category.UpdateCategoryDto;
import ru.masnaviev.explore.model.Category;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private CategoryRepository repository;
    private CategoryConverter converter;

    public ResponseEntity<CategoryDto> createCategory(NewCategoryDto newCategory) {
        log.debug("Создание категории {}", newCategory);
        Category category = converter.newCategoryDtoConvertToCategory(newCategory);
        Category savedCategory = repository.save(category);
        return new ResponseEntity<>(converter.categoryConvertToCategoryDto(savedCategory), HttpStatus.CREATED);
    }

    public CategoryDto updateCategory(Integer catId, UpdateCategoryDto updateCategory) {
        log.debug("Изменение категории {}, catId = {}", updateCategory, catId);
        Category category = converter.changeCategoryDtoConvertToCategory(updateCategory);
        category.setId(catId);
        if (!repository.existsById(catId)) {
            throw new EntityNotFoundException("Категории с id=" + catId + " не существует");
        }
        Category updatedCategory = repository.save(category);
        return converter.categoryConvertToCategoryDto(updatedCategory);
    }

    public ResponseEntity<HttpStatus> deleteCategory(Integer catId) {
        log.debug("Удаление категории, catId = {}", catId);
        repository.deleteById(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        log.debug("Получение категорий, from ={}, size = {}", from, size);
        List<Category> categories = repository.findAllCategories(from, size);
        return converter.categoryConvertToCategoryDto(categories);
    }

    public CategoryDto getCategory(Integer catId) {
        log.debug("Получение категории, catId ={}", catId);
        return converter.categoryConvertToCategoryDto(repository.getReferenceById(catId));
    }
}
