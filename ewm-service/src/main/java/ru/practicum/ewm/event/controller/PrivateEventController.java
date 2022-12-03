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

import java.util.List;

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
}
