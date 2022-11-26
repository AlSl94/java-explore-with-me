package ru.practicum.ewm.requests.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;

public interface RequestDao extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long id);

    List<Request> findAllByEventIdAndRequesterId(Long eventId, Long requesterId); //todo

    Request findByEventIdAndRequesterIdAndId(Long eventId, Long userId, Long reqId);
}
