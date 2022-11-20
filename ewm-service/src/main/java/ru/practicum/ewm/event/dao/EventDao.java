package ru.practicum.ewm.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDao extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            "AND e.state IN ?2 " +
            "AND e.category.id IN ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5")
    List<Event> findEvents(List<Long> users, List<EventState> states, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}
