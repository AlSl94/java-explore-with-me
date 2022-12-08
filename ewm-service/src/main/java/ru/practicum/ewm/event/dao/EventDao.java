package ru.practicum.ewm.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {

    List<Event> findEventsByInitiatorId(Long initiatorId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "JOIN e.userLikes u " +
            "WHERE u.id = ?1")
    List<Event> findEventsByUserLikes(Long userId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "JOIN e.userDislikes u " +
            "WHERE u.id = ?1")
    List<Event> findEventsDislikedByUser(Long userId, Pageable pageable);

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            "AND e.state IN ?2 " +
            "AND e.category.id IN ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5")
    List<Event> adminFindEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = ru.practicum.ewm.event.EventState.PUBLISHED " +
            "AND (e.annotation LIKE CONCAT('%',?1,'%') OR e.description LIKE CONCAT('%',?1,'%')) " +
            "AND e.category.id IN ?2 " +
            "AND e.paid = ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            "AND ((?6 = true AND e.participantLimit = 0) " +
            "OR (?6 = true AND e.participantLimit > e.confirmedRequests) " +
            "OR (?6 = false))")
    List<Event> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = ru.practicum.ewm.event.EventState.PUBLISHED " +
            "AND e.category.id IN ?1 " +
            "AND e.paid = ?2 " +
            "AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND ((?5 = true AND e.participantLimit = 0) " +
            "OR (?5 = true AND e.participantLimit > e.confirmedRequests) " +
            "OR (?5 = false))")
    List<Event> findEventsWithoutText(List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable);

}
