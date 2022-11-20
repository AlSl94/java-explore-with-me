package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<EventState> states,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(value = "/{eventId}")
    public EventFullDto adminUpdateEvent(@PathVariable Long eventId, @RequestBody NewEventDto eventDto) {
        return eventService.adminUpdateEvent(eventId, eventDto);
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        return eventService.publishEvent(eventId);
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        return eventService.rejectEvent(eventId);
    }
}
