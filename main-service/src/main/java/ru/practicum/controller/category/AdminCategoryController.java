package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.model.Category;
import ru.practicum.service.category.AdminCategoryService;

import javax.validation.Valid;

/**
 * Админ Контроллер для {@link Category}
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * Создание категории.
     *
     * @param newCategory информация о новой категории
     * @return созданная категория
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        return service.createCategoryByAdmin(newCategory);
    }

    /**
     * Редактирование категории.
     *
     * @param catId          идентификатор категории
     * @param updateCategory параметры для редактирования категории
     * @return обновленная категория
     */
    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Integer catId,
                                      @RequestBody @Valid UpdateCategoryDto updateCategory) {
        return service.updateCategoryByAdmin(catId, updateCategory);
    }

    /**
     * Удаление категории.
     *
     * @param catId идентификатор категории
     */
    @DeleteMapping("/{catId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer catId) {
        return service.deleteCategoryByAdmin(catId);
    }
}