package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    List<CategoryDto> getCategories(@RequestParam(required = false, defaultValue = "0") int from,
                                    @RequestParam(required = false, defaultValue = "10") int size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping(value = "/{catId}")
    CategoryDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
