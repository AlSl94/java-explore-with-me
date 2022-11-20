package ru.practicum.ewm.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.category.model.Category;


public interface CategoryDao extends JpaRepository<Category, Long> {

}
