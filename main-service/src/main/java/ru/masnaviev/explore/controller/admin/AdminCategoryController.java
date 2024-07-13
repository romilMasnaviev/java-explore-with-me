package ru.masnaviev.explore.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.dto.category.ChangeDirectoryDto;
import ru.masnaviev.explore.dto.category.NewCategoryDto;
import ru.masnaviev.explore.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryController {

    private CategoryService service;

    @PostMapping()
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        return service.createCategory(newCategory);
    }

    @PatchMapping("/{catId}")
    public CategoryDto changeCategory(@PathVariable @Min(value = 0) Integer catId,
                                      @RequestBody @Valid ChangeDirectoryDto changeDirectory) {
        return service.changeCategory(catId, changeDirectory);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable @Min(value = 0) Integer catId){
        return service.deleteCategory(catId);
    }
}
