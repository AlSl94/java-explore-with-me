package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;


    public List<EventShortDto> initiatorEvents(Long userId, int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);

        List<Event> events = eventDao.findByInitiatorId(userId, pageable);

        return EventMapper.toEventDtoList(events);
    }

    @Transactional
    public EventShortDto updateEvent(Long userId, EventShortDto eventDto) {

        Event event = eventDao.findById(eventDto.getId())
                .orElseThrow(() ->
                        new WrongParameterException("События с id " + eventDto.getId() + " не существует."));

        if (event.getState() == EventState.REJECTED || event.getState() == EventState.UNSUPPORTED_STATE
                || event.getState() == EventState.PUBLISHED) {
            throw new WrongParameterException("Изменить можно только отмененные события " +
                    "или события в состоянии ожидания модерации");
        }

        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new WrongParameterException("Обновить событие может только пользователь с id "
                    + event.getInitiator().getId());
        }

        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(CategoryMapper.toCategory(eventDto.getCategory()));
        }
        if (eventDto.getConfirmedRequests() != null) {
            event.setConfirmedRequests(eventDto.getConfirmedRequests());
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(eventDto.getEventDate());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }

        event = eventDao.save(event);

        return EventMapper.toEventShortDto(event);
    }

    @Transactional
    public EventFullDto createEvent(Long userId, EventFullDto eventDto) {

        Event event = EventMapper.toEvent(eventDto);

        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new WrongParameterException("Cоздать событие может только пользователь с id "
                    + event.getInitiator().getId());
        }

        event = eventDao.save(event);

        return EventMapper.toFullEventDto(event);
    }


    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        Event event = eventDao.findByIdAndInitiatorId(eventId, userId);
        return EventMapper.toFullEventDto(event);
    }

    @Transactional
    public EventFullDto cancelEventByUserIdAndEventId(Long userId, Long eventId) {
        Event event = eventDao.findByIdAndInitiatorId(eventId, userId);
        event.setState(EventState.CANCELED);
        return EventMapper.toFullEventDto(event);
    }



}
