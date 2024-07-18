package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable(name = "catId") Integer catId) {
        return service.getCategory(catId);
    }
}
