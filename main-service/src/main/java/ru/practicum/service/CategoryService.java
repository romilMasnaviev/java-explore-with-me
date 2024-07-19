package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.CategoryRepository;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.dto.converter.CategoryConverter;
import ru.practicum.model.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryConverter converter;

    @Transactional
    public ResponseEntity<CategoryDto> createCategoryByAdmin(NewCategoryDto newCategory) {
        log.debug("Создание категории {}", newCategory);
        Category category = converter.newCategoryDtoConvertToCategory(newCategory);
        Category savedCategory = repository.save(category);
        return new ResponseEntity<>(converter.categoryConvertToCategoryDto(savedCategory), HttpStatus.CREATED);
    }

    @Transactional
    public CategoryDto updateCategoryByAdmin(Integer catId, UpdateCategoryDto updateCategory) {
        log.debug("Изменение категории {}, catId = {}", updateCategory, catId);
        Category category = converter.changeCategoryDtoConvertToCategory(updateCategory);
        category.setId(catId);
        Category updatedCategory = repository.save(category);
        return converter.categoryConvertToCategoryDto(updatedCategory);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteCategoryByAdmin(Integer catId) {
        log.debug("Удаление категории, catId = {}", catId);
        repository.deleteById(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<CategoryDto> getCategoriesPublic(Integer from, Integer size) {
        log.debug("Получение категорий, from ={}, size = {}", from, size);
        Pageable pageable = PageRequest.of(from, size);
        List<Category> categories = repository.findAll(pageable).toList();
        return converter.categoryConvertToCategoryDto(categories);
    }

    public CategoryDto getCategory(Integer catId) {
        log.debug("Получение категории, catId ={}", catId);
        return converter.categoryConvertToCategoryDto(repository.getReferenceById(catId));
    }
}
