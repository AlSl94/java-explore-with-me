package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping(value = "/users/{userId}/events")
    List<EventShortDto> findEventsByInitiatorId(@PathVariable Long userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        List<EventShortDto> events = eventService.findEventsByInitiatorId(userId, from, size);
        log.info("Получены события {} пользователя {}", events, userId);
        return events;
    }

    @PatchMapping(value = "/users/{userId}/events")
    EventFullDto updateEvent(@PathVariable Long userId,
                             @RequestBody UpdateEventRequest event) {
        EventFullDto dto = eventService.updateEvent(userId, event);
        log.info("Обновлено событие, {}", dto);
        return dto;
    }


    @PostMapping(value = "/users/{userId}/events")
    EventFullDto createEvent(@PathVariable Long userId,
                             @RequestBody NewEventDto event) {
        EventFullDto dto = eventService.createEvent(userId, event);
        log.info("Создано событие, {}", dto);
        return dto;
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        EventFullDto dto = eventService.findEventByUserIdAndEventId(userId, eventId);
        log.info("Получено событие {} по userId {} и eventId {}", dto, userId, eventId);
        return dto;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        EventFullDto dto = eventService.cancelEventByUserIdAndEventId(userId, eventId);
        log.info("Отменена события {} по userId {} и eventId {}", dto, userId, eventId);
        return dto;
    }

    @PostMapping(value = "/users/{userId}/events/{eventId}/like")
    public EventShortDto addEventLike(@PathVariable Long userId,
                                      @PathVariable Long eventId) {
        EventShortDto dto = eventService.addEventLike(userId, eventId);
        log.info("User {} liked event {}", userId, eventId);
        return dto;
    }

    @DeleteMapping(value = "/users/{userId}/events/{eventId}/like")
    public EventShortDto removeEventLike(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        EventShortDto dto = eventService.removeEventLike(userId, eventId);
        log.info("User {} removed like from event {}", userId, eventId);
        return dto;
    }

    @GetMapping(value = "/users/{userId}/events/liked")
    public Set<EventShortDto> findEventsLikedByUser(@PathVariable Long userId,
                                                    @RequestParam(name = "from", defaultValue = "0")
                                                    @PositiveOrZero int from,
                                                    @RequestParam(name = "size", defaultValue = "10")
                                                    @Positive int size) {
        log.info("Events liked by user {}", userId);
        return eventService.findEventsLikedByUser(userId, from, size);
    }

    @PostMapping(value = "/users/{userId}/events/{eventId}/dislike")
    public EventShortDto addEventDislike(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        EventShortDto dto = eventService.addEventDislike(userId, eventId);
        log.info("User {} disliked event {}", userId, eventId);
        return dto;
    }

    @DeleteMapping(value = "/users/{userId}/events/{eventId}/dislike")
    public EventShortDto removeEventDislike(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        EventShortDto dto = eventService.removeEventDislike(userId, eventId);
        log.info("User {} removed dislike from event {}", userId, eventId);
        return dto;
    }

    @GetMapping(value = "/users/{userId}/events/disliked")
    public Set<EventShortDto> findEventsDislikedByUser(@PathVariable Long userId,
                                                       @RequestParam(name = "from", defaultValue = "0")
                                                       @PositiveOrZero int from,
                                                       @RequestParam(name = "size", defaultValue = "10")
                                                       @Positive int size) {
        log.info("Events disliked by user {}", userId);
        return eventService.findEventsDislikedByUser(userId, from, size);
    }
}
