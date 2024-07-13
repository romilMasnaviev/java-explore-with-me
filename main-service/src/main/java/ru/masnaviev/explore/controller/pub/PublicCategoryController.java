package ru.masnaviev.explore.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.service.CategoryService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    public CategoryService service;

    @GetMapping()
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable(name = "catId") @Min(0) Integer catId) {
        return service.getCategory(catId);
    }
}
