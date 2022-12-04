package ru.practicum.ewm.requests.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;

@Repository
public interface RequestDao extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long id);

    List<Request> findAllByEventIdAndRequesterId(Long eventId, Long requesterId);

}
