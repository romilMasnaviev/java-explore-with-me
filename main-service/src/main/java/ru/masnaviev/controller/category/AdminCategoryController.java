package ru.masnaviev.controller.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.dto.category.CategoryDto;
import ru.masnaviev.dto.category.NewCategoryDto;
import ru.masnaviev.dto.category.UpdateCategoryDto;
import ru.masnaviev.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryController {

    private CategoryService service;

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        return service.createCategory(newCategory);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable @Min(value = 0) Integer catId,
                                      @RequestBody @Valid UpdateCategoryDto updateCategory) {
        return service.updateCategory(catId, updateCategory);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable @Min(value = 0) Integer catId) {
        return service.deleteCategory(catId);
    }
}
