package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventsAdmin(@RequestParam(required = false) List<Long> users,
                                             @RequestParam(required = false) List<EventState> states,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false) String rangeStart,
                                             @RequestParam(required = false) String rangeEnd,
                                             @RequestParam(required = false, defaultValue = "0") int from,
                                             @RequestParam(required = false, defaultValue = "10") int size) {
        List<EventFullDto> events = eventService.adminFindEvents(users,
                states, categories, rangeStart, rangeEnd, from, size);
        log.info("Admin got events list {}", events.toString());
        return events;
    }

    @PutMapping(value = "/{eventId}")
    public EventFullDto adminUpdateEvent(@PathVariable Long eventId, @RequestBody NewEventDto eventDto) {
        EventFullDto dto = eventService.adminUpdateEvent(eventId, eventDto);
        log.info("Admin updated event, {}", dto);
        return dto;
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        EventFullDto dto = eventService.publishEvent(eventId);
        log.info("Admin published event, {}", dto);
        return dto;
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        EventFullDto dto = eventService.rejectEvent(eventId);
        log.info("Admin rejected event, {}", dto);
        return dto;
    }
}
