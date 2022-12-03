package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dao.UserDao;
import ru.practicum.ewm.utility.Constants;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;
    private final CategoryService categoryService;
    private final UserDao userDao;

    public List<EventShortDto> findEventsByInitiatorId(Long userId, int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);

        List<Event> events = eventDao.findEventsByInitiatorId(userId, pageable);

        return EventMapper.toShortEventDtoList(events);
    }

    @Transactional
    public EventFullDto updateEvent(Long userId, UpdateEventRequest eventDto) {

        Event event = eventDao.findById(eventDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Event " + eventDto.getEventId() + " not found"));


        if (event.getState() == EventState.CANCELED || event.getState() == EventState.UNSUPPORTED_STATE
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
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), Constants.TIME_FORMATTER));
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }

        event = eventDao.save(event);

        return EventMapper.toFullEventDto(event);
    }

    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {

        if (eventDto.getAnnotation() == null && eventDto.getDescription() == null) {
            throw new ValidationException("Wrong body");
        }

        Event event = EventMapper.toEventFromNew(eventDto);

        event.setCategory(CategoryMapper.toCategory(categoryService.getCategoryById(eventDto.getCategory())));
        event.setPublishedOn(LocalDateTime.now());
        event.setInitiator(userDao.findById(userId).orElseThrow());
        event.setConfirmedRequests(0);
        event.setState(EventState.PENDING);

        event = eventDao.save(event);
        return EventMapper.toFullEventDto(event);
    }


    public EventFullDto findEventByUserIdAndEventId(Long userId, Long eventId) {
        Event event = eventDao.findByIdAndInitiatorId(eventId, userId);
        return EventMapper.toFullEventDto(event);
    }

    @Transactional
    public EventFullDto cancelEventByUserIdAndEventId(Long userId, Long eventId) {
        Event event = eventDao.findByIdAndInitiatorId(eventId, userId);
        event.setState(EventState.CANCELED);
        eventDao.save(event);
        return EventMapper.toFullEventDto(event);
    }


    public List<EventFullDto> adminFindEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                              String rangeStart, String rangeEnd, int from, int size) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, Constants.TIME_FORMATTER);
        }

        if (rangeEnd != null) {
            end = LocalDateTime.parse(rangeEnd, Constants.TIME_FORMATTER);
        }

        if (users == null) {
            users = new ArrayList<>();
        }

        if (states == null) {
            states = new ArrayList<>();
            states.add(EventState.PENDING);
            states.add(EventState.CANCELED);
            states.add(EventState.PUBLISHED);
        }

        if (categories == null) {
            categories = new ArrayList<>();
        }

        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events = eventDao.adminFindEvents(users, states, categories, start, end, pageable);
        return EventMapper.toFullEventDtoList(events);
    }

    @Transactional
    public EventFullDto adminUpdateEvent(Long eventId, NewEventDto eventDto) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));

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

    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));
        event.setState(EventState.PUBLISHED);
        eventDao.save(event);

        return EventMapper.toFullEventDto(event);
    }

    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));
        event.setState(EventState.CANCELED);
        eventDao.save(event);

        return EventMapper.toFullEventDto(event);
    }

    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                          String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                          String sort, int from, int size) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, Constants.TIME_FORMATTER);
        }

        if (rangeEnd != null) {
            end = LocalDateTime.parse(rangeEnd, Constants.TIME_FORMATTER);
        }

        if (categories == null) {
            categories = new ArrayList<>();
        }

        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events;

        if (text == null) {
            events = eventDao.findEventsWithoutText(categories, paid, start, end, onlyAvailable, pageable);
        } else {
            events = eventDao.findEvents(text, categories, paid, start, end, onlyAvailable, pageable);
        }

        if (Objects.equals(sort, "EVENT_DATE")) {
            events = events
                    .stream()
                    .sorted(Comparator.comparing(Event::getEventDate).reversed())
                    .collect(Collectors.toList());
        }
        return EventMapper.toShortEventDtoList(events);
    }

    public EventFullDto getEventById(Long eventId) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));
        return EventMapper.toFullEventDto(event);
    }
}