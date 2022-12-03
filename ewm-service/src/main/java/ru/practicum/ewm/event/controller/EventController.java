package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.HitClient;
import ru.practicum.ewm.client.dto.EndPointHit;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.utility.Constants;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final HitClient hitClient;

    @GetMapping
    List<EventShortDto> findEvents(@RequestParam(required = false) String text,
                                   @RequestParam(required = false) List<Long> categories,
                                   @RequestParam(defaultValue = "false") Boolean paid,
                                   @RequestParam(required = false) String rangeStart,
                                   @RequestParam(required = false) String rangeEnd,
                                   @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                   @RequestParam(required = false) String sort,
                                   @RequestParam(required = false, defaultValue = "0") int from,
                                   @RequestParam(required = false, defaultValue = "10") int size,
                                   HttpServletRequest request) {
        List<EventShortDto> events = eventService.findEvents(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
        log.info("Got event list {}", events.toString());
        saveHit(request);
        log.info("Added hit to /events/");
        return events;
    }

    @GetMapping("/{eventId}")
    EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request) {
        EventFullDto dto = eventService.getEventById(eventId);
        log.info("Got event by id, {}", dto);
        saveHit(request);
        log.info("Added hit to event {}", eventId);
        return dto;
    }

    private void saveHit(HttpServletRequest request) {
        hitClient.addHit(EndPointHit.builder()
                .app("ewm-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(Constants.TIME_FORMATTER))
                .build());
    }
}
