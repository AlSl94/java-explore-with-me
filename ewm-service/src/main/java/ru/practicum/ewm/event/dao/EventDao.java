package ru.practicum.ewm.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

public interface EventDao extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);  //TODO RC REF COMPARISON

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId); //TODO RC REF COMPARISON
}
