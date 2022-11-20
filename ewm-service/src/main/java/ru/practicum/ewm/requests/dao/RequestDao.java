package ru.practicum.ewm.requests.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;

public interface RequestDao extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long id);

    Request findByEventIdAndRequesterId(Long eventId, Long userId);

    Request findByEventIdAndRequesterIdAndId(Long eventId, Long userId, Long reqId);  //TODO RC REF COMPARISON
}
