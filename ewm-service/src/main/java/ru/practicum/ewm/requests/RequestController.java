package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping(value = "/users/{userId}/requests")
    public List<ParticipationRequestDto> findUserRequests(@PathVariable Long userId) {
        List<ParticipationRequestDto> dtoList = requestService.findUserRequests(userId);
        log.info("Got userRequests, {}", dtoList);
        return dtoList;
    }

    @PostMapping(value = "/users/{userId}/requests")
    public ParticipationRequestDto addNewRequest(@PathVariable Long userId,
                                                 @RequestParam Long eventId) {
        ParticipationRequestDto dto = requestService.addNewRequest(userId, eventId);
        log.info("User {} added new request, {}", userId, eventId);
        return dto;
    }

    @PatchMapping(value = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        ParticipationRequestDto dto = requestService.cancelRequest(requestId, userId);
        log.info("User {} rejected request, {}", userId, requestId);
        return dto;
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> findRequestByUserIdAndEventId(@PathVariable Long userId,
                                                                @PathVariable Long eventId) {
        List<ParticipationRequestDto> dtoList = requestService.findRequestByUserIdAndEventId(userId, eventId);
        log.info("Got list {}, by userId {} and by eventId {}", dtoList, userId, eventId);
        return dtoList;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        ParticipationRequestDto dto = requestService.confirmRequest(userId, eventId, reqId);
        log.info("User {} confirmed participation request {} on event {}", userId, reqId, dto);
        return dto;
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        ParticipationRequestDto dto = requestService.rejectRequest(userId, eventId, reqId);
        log.info("User {} rejected participation request {} on event {}", userId, reqId, dto);
        return dto;
    }
}
