package ru.practicum.ewm.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface EventDao extends JpaRepository<Event, Long> {

    List<Event> findByInitiator(User initiator, Pageable pageable);
}
