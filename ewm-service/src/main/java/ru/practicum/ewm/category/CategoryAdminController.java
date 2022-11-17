package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController { // TODO

    @PatchMapping
    CategoryDto updateCategory(@RequestBody CategoryDto category) {
        return null;
    }

    @PostMapping
    CategoryDto createCategory(@RequestBody CategoryDto category) {
        return null;
    }

    @DeleteMapping(value = "/{catId}")
    void deleteCategory(@PathVariable Long catId) {

    }

}
