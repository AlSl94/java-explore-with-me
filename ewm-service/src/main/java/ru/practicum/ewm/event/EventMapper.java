package ru.practicum.ewm.event;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.UserMapper;
import ru.practicum.ewm.utility.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {
    private EventMapper() {

    }

    public static EventFullDto toFullEventDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getPublishedOn().format(Constants.TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(Constants.TIME_FORMATTER))
                .id(event.getId())
                .initiator(UserMapper.toShortUserDto(event.getInitiator()))
                .location(new Location(event.getLat(), event.getLon()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(Constants.TIME_FORMATTER))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory().getId())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toShortUserDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static Event toEventFromNew(NewEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(), Constants.TIME_FORMATTER))
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .build();
    }

    public static Event toEvent(EventFullDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .category(CategoryMapper.toCategory(eventDto.getCategory()))
                .title(eventDto.getTitle())
                .publishedOn(LocalDateTime.parse(eventDto.getPublishedOn(), Constants.TIME_FORMATTER))
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(), Constants.TIME_FORMATTER))
                .initiator(UserMapper.toUserFromShortDto(eventDto.getInitiator()))
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .state(eventDto.getState())
                .build();
    }

    public static List<EventShortDto> toShortEventDtoList(Iterable<Event> events) {
        List<EventShortDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(toEventShortDto(event));
        }
        return result;
    }

    public static List<EventFullDto> toFullEventDtoList(Iterable<Event> events) {
        List<EventFullDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(toFullEventDto(event));
        }
        return result;
    }
}