package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dao.CategoryDao;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.error.AlreadyExistsException;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;

    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        if (categoryDto.getName() == null && categoryDto.getId() == null) {
            throw new ValidationException("Wrong body");
        }

        Category category = categoryDao.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException("Category {" + categoryDto.getId() + "} not found"));

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        try {
            category = categoryDao.saveAndFlush(category);
            return CategoryMapper.toCategoryDto(category);
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique");
        }
    }

    @Transactional
    public CategoryDto createCategory(NewCategoryDto categoryDto) {

        if (categoryDto.getName() == null) {
            throw new ValidationException("Wrong body");
        }

        try {
            Category category = CategoryMapper.toCategoryFromNew(categoryDto);
            category = categoryDao.save(category);
            return CategoryMapper.toCategoryDto(category);
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
    }

    @Transactional
    public void deleteCategory(Long catId) {
        categoryDao.deleteById(catId);
    }

    public List<CategoryDto> findCategories(int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Category> categories = categoryDao.findAll(pageable).getContent();
        return CategoryMapper.toCategoryDtoList(categories);
    }

    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryDao.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category {" + catId + "} not found"));
        return CategoryMapper.toCategoryDto(category);
    }
}
