package ru.practicum.ewm.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.RequestService;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping(value = "/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        return requestService.getUserRequests(userId);
    }

    @PostMapping(value = "/users/{userId}/requests")
    public ParticipationRequestDto addNewRequest(@PathVariable Long userId,
                                                 @RequestParam Long eventId) {
        return requestService.addNewRequest(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long requestId,
                                                 @PathVariable Long userId) {
        return requestService.cancelRequest(requestId, userId);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getRequestInfoByUserIdAndEventId(@PathVariable Long userId,
                                                                   @PathVariable Long eventId) {
        return requestService.getRequestByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        return requestService.confirmParticipationRequest(userId, eventId, reqId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return requestService.rejectParticipationRequest(userId, eventId, reqId);
    }
}
