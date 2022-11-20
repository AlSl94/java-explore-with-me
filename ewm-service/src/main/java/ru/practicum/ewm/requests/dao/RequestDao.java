package ru.practicum.ewm.requests.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.requests.RequestStatus;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;

public interface RequestDao extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long id);

    Integer countByEventIdAndStatus(Long eventId, RequestStatus requestStatus);
}
