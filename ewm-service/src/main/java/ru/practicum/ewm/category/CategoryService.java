package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dao.CategoryDao;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;

    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        Category category = categoryDao.findById(categoryDto.getId())
                .orElseThrow(() ->
                        new WrongParameterException("Категория с id {" + categoryDto.getId() + "} не найдена"));

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        category = categoryDao.save(category);

        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        Category category = CategoryMapper.toCategoryFromNew(categoryDto);
        category = categoryDao.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    public void deleteCategory(Long catId) {
        categoryDao.deleteById(catId);
    }

    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Category> categories = categoryDao.findAll(pageable).getContent();
        return CategoryMapper.toCategoryDtoList(categories);
    }

    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryDao.findById(catId)
                .orElseThrow(() -> new WrongParameterException("Категория c id {" + catId + "} не найдена"));
        return CategoryMapper.toCategoryDto(category);
    }
}
