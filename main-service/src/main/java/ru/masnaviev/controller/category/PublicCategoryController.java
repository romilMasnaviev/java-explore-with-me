package ru.masnaviev.controller.category;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.dto.category.CategoryDto;
import ru.masnaviev.service.CategoryService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    public CategoryService service;

    @GetMapping()
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") @Min(value = 0) Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") @Min(value = 1) Integer size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable(name = "catId") @Min(value = 0) Integer catId) {
        return service.getCategory(catId);
    }
}
