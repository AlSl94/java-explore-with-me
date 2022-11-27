package ru.practicum.ewm.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.Category;


@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

}
