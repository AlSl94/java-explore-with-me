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

    }

    public EventShortDto updateEvent(Long userId, EventShortDto event) {
    }

    public EventFullDto createEvent(Long userId, EventFullDto event) {
        Event event = Event
         = eventDao.save(event);
    }


    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
    }


    public EventFullDto cancelEventByUserIdAndEventId(Long userId, Long eventId) {
    }


    public ParticipationRequestDto getRequestInfoByUserIdAndEvenId(Long userId, Long eventId) {
    }


    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) {
    }


    public ParticipationRequestDto rejectParticipationRequest(Long userId, Long eventId, Long reqId) {
    }
}
