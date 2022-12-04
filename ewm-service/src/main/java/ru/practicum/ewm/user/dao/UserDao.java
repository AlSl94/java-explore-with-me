package ru.practicum.ewm.user.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByIdIn(List<Long> ids, Pageable pageable);
}