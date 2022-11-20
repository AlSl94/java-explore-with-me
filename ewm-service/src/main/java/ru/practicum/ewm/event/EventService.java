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
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.dao.UserDao;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;
    private final UserDao userDao;


    public List<EventShortDto> initiatorEvents(Long userId, int from, int size) {
        User user = userDao.findById(userId).orElseThrow();
        Pageable pageable = FromSizeRequest.of(from, size);

        List<Event> events = eventDao.findByInitiator(user, pageable);

        return EventMapper.toEventDtoList(events);
    }

    @Transactional
    public EventShortDto updateEvent(Long userId, EventShortDto eventDto) {
        Event event = eventDao.findById(eventDto.getId())
                .orElseThrow(() ->
                        new WrongParameterException("События с id {" + eventDto.getId() + "} не существует."));

        if (event.getState() == EventState.REJECTED || event.getState() == EventState.UNSUPPORTED_STATE
                || event.getState() == EventState.PUBLISHED) {
            throw new WrongParameterException("Изменить можно только отмененные события " +
                    "или события в состоянии ожидания модерации");
        }

        if (event.getInitiator().getId() != userId) {
            throw new WrongParameterException("Обновить событие может только пользователь с id "
                    + event.getInitiator().getId());
        }

//        private Integer confirmedRequests;
//        private LocalDateTime eventDate;
//        private Long id;
//        private UserShortDto initiator;
//        private Boolean paid;
//        private String title;
//        private Integer views;

        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(CategoryMapper.toCategory(eventDto.getCategory()));
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(eventDto.getEventDate());
        }
        if (event.getRequestModeration() != null) {
            event.setRequestModeration(event.getRequestModeration());
        }
        return null;
    }

    public EventFullDto createEvent(Long userId, EventFullDto eventDto) {
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
