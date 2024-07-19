package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.model.Category;
import ru.practicum.service.CategoryService;

import java.util.List;

/**
 * Публичный Контроллер для {@link Category}
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class PublicCategoryController {

    private final CategoryService service;

    /**
     * Получение списка категорий (публичное).
     *
     * @param from индекс начала выборки
     * @param size размер выборки
     * @return список категорий
     */
    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getCategoriesPublic(from, size);
    }

    /**
     * Получение категории по идентификатору (публичное).
     *
     * @param catId идентификатор категории
     * @return категория
     */
    @GetMapping("/{catId}")
    public CategoryDto getCategoryPublic(@PathVariable(name = "catId") Integer catId) {
        return service.getCategory(catId);
    }
}
