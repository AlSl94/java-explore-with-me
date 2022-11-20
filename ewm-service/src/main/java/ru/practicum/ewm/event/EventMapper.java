package ru.practicum.ewm.event;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

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
                .category(event.getCategoryId())
                .build();
        private CategoryDto ;
        private Integer confirmedRequests;
        private LocalDateTime createdOn;
        private String description;
        private LocalDateTime eventDate;
        private Long id;
        private UserShortDto initiator;
        private Location location;
        private Boolean paid;
        private Integer participantLimit;
        private LocalDateTime publishedOn;
        private Boolean requestModeration;
        private String state;
        private String title;
        private Integer views;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .build();
    }

    public static Event toEvent(EventFullDto eventDto) {
        return Event.builder()
                .build();
    }

    public static List<EventShortDto> toEventDtoList(Iterable<Event> events) {
        List<EventShortDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(toEventShortDto(event));
        }
        return result;
    }
}