package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dao.UserDao;
import ru.practicum.ewm.utility.Constants;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;
    private final CategoryService categoryService;
    private final UserDao userDao;


    public List<EventShortDto> initiatorEvents(Long userId, int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);

        List<Event> events = eventDao.findByInitiatorId(userId, pageable);

        return EventMapper.toShortEventDtoList(events);
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
            event.setCategory(CategoryMapper.toCategory(categoryService.getCategoryById(event.getId())));
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
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {

        Event event = EventMapper.toEventFromNew(eventDto);

        event.setCategory(CategoryMapper.toCategory(categoryService.getCategoryById(eventDto.getCategory())));
        event.setPublishedOn(LocalDateTime.now());
        event.setInitiator(userDao.findById(userId).orElseThrow());
        event.setConfirmedRequests(0);
        event.setState(EventState.PENDING);

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


    public List<EventFullDto> getEventsAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                             String rangeStart, String rangeEnd, int from, int size) {
        //TODO тупит обработка states

        Pageable pageable = FromSizeRequest.of(from, size);
        LocalDateTime start = LocalDateTime.parse(rangeStart, Constants.TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, Constants.TIME_FORMATTER);
        //List<Event> events = eventDao.adminFindEvents(users, states, categories, start, end, pageable);
        List<Event> events = eventDao.adminFindEventsTest(users, categories, start, end, pageable);

        return EventMapper.toFullEventDtoList(events);
    }

    public EventFullDto adminUpdateEvent(Long eventId, NewEventDto eventDto) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() ->
                        new WrongParameterException("События с id " + eventId + " не существует."));

        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getLocation() != null) {
            event.setLat(eventDto.getLocation().getLat());
            event.setLon(eventDto.getLocation().getLon());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        event = eventDao.save(event);

        return EventMapper.toFullEventDto(event);
    }

    public EventFullDto publishEvent(Long eventId) {
        Event event = eventDao.findById(eventId).orElseThrow(() -> new WrongParameterException("Неверный id события"));
        event.setState(EventState.PUBLISHED);

        return EventMapper.toFullEventDto(event);
    }

    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventDao.findById(eventId).orElseThrow(() -> new WrongParameterException("Неверный id события"));
        event.setState(EventState.REJECTED);

        return EventMapper.toFullEventDto(event);
    }

    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                         String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                         String sort, int from, int size) {

        LocalDateTime start = LocalDateTime.parse(rangeStart, Constants.TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, Constants.TIME_FORMATTER);
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events = eventDao.findEvents(text, categories, paid, start, end, onlyAvailable, pageable);
        return EventMapper.toShortEventDtoList(events);
    }

    public EventFullDto getEventById(Long eventId) {
        Event event = eventDao.findById(eventId).orElseThrow(() -> new WrongParameterException("Неверный id события"));
        return EventMapper.toFullEventDto(event);
    }
}
