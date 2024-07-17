package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        return service.createCategory(newCategory);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Integer catId,
                                      @RequestBody @Valid UpdateCategoryDto updateCategory) {
        return service.updateCategory(catId, updateCategory);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer catId) {
        return service.deleteCategory(catId);
    }
}
