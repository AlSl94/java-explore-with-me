package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping(value = "/users/{userId}/events")
    List<EventShortDto> userEvents(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PatchMapping(value = "/users/{userId}/events")
    EventShortDto updateEvent(@PathVariable Long userId,
                              @RequestBody EventShortDto event) {
        return null;
    }


    @PostMapping(value = "/users/{userId}/events")
    EventFullDto createEvent(@PathVariable Long userId,
                             @RequestBody EventFullDto event) {
        return null;
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        return null;
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}/requests")
    ParticipationRequestDto getRequestInfoByUserIdAndEvenId(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        return null;
    }
    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return null;
    }
}
