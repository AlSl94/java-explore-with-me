package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto category) {
        CategoryDto dto = categoryService.updateCategory(category);
        log.info("Updated category: {}", category);
        return dto;
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody NewCategoryDto category) {
        CategoryDto dto = categoryService.createCategory(category);
        log.info("Created category: {}", category);
        return dto;
    }

    @DeleteMapping(value = "/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
        log.info("Deleted category: {}", catId);
    }

}
