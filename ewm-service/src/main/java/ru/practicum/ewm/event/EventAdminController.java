package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<String> states,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) LocalDateTime rangeStart,
                                         @RequestParam(required = false) LocalDateTime rangeEnd,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return null;
    }

    @PutMapping(value = "/{eventId}")
    public EventShortDto updateEvent(@PathVariable Long eventId) {
        return null;
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        return null;
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        return null;
    }
}
