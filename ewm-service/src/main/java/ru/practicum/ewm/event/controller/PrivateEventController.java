package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventService;
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
    List<EventShortDto> initiatorEvents(@PathVariable Long userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return eventService.initiatorEvents(userId, from, size);
    }

    @PatchMapping(value = "/users/{userId}/events")
    EventShortDto updateEvent(@PathVariable Long userId,
                              @RequestBody EventShortDto event) {
        return eventService.updateEvent(userId, event);
    }


    @PostMapping(value = "/users/{userId}/events")
    EventFullDto createEvent(@PathVariable Long userId,
                             @RequestBody EventFullDto event) {
        return eventService.createEvent(userId, event);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        return eventService.cancelEventByUserIdAndEventId(userId, eventId);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}/requests")
    ParticipationRequestDto getRequestInfoByUserIdAndEvenId(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return eventService.getRequestInfoByUserIdAndEvenId(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        return eventService.confirmParticipationRequest(userId, eventId, reqId);
    }
    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return eventService.rejectParticipationRequest(userId, eventId, reqId);
    }
}
