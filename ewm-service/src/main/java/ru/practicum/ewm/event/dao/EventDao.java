package ru.practicum.ewm.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;

public interface EventDao extends JpaRepository<Event, Long> {

}
