package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;


    public List<EventShortDto> userEvents(Long userId, int from, int size) {
        return null;
    }

    public EventShortDto updateEvent(Long userId, EventShortDto event) {
        return null;
    }

    public EventFullDto createEvent(Long userId, EventFullDto event) {
        return null;
    }


    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }


    public EventFullDto cancelEventByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }


    public ParticipationRequestDto getRequestInfoByUserIdAndEvenId(Long userId, Long eventId) {
        return null;
    }


    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }


    public ParticipationRequestDto rejectParticipationRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }
}
