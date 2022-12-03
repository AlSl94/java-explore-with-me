package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    List<CategoryDto> findCategories(@RequestParam(required = false, defaultValue = "0") int from,
                                     @RequestParam(required = false, defaultValue = "10") int size) {
        List<CategoryDto> categories = categoryService.findCategories(from, size);
        log.info("Got categories list, {}", categories.toString());
        return categories;
    }

    @GetMapping(value = "/{catId}")
    CategoryDto getCategoryById(@PathVariable Long catId) {
        CategoryDto dto = categoryService.getCategoryById(catId);
        log.info("Got category, {}", dto);
        return dto;
    }
}
